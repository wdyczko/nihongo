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
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.define.SessionKeys;
import org.drvad3r.nihongo.manager.SessionManager;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.WordList;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

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
    private WordList wordList;
    private Word current;
    private ArrayList<Integer> passed;
    private int value;

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    private void setWordList()
    {
        this.wordList = SessionManager.getInstance().loadWordList();
    }

    public void init()
    {
        setWordList();
        this.passed = new ArrayList<>();
        statusLabel.setText(String.format("%d/%d", passed.size(), wordList.getWords().size()));
        this.current = randomizeWord();
        presentWord(this.current);
    }

    private Word randomizeWord()
    {
        Random random = new Random();
        value = -1;
        do
        {
            value = random.nextInt(wordList.getWords().size());
        } while (passed.contains(value));
        passed.add(value);
        return wordList.getWords().get(value);
    }

    private void presentWord(Word word)
    {
        this.englishTextArea.setText(word.getEnglish());
    }

    private void resetTextFieldStyle(TextField textField)
    {
        textField.getStyleClass().removeAll("text-field-incorrect");
        textField.getStyleClass().removeAll("text-field-correct");
        textField.requestLayout();
    }

    private void correctTextFieldStyle(TextField textField)
    {
        textField.getStyleClass().add("text-field-correct");
        textField.requestLayout();
    }

    private void incorrectTextFieldStyle(TextField textField)
    {
        textField.getStyleClass().add("text-field-incorrect");
        textField.requestLayout();
    }

    private boolean isPassCondition()
    {
        String originalTextFieldString = originalTextField.getText().trim();
        String pronounceTextFieldString = pronounceTextField.getText().trim();
        if(originalTextFieldString.equals(this.current.getOriginal()) && pronounceTextFieldString.equals(this.current.getPronounce()))
        {
            return true;
        }
        return false;
    }

    private boolean isOriginalCorrect()
    {
        String originalTextFieldString = originalTextField.getText().trim();
        if(originalTextFieldString.equals(this.current.getOriginal()))
        {
            return true;
        }
        return false;
    }

    private boolean isPronounceCorrect()
    {
        String pronounceTextFieldString = pronounceTextField.getText().trim();
        if(pronounceTextFieldString.equals(this.current.getPronounce()))
        {
            return true;
        }
        return false;
    }

    private boolean isEndingCondition()
    {
        if(passed.size() == wordList.getWords().size())
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
        pronounceLabel.setText(this.current.getPronounce());
        originalLabel.setText(this.current.getOriginal());
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
                learnProgressBar.setProgress(( (double) passed.size()/ ((double) wordList.getWords().size() )));
                statusLabel.setText(String.format("%d/%d", passed.size(), wordList.getWords().size()));
                if(isEndingCondition())
                {
                    nihongo.showManageLists();
                    return;
                }
                this.current = randomizeWord();
                presentWord(this.current);
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
                passed.remove(new Integer(value));
                showAnswer();
            }
        }
        else if(keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.P)
        {
            statusLabel.setText(current.getPolish());
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(4),
                    actionEvent -> statusLabel.setText(String.format("%d/%d", (passed.size() - 1 < 0) ? 0 : passed.size() - 1, wordList.getWords().size()))
            ));
            timeline.play();
        }
    }
}
