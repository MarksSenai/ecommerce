package com.ecommerce.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ecommerce.domains.BilletPayment;

@Service
public class BilletService {

    public void fillOutPaymentBillet(BilletPayment billetPayment, Date paymentInstant) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(paymentInstant);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        billetPayment.setDueDate(cal.getTime());
    }
}
