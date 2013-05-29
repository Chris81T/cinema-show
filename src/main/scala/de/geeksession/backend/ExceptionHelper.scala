package de.geeksession.backend

import de.geeksession.exception.CINEMA_Exception
import javax.transaction.UserTransaction
import de.geeksession.entities.AbstractEntity
import javax.validation.Validation
import scala.collection.JavaConversions
import de.geeksession.exception.CINEMA_ValidationException

abstract class ExceptionHelper {

  @throws[CINEMA_Exception]
  def call[T](fx: => T, errorMsg: String): T = utxCall(None, fx, errorMsg)

  @throws[CINEMA_Exception]
  def utxCall[T](utx: UserTransaction, fx: => T, errorMsg: String): T =
    utxCall(Option(utx), fx, errorMsg)

  @throws[CINEMA_Exception]
  def utxCall[T](utx: Option[UserTransaction], fx: => T, errorMsg: String): T = {
    try {
      utx map (tx => {
        println("===================> BEGIN USER TRANSACTION ...")
        tx.begin
      })
      val retVal = fx
      utx map (tx => {
        println("===================> COMMIT USER TRANSACTION ...")
        tx.commit
      })
      retVal
    } catch {
      case v: CINEMA_ValidationException =>
        println("VALIDATION ERROR'S ==> %s" format v.getValidationMessages)
        throw new CINEMA_Exception("VALIDATION ERROR -> " + errorMsg, v)
      case e: Exception =>
        println("!! !! !! !! !! !! !! !! !! !! !! !! !!")
        println("CATCHED THROWN EXCEPTION WITH MESSAGE = %s " +
          "AND GIVEN ERROR MESSAGE = %s" format(e.getMessage, errorMsg))
        println("!! !! !! !! !! !! !! !! !! !! !! !! !!")

        utx map (tx => {
          println("===================> ROLLBACK USER TRANSACTION !!!")
          tx.rollback
        })

        throw new CINEMA_Exception(errorMsg, e)
    }
  }

  @throws[CINEMA_ValidationException]
  def validate(entities: List[AbstractEntity]) {
    val validator = Validation.buildDefaultValidatorFactory.getValidator
    val messages = for {
      entity <- entities
      violation <- JavaConversions.asScalaSet(validator.validate(entity))
    } yield violation.getMessage

    if (!messages.isEmpty) {
      throw new CINEMA_ValidationException(JavaConversions.asJavaList(messages))
    }
  }

}