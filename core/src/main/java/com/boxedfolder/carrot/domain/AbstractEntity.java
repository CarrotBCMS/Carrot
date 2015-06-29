package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.util.DateTimeSerializer;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@MappedSuperclass
public abstract class AbstractEntity implements Comparable<AbstractEntity> {
    @JsonView(View.Meta.class)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @JsonView(View.Meta.class)
    @JsonSerialize(using = DateTimeSerializer.class)
    @Column(name = "date_created", nullable = false, updatable = false, length = 273)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime dateCreated;

    @JsonView(View.Meta.class)
    @JsonSerialize(using = DateTimeSerializer.class)
    @Column(name = "date_updated", length = 273)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime dateUpdated;

    public DateTime getDateCreated() {
        return dateCreated;
    }

    @JsonIgnore
    public void setDateCreated(DateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DateTime getDateUpdated() {
        return dateUpdated;
    }

    @JsonIgnore
    public void setDateUpdated(DateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int hashCode() {
        if (getId() == null) {
            return super.hashCode();
        }

        return getId().hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AbstractEntity)) {
            return false;
        }

        AbstractEntity other = (AbstractEntity)object;

        if (getId() == null && other.getId() != null) {
            return false;
        }
        if (getId() != null && other.getId() == null) {
            return false;
        }
        if (getId() == null && other.getId() == null) {
            return super.equals(object);
        }
        return getId().longValue() == other.getId().longValue();
    }

    @Override
    public int compareTo(AbstractEntity o) {
        return dateCreated.compareTo(o.getDateCreated());
    }
}
