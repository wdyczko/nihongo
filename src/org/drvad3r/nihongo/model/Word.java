package org.drvad3r.nihongo.model;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
public class Word
{
    private String original;
    private String pronounce;
    private String english;
    private String polish;

    public String getOriginal()
    {
        return original;
    }

    public void setOriginal(String original)
    {
        this.original = original;
    }

    public String getPronounce()
    {
        return pronounce;
    }

    public void setPronounce(String pronounce)
    {
        this.pronounce = pronounce;
    }

    public String getEnglish()
    {
        return english;
    }

    public void setEnglish(String english)
    {
        this.english = english;
    }

    public String getPolish()
    {
        return polish;
    }

    public void setPolish(String polish)
    {
        this.polish = polish;
    }

    public static void print(Word word)
    {
        System.out.println("Original: " + word.getOriginal());
        System.out.println("Pronounce: " + word.getPronounce());
        System.out.println("English: " + word.getEnglish());
        System.out.println("Polish: " + word.getPolish());
    }
}
