package com.ecommerce.services.abstracts;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ecommerce.domains.Orders;
import com.ecommerce.domains.User;
import com.ecommerce.services.interfaces.EmailService;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default-sender}")
    private String SENDER;
    private String SUBJECT = "Pedido confirmado! Código: ";

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendOrderConfirmationEmail(Orders order) {
        sendEmail(prepareSimpleMailMessageFromOrder(order));
    }

    @Override
    public void sendOrderConfirmationHtmlEmail(Orders order) {
        try {
            sendHtmlEmail(prepareMimeMessageFromOrder(order));
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(order);
        }
    }

    @Override
    public void sendPasswordReseted(User user, String password) {
        sendEmail(preparePasswordResetedEmail(user, password));
    }

    protected SimpleMailMessage preparePasswordResetedEmail(User user, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom(SENDER);
        simpleMailMessage.setSubject("Solicitação de nova senha");
        simpleMailMessage.setSentDate(new Date(System.currentTimeMillis()));
        simpleMailMessage.setText("Nova senha: " + password);
        return simpleMailMessage;
    }

    protected MimeMessage prepareMimeMessageFromOrder(Orders order) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(order.getUser().getEmail());
        mimeMessageHelper.setFrom(SENDER);
        mimeMessageHelper.setSubject(SUBJECT + order.getId());
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlFromTemplateOrder(order), true);
        return mimeMessage;

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

    protected String htmlFromTemplateOrder(Orders order) {
        Context context = new Context();
        context.setVariable("order", order);
        return templateEngine.process("emails/orderConfirmation", context);
    }


}
