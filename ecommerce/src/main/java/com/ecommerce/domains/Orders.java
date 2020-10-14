package com.ecommerce.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern="dd/MM/yyyy HH:mm")
    private Date instant;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private Address deliveryAddress;

    @OneToMany(mappedBy = "id.order")
    private List<OrderItem> items = new ArrayList<>();

    public Orders() {
    }

    public Orders(Long id, Date instant, User user, Address deliveryAddress) {
        super();
        this.id = id;
        this.instant = instant;
        this.user = user;
        this.deliveryAddress = deliveryAddress;
    }

    public double getTotalValue() {
        double sum = 0.0;
        for (OrderItem oi : items) {
            sum = sum + oi.getSubTotal();
        }
        return sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id.equals(orders.id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Orders{");
        sb.append("Pedido: ");
        sb.append(getId());
        sb.append(", Horario do pedido: ");
        sb.append(getInstant());
        sb.append(", Client: ");
        sb.append(getUser().getName());
        sb.append(", Situação do pagamento: ");
        sb.append(getPayment().getStatus().getDescription());
        sb.append("\nDetalhes:\n");
        for (OrderItem oi : getItems()) {
            sb.append(oi.toString());
        }
        sb.append("Valor total: ");
        sb.append(getTotalValue());

        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
