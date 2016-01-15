/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.boxedfolder.carrot.domain.general;

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
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator") // Table for portability
    @TableGenerator(name = "id_generator", initialValue = 1, allocationSize = 1) // Avoid big allocation sizes
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

    @PrePersist
    protected void onCreate() {
        dateCreated = new DateTime();
        dateUpdated = new DateTime();
    }

    @PreUpdate
    protected void onUpdate() {
        dateUpdated = new DateTime();
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
        if (object.getClass() != getClass()) {
            return false;
        }

        AbstractEntity other = (AbstractEntity)object;

        if (getId() == null || other.getId() == null) {
            return false;
        }

        return getId().longValue() == other.getId().longValue();
    }

    @Override
    public int compareTo(AbstractEntity object) {
        return dateCreated.compareTo(object.getDateCreated());
    }
}
