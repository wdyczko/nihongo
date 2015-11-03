package org.drvad3r.nihongo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.define.Style;
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
        updateStatus();
    }

    private void presentWord(Word word)
    {
        originalLabel.setText(word.getOriginal());
    }

    private void showAnswer()
    {
        pronounceLabel.setText(wordManager.getCurrent().getPronounce());
    }

    private void updateStatus()
    {
        statusLabel.setText(String.format(Style.STATUS_FORMATTER, (wordManager.getPassedSize() - 1 < 0) ? 0 : wordManager.getPassedSize() - 1, wordManager.getWordsListSize()));
    }

    private boolean isPassCondition()
    {
        if(pronounceTextField.getText().equals(wordManager.getCurrent().getPronounce()))
        {
            return true;
        }
        return false;
    }

    private boolean isEndingCondition()
    {
        if(wordManager.getPassedSize() == wordManager.getWordsListSize())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @FXML
    private void onPronounceKeyInput(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            if(isPassCondition())
            {
                pronounceTextField.getStyleClass().removeAll(Style.Class.TextField.INCORRECT);
                pronounceTextField.requestLayout();
                statusProgressBar.setProgress(( (double) wordManager.getPassedSize()/ ((double) wordManager.getWordsListSize() )));
                statusLabel.setText(String.format(Style.STATUS_FORMATTER, wordManager.getPassedSize(), wordManager.getWordsListSize()));
                if(isEndingCondition())
                {
                    nihongo.showManageLists();
                    return;
                }
                presentWord(wordManager.randWord());
                pronounceTextField.setText("");
                pronounceLabel.setText("");
                pronounceTextField.requestFocus();
            }
            else
            {
                pronounceTextField.getStyleClass().add(Style.Class.TextField.INCORRECT);
                pronounceTextField.requestLayout();
                wordManager.unpassLastIndex();
                showAnswer();
            }
        }
        else if(keyEvent.getCode() == KeyCode.F1)
        {
            statusLabel.setText(wordManager.getCurrent().getLocal());
            recoverStatus();
        }
        else if(keyEvent.getCode() == KeyCode.F2)
        {
            statusLabel.setText(wordManager.getCurrent().getEnglish());
            recoverStatus();
        }
        else if(keyEvent.getCode() == KeyCode.F3)
        {
            statusLabel.setText(wordManager.getCurrent().getPronounce().substring(0, 1) + "...");
            recoverStatus();
        }
    }

    private void recoverStatus() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(4),
                actionEvent -> updateStatus()
        ));
        timeline.play();
    }
}
