package com.webstore.model.util;

import jakarta.validation.*;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class ValidationUtil {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public static void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

