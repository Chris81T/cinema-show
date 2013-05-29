package de.geeksession.backend.validations.validators

import javax.validation.ConstraintValidator
import de.geeksession.backend.validations.MovieName
import javax.validation.ConstraintValidatorContext

class MovieNameValidator extends ConstraintValidator[MovieName, String] {

  /**
   * do nothing. It is possible to get needed values inside from the annotation
   * see example:
   * http://goldenpackagebyanuj.blogspot.de/2013/05/custom-jsr-303-validation-using-constraintValidator.html
   */
  override def initialize(annotation: MovieName) {}

  /**
   * check, if the given name contains three words for instance
   */
  override def isValid(name: String, context: ConstraintValidatorContext): Boolean =
    name.split(" ").length >= 3

}