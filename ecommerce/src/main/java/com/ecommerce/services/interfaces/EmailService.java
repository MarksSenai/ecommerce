package com.ecommerce.services.interfaces;


import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.domains.Orders;
import com.ecommerce.dto.OrdersDTO;

public interface EmailService {

    void sendOrderConfirmationEmail(Orders dto);

    void sendEmail(SimpleMailMessage message);

    void sendOrderConfirmationHtmlEmail(Orders ordersDTO);

    void sendHtmlEmail(MimeMessage msg);
}
