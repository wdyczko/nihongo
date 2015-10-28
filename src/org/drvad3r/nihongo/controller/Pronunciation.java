package org.drvad3r.nihongo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import org.drvad3r.nihongo.Nihongo;

/**
 * Author: Wiktor
 * Creation: 2015-10-28
 */
public class Pronunciation {
    @FXML
    private Label originalLabel;
    @FXML
    private TextField pronounceTextField;
    @FXML
    private Label pronounceLabel;
    @FXML
    private ProgressBar statusProgressBar;
    @FXML
    private Label statusLabel;

    private Nihongo nihongo;

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    public void init()
    {

    }
}
