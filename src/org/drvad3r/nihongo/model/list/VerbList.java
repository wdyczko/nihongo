package org.drvad3r.nihongo.model.list;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.drvad3r.nihongo.model.Verb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Author: Wiktor
 * Creation: 2015-11-10
 */

@XmlRootElement(name = "verbs")
public class VerbList {
    private ObservableList<Verb> verbs;

    public VerbList() {
        verbs = FXCollections.observableArrayList();
    }

    @XmlElement(name = "verb")
    public ObservableList<Verb> getVerbs() {
        return verbs;
    }

    public void setVerbs(ObservableList<Verb> verbs) {
        this.verbs = verbs;
    }
}
