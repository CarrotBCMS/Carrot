package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.*;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "app")
@Entity
public class App extends AbstractEntity {
    @JsonIgnore
    @Column(columnDefinition = "BINARY(16)", name = "app_key")
    private UUID applicationKey;

    @JsonView(View.MetaSync.class)
    @ManyToMany(mappedBy = "apps", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Beacon> beacons = new HashSet<Beacon>();

    @ManyToMany(mappedBy = "apps", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Event> events = new HashSet<Event>();

    @JsonView(View.General.class)
    public UUID getApplicationKey() {
        return applicationKey;
    }

    @JsonIgnore
    public void setApplicationKey(UUID applicationKey) {
        this.applicationKey = applicationKey;
    }

    public Set<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(Set<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
