package com.boxedfolder.carrot.service.impl;

import com.boxedfolder.carrot.config.security.AuthenticationHelper;
import com.boxedfolder.carrot.domain.User;
import com.boxedfolder.carrot.domain.util.Mailer;
import com.boxedfolder.carrot.domain.util.RandomTokenGenerator;
import com.boxedfolder.carrot.exceptions.GeneralExceptions;
import com.boxedfolder.carrot.repository.UserRepository;
import com.boxedfolder.carrot.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Service
public class UserServiceImpl extends CrudServiceImpl<User, UserRepository> implements UserService {
    private RandomTokenGenerator tokenGenerator;
    private PasswordEncoder passwordEncoder;
    private AuthenticationHelper authenticationHelper;
    private Mailer mailer;

    @NotNull
    @Value("${app.baseUrl}")
    private String baseUrl;

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password, Boolean isEnabled) {
        if (findByEmail(email) != null) {
            throw new GeneralExceptions.AlreadyExistsException();
        }

        User user = new User();
        user.setEmail(email);
        user.setEnabled(isEnabled);
        user.setPassword(passwordEncoder.encode(password));
        user = save(user);

        if (!isEnabled) {
            user.setActivationToken(tokenGenerator.getRandomToken());
            repository.save(user);
            sendActivationMail(user);
        }
        return user;
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        // Retrieve logged in user
        User currentUser = authenticationHelper.getCurrentUser();

        // Find user (which is to be updated)
        User selectedUser = find(id);

        if (selectedUser == null || updatedUser == null || currentUser == null) {
            throw new GeneralExceptions.NotFoundException();
        }

        if (updatedUser.getPassword() != null) {
            selectedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        // Check if user is enabled
        if (currentUser.isEnabled()) {
            selectedUser.setEnabled(updatedUser.isEnabled());
        }

        return save(selectedUser);
    }

    @Override
    public void activateUser(String email, String token) {
        User user = findByEmail(email);
        if (user != null && token.equals(user.getActivationToken())) {
            user.setEnabled(true);
            user.setActivationToken(null);
            save(user);
            return;
        }

        throw new GeneralExceptions.NotFoundException();
    }

    @Override
    public void resendActivationMail(String email) {
        User user = findByEmail(email);
        if (user != null) {
            sendActivationMail(user);
            return;
        }

        throw new GeneralExceptions.NotFoundException();
    }


    @Override
    public void requestResetPassword(String email) {
        User user = findByEmail(email);
        if (user != null && user.isEnabled()) {
            user.setResetToken(tokenGenerator.getRandomToken());
            save(user);
            sendResetEmail(user);
            return;
        }

        throw new GeneralExceptions.NotFoundException();
    }

    @Override
    public void resetPassword(String email, String token) {
        User user = findByEmail(email);
        if (user != null && user.isEnabled() && token.equals(user.getResetToken())) {
            String newPassword = tokenGenerator.getRandomToken(50);
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetToken(null);
            save(user);
            sendNewPasswordEmail(user, newPassword);
            return;
        }

        throw new GeneralExceptions.NotFoundException();
    }

    private String getActivationLink(String email, String token, String bUrl) {
        return bUrl + "/activate?email=" + email + "&token=" + token;
    }

    private String getResetPasswordLink(String email, String token, String bUrl) {
        return bUrl + "/reset?email=" + email + "&token=" + token;
    }

    private String getBaseUrl() {
        return baseUrl;
    }

    private void sendActivationMail(User user) {
        if (user == null || user.isEnabled() || user.getActivationToken() == null) {
            return;
        }

        String subject = "Your Papaya Activation Link";
        String activationLink = getActivationLink(user.getEmail(), user.getActivationToken(), getBaseUrl());
        mailer.sendActivationMail(user.getEmail(), subject, activationLink);
    }

    private void sendResetEmail(User user) {
        if (user == null || !user.isEnabled() || user.getResetToken() == null) {
            return;
        }

        String subject = "Reset your Papaya Password";
        String link = getResetPasswordLink(user.getEmail(), user.getResetToken(), getBaseUrl());
        mailer.sendResetPasswordMail(user.getEmail(), subject, link);
    }

    private void sendNewPasswordEmail(User user, String newPassword) {
        if (user == null || !user.isEnabled()) {
            return;
        }

        // Generate random token, link and mail them to the user
        String subject = "Your Papaya Password";
        mailer.sendNewPasswordEmail(user.getEmail(), subject, newPassword);
    }

    @Inject
    public void setAuthenticationHelper(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Inject
    public void setTokenGenerator(RandomTokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @Inject
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Inject
    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }
}