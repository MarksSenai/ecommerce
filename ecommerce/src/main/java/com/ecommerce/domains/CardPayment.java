package com.ecommerce.domains;

import com.ecommerce.domains.enums.PaymentStatus;

import javax.persistence.Entity;

@Entity
public class CardPayment extends Payment{
    private static final long serialVersionUID = 1L;

    private Integer numberOfInstallments;

    public CardPayment() {
    }

    public CardPayment(Long id, PaymentStatus status, Orders order, Integer numberOfInstallments) {
        super(id, status, order);
        this.numberOfInstallments = numberOfInstallments;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }
}
