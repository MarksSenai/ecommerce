package com.ecommerce.domains;

import com.ecommerce.domains.enums.PaymentStatus;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class BilletPayment  extends Payment {
    private static final long serialVersionUID = 1L;
    private Date dueDate;
    private Date payDay;

    public BilletPayment() {
    }

    public BilletPayment(Long id, PaymentStatus status, Orders order, Date dueDate, Date payDay) {
        super(id, status, order);
        this.dueDate = dueDate;
        this.payDay = payDay;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPayDay() {
        return payDay;
    }

    public void setPayDay(Date payDay) {
        this.payDay = payDay;
    }


}
