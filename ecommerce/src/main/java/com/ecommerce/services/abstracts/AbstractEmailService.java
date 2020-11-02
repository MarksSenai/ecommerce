package com.ecommerce.services.abstracts;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.ecommerce.domains.Orders;
import com.ecommerce.services.interfaces.EmailService;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default-sender}")
    private String SENDER;
    private String SUBJECT = "Pedido confirmado: ";

    @Override
    public void sendOrderConfirmationEmail(Orders order) {
        sendEmail(prepareSimpleMailMessageFromOrder(order));
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromOrder(Orders order) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(order.getUser().getEmail());
        simpleMailMessage.setFrom(SENDER);
        simpleMailMessage.setSubject(SUBJECT + order.getId());
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText(order.toString());
        return simpleMailMessage;
    }
}
