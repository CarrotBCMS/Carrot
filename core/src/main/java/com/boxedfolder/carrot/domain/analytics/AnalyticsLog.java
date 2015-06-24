package com.boxedfolder.carrot.domain.analytics;

import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.event.Event;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "analytics_log")
@Entity
public class AnalyticsLog {
    @Column(name = "date")
    @NotNull
    private DateTime dateTime;

    @OneToOne
    @Column(name = "event")
    @NotNull
    private Event occuredEvent;

    @OneToOne
    @Column(name = "beacon")
    @NotNull
    private Beacon beacon;
}
