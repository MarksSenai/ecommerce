package com.ecommerce.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.ecommerce.domains.User;
import com.ecommerce.domains.enums.UserType;
import com.ecommerce.dto.UserDTO;
import com.ecommerce.dto.UserNewDTO;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.resources.exceptions.FieldMessage;
import com.ecommerce.services.validation.utils.BR;

public class UserUpdateValidator implements ConstraintValidator<UserUpdate, UserDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UserUpdate userInsert) {
    }
    @Override
    public boolean isValid(UserDTO objDTO, ConstraintValidatorContext context) {

        @SuppressWarnings("unchecked")
        Map<String, String> map =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long uriId = Long.parseLong(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();
        // inclua os testes aqui, inserindo erros na lista

        User user = userRepository.findByEmail(objDTO.getEmail());
        if(user != null && !user.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "Email já existente!"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}