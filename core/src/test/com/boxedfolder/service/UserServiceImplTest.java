/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.service;

import com.boxedfolder.carrot.domain.User;
import com.boxedfolder.carrot.domain.util.Mailer;
import com.boxedfolder.carrot.domain.util.RandomTokenGenerator;
import com.boxedfolder.carrot.domain.util.impl.MailerImpl;
import com.boxedfolder.carrot.repository.UserRepository;
import com.boxedfolder.carrot.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class UserServiceImplTest {
    private UserServiceImpl userService;
    private UserRepository mockRepository;
    private Mailer mockMailer;
    private RandomTokenGenerator mockTokenGenerator;
    private User firstUser;
    private User secondUser;
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
        mockRepository = mock(UserRepository.class);
        mockMailer = mock(MailerImpl.class);
        mockTokenGenerator = mock(RandomTokenGenerator.class);
        userService = new UserServiceImpl();
        userService.setMailer(mockMailer);
        userService.setRepository(mockRepository);
        userService.setTokenGenerator(mockTokenGenerator);
        userService.setPasswordEncoder(passwordEncoder);

        String password = passwordEncoder.encode("password");
        firstUser = new User();
        firstUser.setEmail("test@example.com");
        firstUser.setPassword(password);
        firstUser.setEnabled(true);
        firstUser.setId(0L);

        password = passwordEncoder.encode("password2");
        secondUser = new User();
        secondUser.setEmail("second@example.com");
        secondUser.setPassword(password);
        secondUser.setEnabled(false);
        secondUser.setId(1L);
    }

    @Test
    public void testGetUserById() {
        given(mockRepository.findOne(1L)).willReturn(secondUser);
        User returnedUser = userService.find(1L);
        assertThat(returnedUser, equalTo(secondUser));
    }

    @Test
    public void testGetUserByEmail() {
        given(mockRepository.findByEmail("test@example.com")).willReturn(firstUser);
        User returnedUser = userService.findByEmail("test@example.com");
        assertThat(returnedUser, equalTo(firstUser));
    }

    @Test
    public void testGetAllUsers() {
        User[] array = {firstUser, secondUser};
        List<User> users = new ArrayList<>(Arrays.asList(array));
        given(mockRepository.findAllByOrderByDateCreatedDesc()).willReturn(users);
        List<User> returnedUsers = userService.findAll();
        assertThat(returnedUsers.size(), equalTo(2));
        assertThat(returnedUsers.get(0), equalTo(firstUser));
        assertThat(returnedUsers.get(1), equalTo(secondUser));
    }

    @Test
    public void testCreateUser() {
        given(mockRepository.save((User)notNull())).willReturn(firstUser);
        User user = userService.createUser("test@example.com", "password", true);
        assertThat(user, equalTo(firstUser));
    }

    @Test
    public void testActivateUser() {
        given(mockRepository.findByEmail("test@example.com")).willReturn(firstUser);
        given(mockRepository.findOne(0L)).willReturn(firstUser);
        firstUser.setEnabled(false);
        firstUser.setActivationToken("12345");
        userService.activateUser(firstUser.getEmail(), "12345");
        assertTrue(firstUser.isEnabled());
    }

    @Test
    public void testResetPassword() {
        User someUser = new User();
        someUser.setEmail("test@example.com");
        someUser.setEnabled(true);
        someUser.setId(0L);
        someUser.setResetToken("token");
        someUser.setPassword("54321");

        given(mockRepository.findByEmail("test@example.com")).willReturn(someUser);
        given(mockRepository.findOne(0L)).willReturn(someUser);
        given(mockTokenGenerator.getRandomToken(anyInt())).willReturn("12345");
        userService.resetPassword(someUser.getEmail(), "token");
        assertNull(someUser.getResetToken());
    }
}
