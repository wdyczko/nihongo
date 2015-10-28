package org.drvad3r.nihongo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
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
 * Creation: 2015-10-21
 */
public class PronunciationAndKanji
{
    @FXML
    private TextField pronounceTextField;
    @FXML
    private TextField originalTextField;
    @FXML
    private Label pronounceLabel;
    @FXML
    private Label originalLabel;
    @FXML
    private ProgressBar learnProgressBar;
    @FXML
    private Label statusLabel;
    @FXML
    private TextArea englishTextArea;

    private Nihongo nihongo;
    private WordManager wordManager;

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    public void init()
    {
        this.wordManager = new WordManager(SessionManager.getInstance().loadWordList());
        statusLabel.setText(String.format(Style.STATUS_FORMATTER, wordManager.getPassedSize(), wordManager.getWordsListSize()));
        presentWord(wordManager.randWord());
    }

    private void presentWord(Word word)
    {
        this.englishTextArea.setText(word.getEnglish());
    }

    private void resetTextFieldStyle(TextField textField)
    {
        textField.getStyleClass().removeAll(Style.Class.TextField.INCORRECT);
        textField.getStyleClass().removeAll(Style.Class.TextField.CORRECT);
        textField.requestLayout();
    }

    private void correctTextFieldStyle(TextField textField)
    {
        textField.getStyleClass().add(Style.Class.TextField.CORRECT);
        textField.requestLayout();
    }

    private void incorrectTextFieldStyle(TextField textField)
    {
        textField.getStyleClass().add(Style.Class.TextField.INCORRECT);
        textField.requestLayout();
    }

    private boolean isPassCondition()
    {
        String originalTextFieldString = originalTextField.getText().trim();
        String pronounceTextFieldString = pronounceTextField.getText().trim();
        if(originalTextFieldString.equals(wordManager.getCurrent().getOriginal()) && pronounceTextFieldString.equals(wordManager.getCurrent().getPronounce()))
        {
            return true;
        }
        return false;
    }

    private boolean isOriginalCorrect()
    {
        String originalTextFieldString = originalTextField.getText().trim();
        if(originalTextFieldString.equals(wordManager.getCurrent().getOriginal()))
        {
            return true;
        }
        return false;
    }

    private boolean isPronounceCorrect()
    {
        String pronounceTextFieldString = pronounceTextField.getText().trim();
        if(pronounceTextFieldString.equals(wordManager.getCurrent().getPronounce()))
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

    private void showAnswer()
    {
        pronounceLabel.setText(wordManager.getCurrent().getPronounce());
        originalLabel.setText(wordManager.getCurrent().getOriginal());
    }

    @FXML
    private void onPronounceKeyEvent(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            if(isPronounceCorrect())
            {
                correctTextFieldStyle(pronounceTextField);
            }
            else
            {
                incorrectTextFieldStyle(pronounceTextField);
            }
            if(isOriginalCorrect())
            {
                correctTextFieldStyle(originalTextField);
            }
            else
            {
                incorrectTextFieldStyle(originalTextField);
            }
            if(isPassCondition())
            {
                learnProgressBar.setProgress(( (double) wordManager.getPassedSize()/ ((double) wordManager.getWordsListSize() )));
                statusLabel.setText(String.format(Style.STATUS_FORMATTER, wordManager.getPassedSize(), wordManager.getWordsListSize()));
                if(isEndingCondition())
                {
                    nihongo.showManageLists();
                    return;
                }
                presentWord(wordManager.randWord());
                resetTextFieldStyle(pronounceTextField);
                resetTextFieldStyle(originalTextField);
                pronounceTextField.setText("");
                originalTextField.setText("");
                pronounceLabel.setText("");
                originalLabel.setText("");
                pronounceTextField.requestFocus();
            }
            else
            {
                wordManager.unpassLastIndex();
                showAnswer();
            }
        }
        else if(keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.P)
        {
            statusLabel.setText(wordManager.getCurrent().getPolish());
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(4),
                    actionEvent -> statusLabel.setText(String.format(Style.STATUS_FORMATTER, (wordManager.getPassedSize() - 1 < 0) ? 0 : wordManager.getPassedSize() - 1, wordManager.getWordsListSize()))
            ));
            timeline.play();
        }
    }
}
