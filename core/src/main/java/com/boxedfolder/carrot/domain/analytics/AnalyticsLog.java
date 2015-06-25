package com.boxedfolder.carrot.domain.analytics;

import com.boxedfolder.carrot.domain.AbstractEntity;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.event.Event;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "analytics_log")
@Entity
public class AnalyticsLog extends AbstractEntity {
    @Column(name = "date", length = 273)
    @NotNull
    private DateTime dateTime;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "event")
    @NotNull
    private Event occuredEvent;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "beacon")
    @NotNull
    private Beacon beacon;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Event getOccuredEvent() {
        return occuredEvent;
    }

    public void setOccuredEvent(Event occuredEvent) {
        this.occuredEvent = occuredEvent;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
