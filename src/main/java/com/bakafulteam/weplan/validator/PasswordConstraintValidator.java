package com.bakafulteam.weplan.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordConstraintValidator implements ConstraintValidator<IsValidPassword, String> {
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$";
    private Pattern pattern;
    private Matcher matcher;

    public PasswordConstraintValidator(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }
    @Override
    public void initialize(IsValidPassword isValidPassword) {
        isValidPassword.message();
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext cxt) {
        if(password==null){
            return false;
        }
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
}
