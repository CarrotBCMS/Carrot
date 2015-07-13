package com.boxedfolder.carrot.domain;

import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@DiscriminatorValue("text")
@Table(name = "text_event")
@Entity
public class TextEvent extends Event {
    @JsonView(View.General.class)
    @NotNull
    @Size(min = 1)
    @Column(columnDefinition = "TEXT")
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
