package com.bakafulteam.weplan.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsValidPassword {
    String message() default "Password must meet complexity requirement";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
