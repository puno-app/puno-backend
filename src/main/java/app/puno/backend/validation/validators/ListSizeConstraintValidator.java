package app.puno.backend.validation.validators;

import app.puno.backend.validation.ListSize;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListSizeConstraintValidator implements ConstraintValidator<ListSize, List<?>> {

	private int min, max;

	@Override
	public boolean isValid(List<?> value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		return value.size() >= min && value.size() <= max;
	}

	@Override
	public void initialize(ListSize constraintAnnotation) {
		this.min = constraintAnnotation.min();
		this.max = constraintAnnotation.max();
	}

}
