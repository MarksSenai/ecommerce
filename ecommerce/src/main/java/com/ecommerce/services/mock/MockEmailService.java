package com.ecommerce.services.mock;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.services.abstracts.AbstractEmailService;

public class MockEmailService extends AbstractEmailService {

    private static final Logger logger = LoggerFactory.getLogger(MockEmailService.class);
    @Override
    public void sendEmail(SimpleMailMessage message) {
        logger.info("Email sending emulation");
        logger.info(message.toString());
        logger.info("Email sent");
    }

    @Override
    public void sendHtmlEmail(MimeMessage message) {
        logger.info(" HTML Email sending emulation");
        logger.info(message.toString());
        logger.info("Email sent");
    }
}
