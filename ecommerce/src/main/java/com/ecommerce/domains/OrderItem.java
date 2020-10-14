package com.ecommerce.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();
    private Double discount;
    private Integer quantity;
    private Double price;

    public OrderItem() {
    }

    public OrderItem(Orders order, Product product, Double discount, Integer quantity, Double price) {
        super();
        id.setOrder(order);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
    }

    public double getSubTotal() {
        return (price - discount) * quantity;
    }

    @JsonIgnore
    public Orders getOrder() {
        return id.getOrder();
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public OrderItemPK getId() {
        return id;
    }

    public void setId(OrderItemPK id) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem orderItem = (OrderItem) obj;
        if (id == null) {
            if (orderItem.id != null)
                return false;
        } else if (!id.equals(orderItem.id))
            return false;
        return true;
    }

//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Produto: ");
//        sb.append(getProduct().getName());
//        sb.append(", Quantidade: ");
//        sb.append(getQuantity());
//        sb.append(", Preço unitário");
//        sb.append(getPrice());
//        sb.append(", Subtotal: ");
//        sb.append(getSubTotal());
//        sb.append("\n");
//        return sb.toString();
//    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
