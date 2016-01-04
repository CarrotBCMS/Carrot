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

package com.boxedfolder.carrot.domain.util.impl;

import com.boxedfolder.carrot.domain.util.Mailer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Component
public class MailerImpl implements Mailer {
    private JavaMailSender sender;
    private SpringTemplateEngine templateEngine;

    @NotNull
    @Value("${spring.mail.smtp.from}")
    private String fromAddress;

    @Inject
    public MailerImpl(JavaMailSender sender, SpringTemplateEngine templateEngine) {
        this.sender = sender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendActivationMail(String to, String subject, String link) {
        Context context = getContext();
        context.setVariable("link", link);
        sendMessage(getMimeMessage(to, subject, "email/activate", context));
    }

    @Override
    public void sendResetPasswordMail(String to, String subject, String link) {
        Context context = getContext();
        context.setVariable("link", link);
        sendMessage(getMimeMessage(to, subject, "email/resetPassword", context));
    }

    @Override
    public void sendNewPasswordEmail(String to, String subject, String newPassword) {
        Context context = getContext();
        context.setVariable("newPassword", newPassword);
        sendMessage(getMimeMessage(to, subject, "email/newPassword", context));
    }

    private Context getContext() {
        return new Context();
    }

    private MimeMessage getMimeMessage(String to, String subject, String template, Context context) {
        try {
            final MimeMessage mimeMessage = sender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject(subject);
            message.setTo(to);
            message.setFrom(fromAddress);

            String htmlContent = templateEngine.process(template, context);
            message.setText(htmlContent, true);
            return message.getMimeMessage();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void sendMessage(MimeMessage message) {
        if (message != null) {
            sender.send(message);
        }
    }
}