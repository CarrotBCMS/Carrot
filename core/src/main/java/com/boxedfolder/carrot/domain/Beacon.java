package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.event.Event;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class Beacon extends AbstractEntity {
    @ManyToMany(mappedBy = "beacons", fetch = FetchType.LAZY)
    private List<App> apps = new ArrayList<App>();

    @JsonView(View.Sync.class)
    @ManyToMany(mappedBy = "beacons", fetch = FetchType.LAZY)
    private List<Event> events = new ArrayList<Event>();
}
