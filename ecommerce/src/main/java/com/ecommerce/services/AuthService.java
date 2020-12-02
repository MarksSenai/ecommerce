package com.ecommerce.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.domains.User;
import com.ecommerce.services.interfaces.EmailService;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void resetPassword(String email) {
        User user = userService.findUserByEmail(email);
        String newPassword = getRandomPassword();
        user.setPassword(passwordEncoder.encode(newPassword));
        emailService.sendPasswordReseted(user, newPassword);
        userService.updateUserPassword(user);
    }

    public String getRandomPassword() {
        char[] passWord = new char[10];
        for (int i=0; i < 10 ; i++) {
            passWord[i] = randomValue();
        }
        return new String(passWord);
    }

    private char randomValue() {
        int digit = random.nextInt(3);
        if (digit == 0) {
            return (char) (random.nextInt(10) + 48);
        } else if (digit == 1) {
            return (char) (random.nextInt(26) + 65);
        } else {
            return (char) (random.nextInt(26) + 97);
        }
    }
}
