package com.ecommerce.services.interfaces;


import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.domains.Orders;

public interface EmailService {

    void sendOrderConfirmationEmail(Orders order);

    void sendEmail(SimpleMailMessage message);
}
