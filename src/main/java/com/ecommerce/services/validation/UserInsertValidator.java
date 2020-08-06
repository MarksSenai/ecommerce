package com.ecommerce.services.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ecommerce.domains.enums.UserType;
import com.ecommerce.dto.UserNewDTO;
import com.ecommerce.resources.exceptions.FieldMessage;
import com.ecommerce.services.validation.utils.BR;

public class UserInsertValidator implements ConstraintValidator<UserInsert, UserNewDTO> {
    @Override
    public void initialize(UserInsert userInsert) {
    }
    @Override
    public boolean isValid(UserNewDTO objDTO, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        // inclua os testes aqui, inserindo erros na lista
        if (objDTO.getUserType().equals((UserType.CLIENT_PERSONAL_ENTITY).getCod()) &&
                !BR.isValidCPF(objDTO.getUserCode())) {
            list.add(new FieldMessage("userCode", "CPF Inválido"));
        }
        if (objDTO.getUserType().equals((UserType.CLIENT_LEGAL_ENTITY).getCod()) &&
                !BR.isValidCNPJ(objDTO.getUserCode())) {
            list.add(new FieldMessage("userCode", "CNPJ Inválido"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}