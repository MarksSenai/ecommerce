package com.ecommerce.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ecommerce.domains.Address;
import com.ecommerce.domains.OrderItem;
import com.ecommerce.domains.Payment;
import com.ecommerce.domains.User;

public class OrdersDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orderId;
    private Date instant;
    private User user;
    private Payment payment;
    private Address deliveryAddress;
    private List<OrderItemDTO> items = new ArrayList<>();
    private Double totalValue;

    public OrdersDTO() {
    }

    public OrdersDTO(Date instant, User user, Address deliveryAddress) {
        super();
        this.instant = instant;
        this.user = user;
        this.deliveryAddress = deliveryAddress;
    }


    public double getTotalValue() {
        double sum = 0.0;
        for (OrderItemDTO oi : items) {
            sum = sum + oi.getSubTotal();
        }
        this.totalValue = sum;
        return totalValue;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instant) {
        this.instant = instant;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
