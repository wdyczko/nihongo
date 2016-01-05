package org.drvad3r.nihongo.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Author: Wiktor
 * Creation: 2015-11-10
 */
public class Verb {
    private StringProperty negative;
    private StringProperty conjunctive;
    private StringProperty plain;
    private StringProperty conditional;
    private StringProperty volitional;
    private StringProperty local;
    private StringProperty kanaBase;
    private StringProperty te;
    private StringProperty ta;
    private StringProperty tara;
    private StringProperty tari;
    private StringProperty imperative;

    public Verb() {
        negative = new SimpleStringProperty("");
        conjunctive = new SimpleStringProperty("");
        plain = new SimpleStringProperty("");
        conditional = new SimpleStringProperty("");
        volitional = new SimpleStringProperty("");
        local = new SimpleStringProperty("");
        kanaBase = new SimpleStringProperty("");
        te = new SimpleStringProperty("");
        ta = new SimpleStringProperty("");
        tara = new SimpleStringProperty("");
        tari = new SimpleStringProperty("");
        imperative = new SimpleStringProperty("");
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

    public String getConjunctive() {
        return conjunctive.get();
    }

    public StringProperty conjunctiveProperty() {
        return conjunctive;
    }

    public void setConjunctive(String conjunctive) {
        this.conjunctive.set(conjunctive);
    }

    public String getPlain() {
        return plain.get();
    }

    public StringProperty plainProperty() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain.set(plain);
    }

    public String getConditional() {
        return conditional.get();
    }

    public StringProperty conditionalProperty() {
        return conditional;
    }

    public void setConditional(String conditional) {
        this.conditional.set(conditional);
    }

    public String getVolitional() {
        return volitional.get();
    }

    public StringProperty volitionalProperty() {
        return volitional;
    }

    public void setVolitional(String volitional) {
        this.volitional.set(volitional);
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

    public String getKanaBase() {
        return kanaBase.get();
    }

    public StringProperty kanaBaseProperty() {
        return kanaBase;
    }

    public void setKanaBase(String kanaBase) {
        this.kanaBase.set(kanaBase);
    }

    public String getTe() {
        return te.get();
    }

    public StringProperty teProperty() {
        return te;
    }

    public void setTe(String te) {
        this.te.set(te);
    }

    public String getTa() {
        return ta.get();
    }

    public StringProperty taProperty() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta.set(ta);
    }

    public String getTara() {
        return tara.get();
    }

    public StringProperty taraProperty() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara.set(tara);
    }

    public String getTari() {
        return tari.get();
    }

    public StringProperty tariProperty() {
        return tari;
    }

    public void setTari(String tari) {
        this.tari.set(tari);
    }

    public String getImperative() {
        return imperative.get();
    }

    public StringProperty imperativeProperty() {
        return imperative;
    }

    public void setImperative(String imperative) {
        this.imperative.set(imperative);
    }

    public static void printVerb(Verb verb) {
        System.out.println(String.format("Negative: %s, Conjunctive: %s, Plain: %s, Conditional: %s, Volitional: %s, Local: %s, KanaBase: %s",
                verb.getNegative(), verb.getConjunctive(), verb.getPlain(), verb.getConditional(), verb.getVolitional(), verb.getLocal(), verb.getKanaBase()));
    }
}
