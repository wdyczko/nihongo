package org.drvad3r.nihongo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
public class Word
{
    private StringProperty original;
    private StringProperty pronounce;
    private StringProperty english;
    private StringProperty polish;

    public Word()
    {
        this(null, null);
    }

    public Word(String original, String english)
    {
        this.original = new SimpleStringProperty(original);
        this.english = new SimpleStringProperty(english);

        this.pronounce = new SimpleStringProperty("No pronounce");
        this.polish = new SimpleStringProperty("No polish translation");
    }

    public String getOriginal()
    {
        return original.get();
    }

    public StringProperty originalProperty()
    {
        return original;
    }

    public void setOriginal(String original)
    {
        this.original.set(original);
    }

    public String getPronounce()
    {
        return pronounce.get();
    }

    public StringProperty pronounceProperty()
    {
        return pronounce;
    }

    public void setPronounce(String pronounce)
    {
        this.pronounce.set(pronounce);
    }

    public String getEnglish()
    {
        return english.get();
    }

    public StringProperty englishProperty()
    {
        return english;
    }

    public void setEnglish(String english)
    {
        this.english.set(english);
    }

    public String getPolish()
    {
        return polish.get();
    }

    public StringProperty polishProperty()
    {
        return polish;
    }

    public void setPolish(String polish)
    {
        this.polish.set(polish);
    }

    public static void print(Word word)
    {
        System.out.println("Original: " + word.getOriginal());
        System.out.println("Pronounce: " + word.getPronounce());
        System.out.println("English: " + word.getEnglish());
        System.out.println("Polish: " + word.getPolish());
    }
}
