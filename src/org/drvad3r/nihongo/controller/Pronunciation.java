package org.drvad3r.nihongo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.manager.SessionManager;
import org.drvad3r.nihongo.manager.WordManager;
import org.drvad3r.nihongo.model.Word;

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
    private WordManager wordManager;

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    public void init()
    {
        this.wordManager = new WordManager(SessionManager.getInstance().loadWordList());
        presentWord(wordManager.randWord());
    }

    private void presentWord(Word word)
    {
        originalLabel.setText(word.getOriginal());
    }

    private void showAnswer()
    {

    }
}
