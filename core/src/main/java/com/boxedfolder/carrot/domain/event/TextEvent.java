package com.boxedfolder.carrot.domain.event;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
public class TextEvent extends Event {
    @JsonProperty
    @Column(nullable = false)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
