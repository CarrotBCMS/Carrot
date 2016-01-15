/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
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
