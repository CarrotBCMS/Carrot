package com.boxedfolder.carrot.domain.analytics;

import com.boxedfolder.carrot.domain.AbstractEntity;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "analytics_log")
@Entity
public class AnalyticsLog extends AbstractEntity {
    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "event")
    @NotNull
    private Event occuredEvent;

    @JsonIgnore
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "beacon")
    @NotNull
    private Beacon beacon;

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
