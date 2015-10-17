package org.drvad3r.nihongo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */

@XmlRootElement(name = "words")
public class WordList
{
    private List<Word> words;
    private String description;

    public WordList()
    {
        words = new ArrayList<>();
    }

    @XmlElement(name = "description")
    public String getDescription() { return description; }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @XmlElement(name = "word")
    public List<Word> getWords()
    {
        return words;
    }

    public void setWords(List<Word> words)
    {
        this.words = words;
    }
}
