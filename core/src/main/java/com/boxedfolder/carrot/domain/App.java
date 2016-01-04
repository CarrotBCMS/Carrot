/*
 * Carrot - beacon management
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

package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.analytics.AnalyticsLog;
import com.boxedfolder.carrot.domain.general.AbstractUserRelatedEntity;
import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Table(name = "app")
@Entity
public class App extends AbstractUserRelatedEntity {
    @JsonIgnore
    @Column(name = "app_key")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID applicationKey;

    @ManyToMany(mappedBy = "apps", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Event> events = new HashSet<>();

    @OneToMany(mappedBy = "app", cascade = CascadeType.REMOVE)
    private Set<AnalyticsLog> logs;

    @JsonView(View.General.class)
    public UUID getApplicationKey() {
        return applicationKey;
    }

    @JsonIgnore
    public void setApplicationKey(UUID applicationKey) {
        this.applicationKey = applicationKey;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
}
