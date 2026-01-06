package peaksoft.instogram.validation.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import peaksoft.instogram.validation.UsernameValidation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UsernameValidation.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameAnnValid {
    String message() default "username not be empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
