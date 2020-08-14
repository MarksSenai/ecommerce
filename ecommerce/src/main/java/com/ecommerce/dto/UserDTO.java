package com.ecommerce.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.ecommerce.domains.User;
import com.ecommerce.services.validation.UserUpdate;

@UserUpdate
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @NotEmpty
    @Length(min = 5, max=120, message = "Tamanho deve ser entre 5 e 120 caracteres")
    private String name;
    @NotEmpty(message="Preenchimento de e-mail obrigatório!")
    @Email(message="Email inválido")
    private String email;

    public UserDTO(){}

    public UserDTO(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
