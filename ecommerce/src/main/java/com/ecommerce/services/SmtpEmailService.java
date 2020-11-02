package com.ecommerce.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.services.abstracts.AbstractEmailService;

public class SmtpEmailService extends AbstractEmailService {

    private static final Logger logger = LoggerFactory.getLogger(SmtpEmailService.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Sending email...");
        mailSender.send(message);
        logger.info("Email sent");
    }
}
