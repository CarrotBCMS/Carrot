package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Entity
public class App extends AbstractEntity {
    @JsonIgnore
    @Type(type = "uuid-binary")
    private UUID applicationKey;

    @JsonView(View.MetaSync.class)
    @ManyToMany(mappedBy = "apps", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Beacon> beacons = new ArrayList<Beacon>();

    @ManyToMany(mappedBy = "apps", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<Event>();

    @JsonView(View.General.class)
    public UUID getApplicationKey() {
        return applicationKey;
    }

    @JsonIgnore
    public void setApplicationKey(UUID applicationKey) {
        this.applicationKey = applicationKey;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
