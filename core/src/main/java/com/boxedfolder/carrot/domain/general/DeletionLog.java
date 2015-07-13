package com.boxedfolder.carrot.domain.general;

import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "deletion_log")
@Entity
public class DeletionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_time")
    private DateTime dateTime;

    private Class type;

    public Long getId() {
        return id;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
