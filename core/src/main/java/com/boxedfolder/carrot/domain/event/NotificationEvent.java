package com.boxedfolder.carrot.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.Size;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class NotificationEvent extends Event {
    @JsonProperty
    @Column(nullable = false)
    @Size(min = 1)
    private String title;

    @JsonProperty
    @Column(nullable = false)
    private String message;

    @JsonProperty
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
