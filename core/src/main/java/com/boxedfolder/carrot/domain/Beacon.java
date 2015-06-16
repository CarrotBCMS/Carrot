package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.event.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class Beacon extends AbstractEntity {
    @JsonIgnore
    @ManyToMany(mappedBy = "beacons", fetch = FetchType.LAZY)
    private List<App> apps = new ArrayList<App>();

    @JsonIgnore
    @ManyToMany(mappedBy = "beacons", fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<Event>();
}
