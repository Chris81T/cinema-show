package de.geeksession.backend.validations;

import de.geeksession.backend.validations.validators.MovieNameValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = MovieNameValidator.class)
public @interface MovieName {

    String message();

    Class[] groups() default {};

    Class[] payload() default {};

}
