package peaksoft.instogram.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import peaksoft.instogram.validation.validator.UsernameAnnValid;

public class UsernameValidation implements ConstraintValidator<UsernameAnnValid, String> {
    @Override
    public void initialize(UsernameAnnValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(username == null) return true; //null не проверяем - это задача @NotNull
        return !username.trim().isEmpty();
    }
}
