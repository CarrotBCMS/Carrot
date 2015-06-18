package com.boxedfolder.carrot.domain.event;

import com.boxedfolder.carrot.domain.AbstractEntity;
import com.boxedfolder.carrot.domain.App;
import com.boxedfolder.carrot.domain.Beacon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "eventType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NotificationEvent.class),
        @JsonSubTypes.Type(value = TextEvent.class)
})
@MappedSuperclass
public class Event extends AbstractEntity {
    public static final double TYPE_ENTER = 0;
    public static final double TYPE_EXIT = 1;
    public static final double TYPE_BOTH = 2;

    @JsonProperty
    private float threshold;

    @JsonProperty
    private LocalDateTime scheduledStartDate;

    @JsonProperty
    private LocalDateTime scheduledEndDate;

    @Column(nullable = false)
    private int eventType;

    @JsonIgnore
    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private List<Beacon> beacons = new ArrayList<Beacon>();

    @JsonIgnore
    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
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
