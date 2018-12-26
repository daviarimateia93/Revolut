package com.revolut.account.infrastructure.commons.validation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.revolut.account.core.commons.validation.Violation;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.configuration.annotation.AnnotationsConfigurer;
import net.sf.oval.configuration.annotation.BeanValidationAnnotationsConfigurer;

public class OvalValidator implements com.revolut.account.core.commons.validation.Validator {

    private Validator validator;

    public OvalValidator() {
        this.validator = new Validator(
                new AnnotationsConfigurer(), new BeanValidationAnnotationsConfigurer());
    }

    @Override
    public List<Violation> validate(Object object) {
        return validator.validate(object)
                .stream()
                .map(this::translate)
                .collect(Collectors.toList());
    }

    private Violation translate(ConstraintViolation constraintViolation) {

        List<Violation> causes = Arrays
                .stream(Optional.ofNullable(constraintViolation.getCauses()).orElse(new ConstraintViolation[] {}))
                .map(this::translate)
                .collect(Collectors.toList());

        Violation violation = new Violation();
        violation.setMessage(constraintViolation.getMessage());
        violation.setMessageTemplate(constraintViolation.getMessageTemplate());
        violation.setInvalidValue(constraintViolation.getInvalidValue());
        violation.setCauses(causes);

        return violation;
    }
}
