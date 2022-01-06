package app.puno.backend.validation;

import app.puno.backend.validation.validators.ListSizeConstraintValidator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ListSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ListSize {

	int min() default 1;
	int max() default Integer.MAX_VALUE;

	String message() default "Invalid number of items in list.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
