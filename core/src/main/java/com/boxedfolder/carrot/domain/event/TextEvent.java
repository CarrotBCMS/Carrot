package com.boxedfolder.carrot.domain.event;

import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@Entity
public class TextEvent extends Event {
    @JsonView(View.General.class)
    @Column(nullable = false)
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
