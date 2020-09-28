package com.ecommerce.domains;

import com.ecommerce.domains.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@JsonTypeName("billetPayment")
public class BilletPayment  extends Payment {
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
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
