package org.drvad3r.nihongo.model.list;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.drvad3r.nihongo.model.Word;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */

@XmlRootElement(name = "words")
public class WordList {
    private ObservableList<Word> words;
    private String description;

    public WordList() {
        words = FXCollections.observableArrayList();
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement(name = "word")
    public ObservableList<Word> getWords() {
        return words;
    }

    public void setWords(ObservableList<Word> words) {
        this.words = words;
    }
}
