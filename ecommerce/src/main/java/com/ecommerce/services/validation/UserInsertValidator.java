package com.ecommerce.services.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.domains.User;
import com.ecommerce.domains.enums.Profile;
import com.ecommerce.dto.UserNewDTO;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.resources.exceptions.FieldMessage;
import com.ecommerce.services.validation.utils.BR;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserNewDTO> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UserInsert userInsert) {
    }
    @Override
    public boolean isValid(UserNewDTO objDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        // inclua os testes aqui, inserindo erros na lista
        if (objDTO.getProfiles().equals((Profile.CLIENT_PERSONAL_ENTITY).getCod()) &&
                !BR.isValidCPF(objDTO.getUserCode())) {
            list.add(new FieldMessage("userCode", "CPF Inválido"));
        }
        if (objDTO.getProfiles().equals((Profile.CLIENT_LEGAL_ENTITY).getCod()) &&
                !BR.isValidCNPJ(objDTO.getUserCode())) {
            list.add(new FieldMessage("userCode", "CNPJ Inválido"));
        }

        User userMail = userRepository.findByEmail(objDTO.getEmail());
        if(userMail != null) {
            list.add(new FieldMessage("email", "Email já existente!"));
        }

        User userCode = userRepository.findByUserCode(objDTO.getUserCode());
        if (userCode != null) {
            if (objDTO.getProfiles().equals(Profile.CLIENT_PERSONAL_ENTITY.getCod())) {
                list.add(new FieldMessage("userCode", "CPF já existente!"));
            } else if (objDTO.getProfiles().equals(Profile.CLIENT_LEGAL_ENTITY.getCod())) {
                list.add(new FieldMessage("userCode", "CNPJ já existente!"));
            }
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}