package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Entity
public class Beacon extends AbstractEntity {
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<App> apps = new ArrayList<App>();

    @JsonView(View.Sync.class)
    @ManyToMany(mappedBy = "beacons", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Event> events = new ArrayList<Event>();
}
