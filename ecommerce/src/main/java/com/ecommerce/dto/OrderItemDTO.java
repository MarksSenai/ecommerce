package com.ecommerce.dto;

import com.ecommerce.domains.OrderItemPK;

public class OrderItemDTO {

    private Long id;
    private Double discount;
    private Integer quantity;
    private Double price;

    public double getSubTotal() {
        return (price - discount) * quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
