package org.drvad3r.nihongo.define;

/**
 * Created by wdyczko on 10/28/2015.
 */
public interface Style {
    String STATUS_FORMATTER = "%d/%d";

    interface Class {
        interface TextField {
            String CORRECT = "text-field-correct";
            String INCORRECT = "text-field-incorrect";
        }
    }
}
