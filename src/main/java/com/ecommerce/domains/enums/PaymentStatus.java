package com.ecommerce.domains.enums;

public enum PaymentStatus {
    PAID(1, "Pago"),
    PENDING(2, "Pendente"),
    CANCELED(3, "Cancelado");

    private int cod;
    private String description;

    PaymentStatus(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentStatus toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (PaymentStatus ps : PaymentStatus.values()) {
            if (cod.equals(ps.getCod())) {
                return  ps;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + cod);
    }
}