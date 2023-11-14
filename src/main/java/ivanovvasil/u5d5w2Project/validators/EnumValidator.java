package ivanovvasil.u5d5w2Project.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
  private Enum<?>[] enums;

  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    enums = constraintAnnotation.enumClass().getEnumConstants();
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.trim().isEmpty()) {
      return false;
    }

    for (Enum<?> enumValue : enums) {
      if (enumValue.name().equals(value)) {
        return true;
      }
    }
    return false;
  }
}
