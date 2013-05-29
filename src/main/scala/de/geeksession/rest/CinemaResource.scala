package de.geeksession.rest

import java.util.List
import scala.collection.JavaConversions
import org.joda.time.DateTime
import de.geeksession.entities.CinemaRoom
import de.geeksession.entities.Movie
import de.geeksession.entities.Timetable
import de.geeksession.services.CinemaService
import javax.ejb.Stateless
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import de.geeksession.exception.CINEMA_Exception
import de.geeksession.backend.ExceptionHelper
import javax.ws.rs.ext.Provider
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.core.Response
import javax.ws.rs.core.Application
import javax.ws.rs.ApplicationPath

@ApplicationPath("rest")
class CinemaResourceActivator extends Application

@Provider
class NoResultMapper extends ExceptionMapper[CINEMA_Exception] {
  def toResponse(e: CINEMA_Exception): Response = {
    Response.status(500).entity(e.getMessage).build();
  }
}

@Stateless
@Path("cinema-api")
class CinemaResource extends ExceptionHelper {

  @Inject
  private var cinemaService: CinemaService = _

  @GET
  @Produces(Array("application/json"))
  @Path("movies/")
  def getMovies: List[Movie] = call({
    val movies = cinemaService.getMovies
    println("get movies = %s" format movies)
    JavaConversions.seqAsJavaList(movies)
  }, "what movies do you mean?")

  @GET
  @Produces(Array("application/json"))
  @Path("rooms/")
  def getCinemaRooms: List[CinemaRoom] = call({
    val cinemaRooms = cinemaService.getCinemaRooms
    println("get cinema rooms = %s" format cinemaRooms)
    JavaConversions.seqAsJavaList(cinemaRooms)
  }, "rooms? I have not any rooms for you!")

  @GET
  @Produces(Array("application/json"))
  @Path("/rooms/room/{id}")
  def getCinemaRoom(@PathParam("id") id: String): CinemaRoom = call({
    try {
      val longId = id.toLong
      val cinemaRoom = cinemaService.getCinemaRoom(longId)
      println("get cinema room = %s" format cinemaRoom)
      cinemaRoom getOrElse (null)
    } catch {
      case nfe: NumberFormatException => throw new CINEMA_Exception("could not transform string to long", nfe)
    }
  }, "which cinema room?")


  @GET
  @Produces(Array("application/json"))
  @Path("timetables/")
  def getTimetables: List[Timetable] = call({
    val timetables = cinemaService.getTimetables
    println("get timetables = %s" format timetables)
    JavaConversions.seqAsJavaList(timetables)
  }, "what are timetables?")

  @POST
  @Path("/rooms/room/{name}")
  @throws[CINEMA_Exception]
  def createRoom(@PathParam("name") name: String, @QueryParam("seats") countOfSeats: Int) = call({
    cinemaService.createRoom(name, countOfSeats)
  }, "I'm not a craft-man!")

  @POST
  @Path("/rooms/room/multiple/{name}")
  @throws[CINEMA_Exception]
  def createRooms(@PathParam("name") name: String, @QueryParam("seats") countOfSeats: Int,
                  @QueryParam("count") count: Int) = call({
    println("optional query param count is given with %s" format count)
    for (_ <- 0 to count + 1) {
      println("create new one.....")
      cinemaService.createRoom(name, countOfSeats)
    }
  }, "I'm not a craft-man!")

  @POST
  @Produces(Array("application/json"))
  @Path("rooms/room/{name}/length/{length}")
  def createMovie(@PathParam("name") name: String, @PathParam("length") length: Int,
                  published: DateTime) = call({
    cinemaService.createMovie(name, length, published)
  }, "I am not a constructor!")

  @POST
  @Produces(Array("application/json"))
  @Path("timetables/timetable/{name}")
  def createTimetable(movie: Movie, cinemaRoom: CinemaRoom, from: DateTime, to: DateTime) = call({
    cinemaService.createTimetable(movie, cinemaRoom, from, to)
  }, "Again, what is a timetable, Nerd!")


}
  
  
