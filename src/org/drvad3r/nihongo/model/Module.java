package org.drvad3r.nihongo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author: Wiktor
 * Creation: 2015-10-21
 */
public class Module {
    private StringProperty name;
    private StringProperty file;

    public Module() {
        this(null, null);
    }

    public Module(String name, String file) {
        this.name = new SimpleStringProperty(name);
        this.file = new SimpleStringProperty(file);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getFile() {
        return file.get();
    }

    public StringProperty fileProperty() {
        return file;
    }

    public void setFile(String file) {
        this.file.set(file);
    }

    @Override
    public String toString() {
        return getName();
    }
}
