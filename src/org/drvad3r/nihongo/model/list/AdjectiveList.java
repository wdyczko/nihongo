package org.drvad3r.nihongo.model.list;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.drvad3r.nihongo.model.Adjective;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Wiktor
 * Creation date: 2016-01-24.
 */
@XmlRootElement(name = "adjectives")
public class AdjectiveList {
    private ObservableList<Adjective> adjectives;

    public AdjectiveList() {
        adjectives = FXCollections.observableArrayList();
    }

    @XmlElement(name = "adjective")
    public ObservableList<Adjective> getAdjectives() {
        return adjectives;
    }

    public void setAdjectives(ObservableList<Adjective> adjectives) {
        this.adjectives = adjectives;
    }
}
