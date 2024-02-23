package com.findar.common.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class EnumListValidator implements ConstraintValidator<ValidEnum, List<String>> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(List<String> valuesForValidation, ConstraintValidatorContext constraintValidatorContext) {
        if (CollectionUtils.isEmpty(valuesForValidation)) return true;


        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (String value : valuesForValidation) {
                boolean valueMatchesEnum = false;
                for (Object enumValue : enumValues) {
                    if (valueMatchesEnumValue(value, enumValue)) {
                        valueMatchesEnum = true;
                        break;
                    }
                }
                if (!valueMatchesEnum) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean valueMatchesEnumValue(String valueForValidation, Object enumValue) {
        String enumValueString = enumValue.toString();
        return this.annotation.ignoreCase()
                ? valueForValidation.equalsIgnoreCase(enumValueString)
                : valueForValidation.equals(enumValueString);
    }
}
