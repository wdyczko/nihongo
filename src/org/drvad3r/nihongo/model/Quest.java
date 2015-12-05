package org.drvad3r.nihongo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Comparator;

/**
 * Author: Wiktor
 * Creation date: 2015-12-05.
 */
public class Quest {
    private StringProperty input;
    private StringProperty output;
    private StringProperty title;

    public Quest()
    {
        this(null, null, null);
    }

    public Quest(String input, String output, String title)
    {
        this.input = new SimpleStringProperty(input);
        this.output = new SimpleStringProperty(output);
        this.title = new SimpleStringProperty(title);
    }

    public String getInput()
    {
        return input.get();
    }

    public StringProperty inputProperty()
    {
        return input;
    }

    public void setInput(String input)
    {
        this.input.set(input);
    }

    public String getOutput()
    {
        return output.get();
    }

    public StringProperty outputProperty()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output.set(output);
    }

    public String getTitle()
    {
        return title.get();
    }

    public StringProperty titleProperty()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title.set(title);
    }

    public String toString()
    {
        return getTitle();
    }
}
