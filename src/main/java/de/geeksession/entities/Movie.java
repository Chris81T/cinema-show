package de.geeksession.entities;

import de.geeksession.backend.validations.MovieName;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.util.Calendar;

@Entity
@Access(AccessType.PROPERTY)
@Table(name = "CI_MOVIE")
public class Movie extends AbstractEntity {

    private static final long serialVersionUID = -5491179500446822401L;
    private String name = null;
    private Integer length = null;

    @Access(AccessType.FIELD)
    @Temporal(TemporalType.DATE)
    @Past(message = "Movie published times must be in the past!")
    private Calendar published = null;

    @MovieName(message = "A movie must contain at least 3 words")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Min(value = 20, message = "Movielength must be at least 20 minutes!")
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Transient
    public DateTime getPublished() {
        return new DateTime(published);
    }

    public void setPublished(DateTime published) {
        this.published = published.toGregorianCalendar();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Movie [name=");
        builder.append(name);
        builder.append(", length=");
        builder.append(length);
        builder.append(", published=");
        builder.append(published);
        builder.append("]");
        builder.append(" -- ID=");
        builder.append(getId());
        return builder.toString();
    }


}
