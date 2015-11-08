package com.boxedfolder.carrot.domain.analytics;

import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.Event;
import com.boxedfolder.carrot.domain.general.AbstractEntity;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "analytics_log")
@Entity
public class AnalyticsLog extends AbstractEntity {
    @JsonView(View.Meta.class)
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "event")
    private Event occuredEvent;

    @JsonView(View.Meta.class)
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "beacon")
    private Beacon beacon;

    @JsonView(View.Meta.class)
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "app")
    private App app;

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

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
