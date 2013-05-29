package de.geeksession.entities;

import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Calendar;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "CI_TIMETABLE")
public class Timetable extends AbstractEntity {

    private static final long serialVersionUID = -645940547541445367L;

    @Access(AccessType.FIELD)
    @Future(message = "Beginning must be in the future")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar beginning = null;

    @Access(AccessType.FIELD)
    @Future(message = "End must be in the future")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar end = null;

    private CinemaRoom cinemaRoom = null;
    private Movie movie = null;

    @ManyToOne
    @NotNull(message = "No CinemaRoom is set!")
    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @ManyToOne
    @NotNull(message = "No Movie is set!")
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Transient
    public DateTime getFrom() {
        return new DateTime(beginning);
    }

    public void setFrom(DateTime from) {
        this.beginning = from.toGregorianCalendar();
    }

    @Transient
    public DateTime getTo() {
        return new DateTime(end);
    }

    public void setTo(DateTime to) {
        this.end = to.toGregorianCalendar();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Timetable [from=");
        builder.append(beginning);
        builder.append(", to=");
        builder.append(end);
        builder.append(", cinemaRoom=");
        builder.append(cinemaRoom);
        builder.append(", movie=");
        builder.append(movie);
        builder.append("]");
        builder.append(" -- ID=");
        builder.append(getId());
        return builder.toString();
    }


}
