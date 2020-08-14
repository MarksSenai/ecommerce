package com.ecommerce.domains.enums;

public enum UserType {

    SYSTEM_ADMIN(1, "Administrador do sistema"),
    USER_ADMIN(2, "Usuário administrador"),
    CLIENT_PERSONAL_ENTITY(3, "Pessoa Física"),
    CLIENT_LEGAL_ENTITY(4, "Pessoa Jurídica");

    private int cod;
    private String description;

    private UserType(int cod, String description) {
        this.cod = cod;
        this.description = description;
    }

    public int getCod() {
        return cod;
    }

    public String getDescription() {
        return description;
    }

    public static UserType toEnum(Integer cod) {
        if (cod == null) {
            return null;
        }

        for (UserType ct : UserType.values()) {
            if (cod.equals(ct.getCod())) {
                return  ct;
            }
        }

        throw new IllegalArgumentException("Invalid id: " + cod);
    }
}
