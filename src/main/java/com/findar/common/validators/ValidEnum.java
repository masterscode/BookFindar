package com.findar.common.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {EnumValidator.class, EnumListValidator.class})
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum
{
    String message() default "Invalid value. This is not permitted.";
     
    Class<?>[] groups() default {};
  
    Class<? extends Payload>[] payload() default {};
     
    Class<? extends java.lang.Enum<?>> enumClass();
     
    boolean ignoreCase() default false;
}
