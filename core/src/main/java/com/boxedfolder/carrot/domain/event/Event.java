package com.boxedfolder.carrot.domain.event;

import com.boxedfolder.carrot.domain.AbstractEntity;
import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.boxedfolder.carrot.domain.util.LocalDateTimeDeserializer;
import com.boxedfolder.carrot.domain.util.LocalDateTimeSerializer;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NotificationEvent.class, name = "notification"),
        @JsonSubTypes.Type(value = TextEvent.class, name = "text")
})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public abstract class Event extends AbstractEntity {
    public static final double TYPE_ENTER = 0;
    public static final double TYPE_EXIT = 1;
    public static final double TYPE_BOTH = 2;

    /**
     * Whether event is active or not
     */
    @JsonView(View.General.class)
    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;

    /**
     * Retrigger threshold (in minutes)
     */
    @JsonView(View.General.class)
    private float threshold;

    /**
     * Sets a start date when this event is going to be active.
     */
    @JsonView(View.General.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime scheduledStartDate;

    /**
     * Sets an end date when this event is going to be active.
     */
    @JsonView(View.General.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime scheduledEndDate;

    /**
     * Type of event. May be one of the following types:
     * TYPE_ENTER(0)
     * TYPE_EXIT(1)
     * TYPE_BOTH(2)
     */
    @JsonView(View.General.class)
    @Column(nullable = false)
    private int eventType;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Beacon> beacons = new ArrayList<Beacon>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<App> apps = new ArrayList<App>();

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public LocalDateTime getScheduledStartDate() {
        return scheduledStartDate;
    }

    public void setScheduledStartDate(LocalDateTime scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    public LocalDateTime getScheduledEndDate() {
        return scheduledEndDate;
    }

    public void setScheduledEndDate(LocalDateTime scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public List<App> getApps() {
        return apps;
    }

    public void setApps(List<App> apps) {
        this.apps = apps;
    }
}
