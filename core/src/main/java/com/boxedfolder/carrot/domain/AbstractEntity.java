package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.util.LocalDateTimeSerializer;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@MappedSuperclass
public abstract class AbstractEntity {
    @JsonView(View.General.class)
    @Size(min = 1)
    @NotNull
    private String name;

    @JsonView(View.Meta.class)
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @JsonView(View.Meta.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "date_created", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @JsonView(View.Meta.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
