package de.geeksession.services

import javax.ejb.Stateless
import de.geeksession.backend.CinemaRoomDAO
import javax.inject.Inject
import de.geeksession.backend.MovieDAO
import de.geeksession.entities.CinemaRoom
import de.geeksession.backend.ExceptionHelper
import de.geeksession.exception.CINEMA_Exception
import org.joda.time.DateTime
import de.geeksession.entities.Movie
import de.geeksession.entities.Timetable
import de.geeksession.backend.TimetableDAO

@Stateless
class CinemaService extends ExceptionHelper {

  @Inject
  private var movieDAO: MovieDAO = _

  @Inject
  private var cinemaRoomDAO: CinemaRoomDAO = _

  @Inject
  private var timetableDAO: TimetableDAO = _

  @throws[CINEMA_Exception]
  def getMovies(): List[Movie] = call({
    movieDAO.findAll
  }, "Could not get all movies")

  @throws[CINEMA_Exception]
  def getCinemaRooms(): List[CinemaRoom] = call({
    cinemaRoomDAO.findAll
  }, "Could not get all cinema rooms")

  @throws[CINEMA_Exception]
  def getCinemaRoom(id: Long): Option[CinemaRoom] = call({
    cinemaRoomDAO.find(id)
  }, "Could not get all cinema rooms")

  @throws[CINEMA_Exception]
  def getTimetables(): List[Timetable] = call({
    timetableDAO.findAll
  }, "Could not get all timetables")

  @throws[CINEMA_Exception]
  def createRoom(name: String, countOfSeats: Int): CinemaRoom = {
    call({
      val room = new CinemaRoom
      room.setName(name)
      room.setCountOfSeats(countOfSeats)

      validate(room :: Nil)

      cinemaRoomDAO.create(room)
    }, "Could not create the room with name = %s" format name)
  }

  @throws[CINEMA_Exception]
  def createMovie(name: String, length: Int, published: DateTime): Movie = {
    call({
      val movie = new Movie
      movie.setName(name)
      movie.setLength(length)
      movie.setPublished(published)

      validate(movie :: Nil)

      movieDAO.create(movie)
    }, "Could not create the movie with the name = %s" format name)
  }

  @throws[CINEMA_Exception]
  def createTimetable(movie: Movie, cinemaRoom: CinemaRoom, from: DateTime, to: DateTime): Timetable = {
    call({
      val timetable = new Timetable
      timetable.setMovie(movie)
      timetable.setCinemaRoom(cinemaRoom)
      timetable.setFrom(from)
      timetable.setTo(to)

      validate(timetable :: Nil)

      timetableDAO.create(timetable)
    }, "Could not create timetable")
  }
}