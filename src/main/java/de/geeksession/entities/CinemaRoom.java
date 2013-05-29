package de.geeksession.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Entity
@Table(name = "CI_CINEMA_ROOM")
public class CinemaRoom extends AbstractEntity {


    private static final long serialVersionUID = 7551860772973424886L;
    private String name = null;
    private Integer countOfSeats = null;

    @Size(min = 3, max = 16, message = "Roomname must be between 3 and 16 digits")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Max(value = 800, message = "to many set seats")
    public Integer getCountOfSeats() {
        return countOfSeats;
    }

    public void setCountOfSeats(Integer countOfSeats) {
        this.countOfSeats = countOfSeats;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CinemaRoom [name=");
        builder.append(name);
        builder.append(", countOfSeats=");
        builder.append(countOfSeats);
        builder.append("]");
        builder.append(" -- ID=");
        builder.append(getId());
        return builder.toString();
    }


}
