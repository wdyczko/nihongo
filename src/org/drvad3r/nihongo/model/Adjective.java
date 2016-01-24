package org.drvad3r.nihongo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author: Wiktor
 * Creation date: 2016-01-24.
 */
public class Adjective {
    private StringProperty affirmative;
    private StringProperty affirmativePolite;
    private StringProperty negative;
    private StringProperty negativePolite;
    private StringProperty past;
    private StringProperty pastPolite;
    private StringProperty negativePast;
    private StringProperty negativePastPolite;
    private StringProperty local;
    private StringProperty furagana;

    public Adjective() {
        affirmative = new SimpleStringProperty("");
        affirmativePolite = new SimpleStringProperty("");
        negative = new SimpleStringProperty("");
        negativePolite = new SimpleStringProperty("");
        past = new SimpleStringProperty("");
        pastPolite = new SimpleStringProperty("");
        negativePast = new SimpleStringProperty("");
        negativePastPolite = new SimpleStringProperty("");
        local = new SimpleStringProperty("");
        furagana = new SimpleStringProperty("");
    }

    public String getAffirmative() {
        return affirmative.get();
    }

    public StringProperty affirmativeProperty() {
        return affirmative;
    }

    public void setAffirmative(String affirmative) {
        this.affirmative.set(affirmative);
    }

    public String getAffirmativePolite() {
        return affirmativePolite.get();
    }

    public StringProperty affirmativePoliteProperty() {
        return affirmativePolite;
    }

    public void setAffirmativePolite(String affirmativePolite) {
        this.affirmativePolite.set(affirmativePolite);
    }

    public String getNegative() {
        return negative.get();
    }

    public StringProperty negativeProperty() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative.set(negative);
    }

    public String getNegativePolite() {
        return negativePolite.get();
    }

    public StringProperty negativePoliteProperty() {
        return negativePolite;
    }

    public void setNegativePolite(String negativePolite) {
        this.negativePolite.set(negativePolite);
    }

    public String getPast() {
        return past.get();
    }

    public StringProperty pastProperty() {
        return past;
    }

    public void setPast(String past) {
        this.past.set(past);
    }

    public String getPastPolite() {
        return pastPolite.get();
    }

    public StringProperty pastPoliteProperty() {
        return pastPolite;
    }

    public void setPastPolite(String pastPolite) {
        this.pastPolite.set(pastPolite);
    }

    public String getNegativePast() {
        return negativePast.get();
    }

    public StringProperty negativePastProperty() {
        return negativePast;
    }

    public void setNegativePast(String negativePast) {
        this.negativePast.set(negativePast);
    }

    public String getNegativePastPolite() {
        return negativePastPolite.get();
    }

    public StringProperty negativePastPoliteProperty() {
        return negativePastPolite;
    }

    public void setNegativePastPolite(String negativePastPolite) {
        this.negativePastPolite.set(negativePastPolite);
    }

    public String getLocal() {
        return local.get();
    }

    public StringProperty localProperty() {
        return local;
    }

    public void setLocal(String local) {
        this.local.set(local);
    }

    public String getFuragana() {
        return furagana.get();
    }

    public StringProperty furaganaProperty() {
        return furagana;
    }

    public void setFuragana(String furagana) {
        this.furagana.set(furagana);
    }
}
