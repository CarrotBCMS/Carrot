package com.boxedfolder.carrot.domain.util;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public interface Mailer {
    void sendActivationMail(String to, String subject, String link);
    void sendResetPasswordMail(String to, String subject, String link);
    void sendNewPasswordEmail(String to, String subject, String newPassword);
}