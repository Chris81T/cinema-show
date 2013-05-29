package de.geeksession.backend

import javax.ejb.Stateless
import javax.ejb.TransactionAttribute
import javax.ejb.TransactionAttributeType
import de.geeksession.entities.Movie
import de.geeksession.entities.Timetable
import de.geeksession.entities.CinemaRoom
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import de.geeksession.entities.AbstractEntity
import scala.collection.JavaConversions
import de.geeksession.exception.CINEMA_Exception

abstract class CommonOps[E <: AbstractEntity](entityClass: Class[E]) extends ExceptionHelper {

  @PersistenceContext
  protected var entityManager: EntityManager = _

  protected def asScalaList(javaList: java.util.List[E]): List[E] =
    JavaConversions.asScalaBuffer(javaList).toList

  @throws[CINEMA_Exception]
  def create(entity: E): E = {
    call({
      entityManager.persist(entity)
    }, "Could not persist")
    entity
  }

  @throws[CINEMA_Exception]
  def remove(id: Long) {
    find(id) match {
      case Some(entity) => remove(entity)
      case None => throw new CINEMA_Exception("Could not remove entity with id = %s" format id)
    }
  }

  @throws[CINEMA_Exception]
  def remove(entity: E) {
    call(entityManager.remove(entity), "Something went wrong during remove opertation")
  }

  @throws[CINEMA_Exception]
  def find(entity: E): Option[E] = {
    find(entity.getId)
  }

  @throws[CINEMA_Exception]
  def find(id: Long): Option[E] = {
    Option(call(entityManager.find(entityClass, id), "Could not find entity"))
  }

  @throws[CINEMA_Exception]
  def findAll: List[E] = {
    val query = "SELECT e FROM %s e" format entityClass.getSimpleName
    asScalaList(call(entityManager.createQuery(query).getResultList.asInstanceOf[java.util.List[E]],
      "Could not find all entities"))
  }

  @throws[CINEMA_Exception]
  def merge(entity: E): E = {
    call(entityManager.merge(entity), "Could not merge entity")
  }

}

@Stateless
@TransactionAttribute(value = TransactionAttributeType.MANDATORY)
class MovieDAO extends CommonOps[Movie](classOf[Movie]) {


}

@Stateless
@TransactionAttribute(value = TransactionAttributeType.MANDATORY)
class CinemaRoomDAO extends CommonOps[CinemaRoom](classOf[CinemaRoom]) {


}

@Stateless
@TransactionAttribute(value = TransactionAttributeType.MANDATORY)
class TimetableDAO extends CommonOps[Timetable](classOf[Timetable]) {


}

