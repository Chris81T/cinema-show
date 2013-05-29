package de.geeksession.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = -1498636291105680541L;

    /**
     * if this technical id is "null", then it indicates a non persisted entity
     * object!
     */
    private Long id = null;

    /**
     * this is the primary key member. It is of type string, while manually (
     * from appropriate DAO) a generated universal unique id will be set.
     *
     * @return the universal unique id for this persisted entity. If id is NULL,
     *         then the entity is transient (not persisted)!
     */
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * ATTENTION:
     * Do not replace it with the generated-one of the eclipse tools! It is
     * adapted to call the super implementation, if the id (by transient created
     * object's) is NULL!
     */
    @Override
    public int hashCode() {
        if (id == null) {
            return super.hashCode();
        }
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    /**
     * ATTENTION:
     * Do not replace it with the generated-one of the eclipse tools! It is
     * adapted to call the super implementation, if the id (by transient created
     * object's) is NULL!
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AbstractEntity other = (AbstractEntity) obj;
        if (id == null) {
            return super.equals(obj);
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AbstractEntity [id (UUID)=");
        builder.append(id);
        builder.append(", toString()=");
        builder.append(super.toString());
        builder.append("]");
        return builder.toString();
    }
}
