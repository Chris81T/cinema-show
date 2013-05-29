package de.geeksession

import de.geeksession.services.CinemaService
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject
import org.joda.time.DateTime

@Singleton
@Startup
class CinemaStartupClient {

  @Inject
  private var cinemaService: CinemaService = _

  @PostConstruct
  def doIt {
    println("###########################")
    println("DO IT : = )")
    println("###########################")

    //    val movie = new Movie
    //    movie.setName("Star Trek - Into the darkness")
    //    movie.setLength(137)
    //
    val published = new DateTime
    //    published.withDate(2013, 5, 11)
    //    movie.setPublished(published)
    //
    //    val bigRoom = new CinemaRoom
    //    bigRoom.setName("IMAX 4D")
    //    bigRoom.setCountOfSeats(950)
    //
    //    println("CREATE CINEMA ROOM --> " + cinemaRoomDAO.create(bigRoom))
    //    println("CREATE THE FIRST MOVIE --> " + movieDAO.create(movie))

    try {
      val room = cinemaService.createRoom("IMAX 5D", 799)
      val movie = cinemaService.createMovie("Indiana Jones is amazing!", 89,
        new DateTime(1987, 2, 2, 0, 0))
      cinemaService.createTimetable(movie,
        room, new DateTime(2013, 6, 10, 20, 0), new DateTime(2013, 6, 1, 0, 0))
    } catch {
      case t: Throwable =>
        println("Dat war nix...=====> %s" format t.getMessage)
      //throw t
    }

  }

}