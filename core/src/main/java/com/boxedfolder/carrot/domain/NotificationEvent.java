package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@DiscriminatorValue("notification")
@Entity(name = "notification_event")
public class NotificationEvent extends Event {
    @JsonView(View.General.class)
    @Column(nullable = false)
    @NotNull
    @Size(min = 1)
    private String title;

    @JsonView(View.General.class)
    @NotNull
    @Size(min = 1)
    private String message;

    @JsonView(View.General.class)
    private String payload;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
