package org.drvad3r.nihongo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.WordList;

import java.util.Random;

/**
 * Author: Wiktor
 * Creation: 2015-10-21
 */
public class WordLearn
{
    @FXML
    private Label englishLabel;
    @FXML
    private TextField pronounceTextField;
    @FXML
    private TextField originalTextField;
    @FXML
    private Label pronounceLabel;
    @FXML
    private Label originalLabel;

    private Nihongo nihongo;
    private WordList wordList;
    private Word current;

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    public void setWordList(WordList wordList)
    {
        this.wordList = wordList;
    }

    public void init()
    {
        this.current = randomizeWord();
        presentWord(this.current);
    }

    private Word randomizeWord()
    {
        Random random = new Random();
        int value = random.nextInt(wordList.getWords().size());
        return wordList.getWords().get(value);
    }

    private void presentWord(Word word)
    {
        this.englishLabel.setText(word.getEnglish());
        this.originalLabel.setText(word.getOriginal());
        this.pronounceLabel.setText(word.getPronounce());
    }

    @FXML
    private void onPronounceKeyEvent(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            this.current = randomizeWord();
            presentWord(this.current);
            if(this.pronounceTextField.getStyleClass().indexOf("text-field-wrong") < 0)
                this.pronounceTextField.getStyleClass().add("text-field-wrong");
            else
                this.pronounceTextField.getStyleClass().remove("text-field-wrong");
            this.pronounceTextField.requestLayout();
        }
    }
}
