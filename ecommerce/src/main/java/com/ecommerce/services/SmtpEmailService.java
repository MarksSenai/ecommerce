package com.ecommerce.services;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;

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

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Sending email...");
        mailSender.send(message);
        logger.info("Email sent");
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        logger.info("Sending html email...");
        javaMailSender.send(msg);
        logger.info("Email sent");
    }
}
