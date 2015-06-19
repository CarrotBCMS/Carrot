package com.boxedfolder.carrot.domain.event;

import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Entity
public class NotificationEvent extends Event {
    @JsonView(View.General.class)
    @Column(nullable = false)
    @Size(min = 1)
    private String title;

    @JsonView(View.General.class)
    @Column(nullable = false)
    private String message;

    @JsonView(View.General.class)
    @Column(nullable = false)
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
