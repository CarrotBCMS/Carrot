package com.boxedfolder.carrot.domain.event;

import com.boxedfolder.carrot.domain.util.View;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

/**
 * @author Heiko Dreyer (heiko@boxedfolder.com)
 */
@DiscriminatorValue("text")
@Entity
public class TextEvent extends Event {
    @JsonView(View.General.class)
    @NotNull
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
