package com.ecommerce.services.interfaces;


import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.domains.Orders;
import com.ecommerce.domains.User;

public interface EmailService {

    void sendOrderConfirmationEmail(Orders dto);

    void sendEmail(SimpleMailMessage message);

    void sendOrderConfirmationHtmlEmail(Orders order);

    void sendHtmlEmail(MimeMessage msg);

    void sendPasswordReseted(User user, String password);
}
