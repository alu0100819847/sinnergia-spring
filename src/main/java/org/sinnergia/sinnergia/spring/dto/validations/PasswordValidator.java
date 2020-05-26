package org.sinnergia.sinnergia.spring.dto.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class PasswordValidator implements ConstraintValidator<Password, String[]> {

   @Override
   public void initialize(Password constraintAnnotation) {
      // Empty
   }

   @Override
   public boolean isValid(String[] password, ConstraintValidatorContext constraintValidatorContext) {
      if(!password[0].equals(password[1])){
         throw new IllegalArgumentException("Illegal method signature, "
                 + "expected parameter of type Reservation.");
      }
      return true;
   }
}
