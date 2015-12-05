/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
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

package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.general.AbstractUserRelatedEntity;
import com.boxedfolder.carrot.domain.util.DateTimeDeserializer;
import com.boxedfolder.carrot.domain.util.DateTimeSerializer;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NotificationEvent.class, name = "notification"),
        @JsonSubTypes.Type(value = TextEvent.class, name = "text")
})
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity(name = "event")
public abstract class Event extends AbstractUserRelatedEntity {
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
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime scheduledStartDate;

    /**
     * Sets an end date when this event is going to be active.
     */
    @JsonView(View.General.class)
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime scheduledEndDate;

    /**
     * Type of event. May be one of the following types:
     * TYPE_ENTER(0)
     * TYPE_EXIT(1)
     * TYPE_BOTH(2)
     */
    @JsonView(View.General.class)
    @Column(nullable = false)
    private int eventType;

    @JsonView(View.General.class)
    @JsonIgnoreProperties({"uuid", "major", "minor", "dateUpdated", "dateCreated"})
    @JoinTable(name = "event_beacon", joinColumns = {
            @JoinColumn(name = "event_id", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "beacon_id", nullable = false)
    })
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Beacon> beacons = new HashSet<>();

    @JsonView(View.Client.class)
    @JsonIgnoreProperties({"applicationKey", "dateUpdated", "dateCreated"})
    @JoinTable(name = "event_app", joinColumns = {
            @JoinColumn(name = "event_id", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "app_id", nullable = false)
    })
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<App> apps = new HashSet<>();

    @OneToMany(mappedBy = "occuredEvent", cascade = CascadeType.REMOVE)
    private Set<AnalyticsLog> logs;

    @PreRemove
    protected void onRemove() {
        for (App app : apps) {
            app.getEvents().remove(this);
        }

        for (Beacon beacon : beacons) {
            beacon.getEvents().remove(this);
        }
    }

    public float getThreshold() {
        return threshold;
    }

    public void setThreshold(float threshold) {
        this.threshold = threshold;
    }

    public DateTime getScheduledStartDate() {
        return scheduledStartDate;
    }

    public void setScheduledStartDate(DateTime scheduledStartDate) {
        this.scheduledStartDate = scheduledStartDate;
    }

    public DateTime getScheduledEndDate() {
        return scheduledEndDate;
    }

    public void setScheduledEndDate(DateTime scheduledEndDate) {
        this.scheduledEndDate = scheduledEndDate;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public Set<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(Set<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Set<App> getApps() {
        return apps;
    }

    public void setApps(Set<App> apps) {
        this.apps = apps;
    }
}
