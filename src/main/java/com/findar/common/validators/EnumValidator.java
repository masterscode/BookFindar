package com.findar.common.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String valueForValidation, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(valueForValidation)) return true;


        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (Objects.nonNull(enumValues)) {
            for (Object enumValue : enumValues) {
                if (valueMatchesEnumValue(valueForValidation, enumValue)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean valueMatchesEnumValue(String valueForValidation, Object enumValue) {
        String enumValueString = enumValue.toString();
        return this.annotation.ignoreCase()
                ? valueForValidation.equalsIgnoreCase(enumValueString)
                : valueForValidation.equals(enumValueString);
    }
}
