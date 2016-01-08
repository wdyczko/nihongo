package org.drvad3r.nihongo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.define.Option;
import org.drvad3r.nihongo.define.SessionKeys;
import org.drvad3r.nihongo.define.Style;
import org.drvad3r.nihongo.manager.SessionManager;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.manager.WordManager;
import org.drvad3r.nihongo.model.Quest;
import org.drvad3r.nihongo.model.Word;

import java.security.Key;

/**
 * Author: Wiktor
 * Creation date: 2015-12-12.
 */
public class GeneralQuest {
    public TextArea inputTextArea;
    public TextField originalTextField;
    public Label originalLabel;
    public TextField pronounceTextField;
    public TextField englishTextField;
    public TextField localTextField;
    public Label pronounceLabel;
    public Label englishLabel;
    public Label localLabel;
    public ProgressBar progressBar;
    public Label statusLabel;

    private Nihongo nihongo;
    private Quest quest;
    private WordManager wordManager;
    private int returnFocusTo = -1;

    private boolean original;
    private boolean english;
    private boolean pronounce;
    private boolean local;

    public void initialize() {
        wordManager = new WordManager(SessionManager.getInstance().loadWordList());
        String questTitle = SessionManager.getInstance().getSessionItem(SessionKeys.CURRENT_QUEST_TITLE);

        if (questTitle.isEmpty())
            throw new RuntimeException("Session Error: Current quest title key in session not found!");

        StorageManager storageManager = new StorageManager();
        quest = storageManager.loadQuestsDataFromFile().getQuest(questTitle);

        if (quest == null)
            throw new RuntimeException("Data Error: Quest item not found!");

        recognizeQuestParameters(quest);
        prepareUserInterface();

        statusLabel.setText(String.format(Style.STATUS_FORMATTER, wordManager.getPassedSize(), wordManager.getWordsListSize()));
        presentWord(wordManager.randWord());
    }

    private void resetTextFieldStyle(TextField textField) {
        textField.getStyleClass().removeAll(Style.Class.TextField.INCORRECT);
        textField.getStyleClass().removeAll(Style.Class.TextField.CORRECT);
        textField.requestLayout();
    }

    private void correctTextFieldStyle(TextField textField) {
        textField.getStyleClass().add(Style.Class.TextField.CORRECT);
        textField.requestLayout();
    }

    private void incorrectTextFieldStyle(TextField textField) {
        textField.getStyleClass().add(Style.Class.TextField.INCORRECT);
        textField.requestLayout();
    }

    private void recognizeQuestParameters(Quest quest) {
        original = Option.isOriginal(Integer.parseInt(quest.getOutput()));
        english = Option.isEnglish(Integer.parseInt(quest.getOutput()));
        pronounce = Option.isPronounce(Integer.parseInt(quest.getOutput()));
        local = Option.isLocal(Integer.parseInt(quest.getOutput()));
    }

    private void prepareUserInterface() {
        inputTextArea.setFocusTraversable(false);

        prepareOriginal();
        prepareEnglish();
        preparePronounce();
        prepareLocal();
    }

    private void prepareOriginal() {
        if (!original) {
            originalTextField.setDisable(true);
            originalLabel.setDisable(true);
        } else {
            if (returnFocusTo == -1)
                returnFocusTo = 0;
        }
    }

    private void prepareEnglish() {
        if (!english) {
            englishTextField.setDisable(true);
            englishLabel.setDisable(true);
        } else {
            if (returnFocusTo == -1)
                returnFocusTo = 1;
        }
    }

    private void preparePronounce() {
        if (!pronounce) {
            pronounceTextField.setDisable(true);
            pronounceLabel.setDisable(true);
        } else {
            if (returnFocusTo == -1)
                returnFocusTo = 2;
        }
    }

    private void prepareLocal() {
        if (!local) {
            localTextField.setDisable(true);
            localLabel.setDisable(true);
        } else {
            if (returnFocusTo == -1)
                returnFocusTo = 3;
        }
    }

    private void returnFocus() {
        switch (returnFocusTo) {
            case 0:
                originalTextField.requestFocus();
                break;
            case 1:
                englishTextField.requestFocus();
                break;
            case 2:
                pronounceTextField.requestFocus();
                break;
            case 3:
                localTextField.requestFocus();
                break;
        }
    }

    private void presentWord(Word word) {
        Option option = Option.toOption(Integer.parseInt(quest.getInput()));
        switch (option) {
            case ORIGINAL:
                inputTextArea.setText(word.getOriginal());
                break;
            case ENGLISH:
                inputTextArea.setText(word.getEnglish());
                break;
            case LOCAL:
                inputTextArea.setText(word.getLocal());
                break;
            case PRONOUNCE:
                inputTextArea.setText(word.getPronounce());
                break;
        }
    }

    public Nihongo getNihongo() {
        return nihongo;
    }

    public void setNihongo(Nihongo nihongo) {
        this.nihongo = nihongo;
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (isPassCondition()) {
                progressBar.setProgress(((double) wordManager.getPassedSize() / ((double) wordManager.getWordsListSize())));
                nihongo.extendWindowTitle(getPercentString());
                statusLabel.setText(String.format(Style.STATUS_FORMATTER, wordManager.getPassedSize(), wordManager.getWordsListSize()));
                if (wordManager.isEndingCondition()) {
                    nihongo.showManageLists();
                    nihongo.recoverWindowTitle();
                    return;
                }
                resetInputs();
                presentWord(wordManager.randWord());
                returnFocus();
            } else {
                wordManager.unpassLastIndex();
            }
        }
        if(keyEvent.getCode() == KeyCode.ESCAPE)
        {
            nihongo.showManageLists();
            nihongo.recoverWindowTitle();
            return;
        }
        if(keyEvent.getCode() == KeyCode.F1)
        {
            originalLabel.setText(wordManager.getCurrent().getOriginal());
        }
        if(keyEvent.getCode() == KeyCode.F2)
        {
            pronounceLabel.setText(wordManager.getCurrent().getPronounce());
        }
        if(keyEvent.getCode() == KeyCode.F3)
        {
            englishLabel.setText(wordManager.getCurrent().getEnglish());
        }
        if(keyEvent.getCode() == KeyCode.F4)
        {
            localLabel.setText(wordManager.getCurrent().getLocal());
        }
        if (keyEvent.getCode() == KeyCode.ALT) {
            keyEvent.consume();
        }
    }

    private String getPercentString() {
        double percent = 100.00 * (double) wordManager.getPassedSize() / ((double) wordManager.getWordsListSize());
        return String.format("%d %%", (int) percent);
    }

    private void resetInputs() {
        englishLabel.setText("");
        englishTextField.setText("");
        originalLabel.setText("");
        originalTextField.setText("");
        pronounceLabel.setText("");
        pronounceTextField.setText("");
        localLabel.setText("");
        localTextField.setText("");
        resetTextFieldStyle(englishTextField);
        resetTextFieldStyle(originalTextField);
        resetTextFieldStyle(pronounceTextField);
        resetTextFieldStyle(localTextField);
    }

    private boolean isPassCondition() {
        boolean passCondition = true;
        passCondition = isOriginalPassCondition() && passCondition;
        passCondition = isEnglishPassCondition() && passCondition;
        passCondition = isPronouncePassCondition() && passCondition;
        passCondition = isLocalPassCodition() && passCondition;
        return passCondition;
    }

    private boolean isOriginalPassCondition() {
        if (original) {
            if (!originalTextField.getText().trim().equals(wordManager.getCurrent().getOriginal())) {
                incorrectOriginal();
                return false;
            } else {
                correctTextFieldStyle(originalTextField);
                return true;
            }
        } else
            return true;
    }

    private void incorrectOriginal() {
        incorrectTextFieldStyle(originalTextField);
        originalLabel.setText(wordManager.getCurrent().getOriginal());
    }

    private boolean isEnglishPassCondition() {
        if (english) {
            if (!englishTextField.getText().trim().equals(wordManager.getCurrent().getEnglish())) {
                incorrectEnglish();
                return false;
            } else {
                correctTextFieldStyle(englishTextField);
                return true;
            }
        } else
            return true;
    }

    private void incorrectEnglish() {
        incorrectTextFieldStyle(englishTextField);
        englishLabel.setText(wordManager.getCurrent().getEnglish());
    }

    private boolean isPronouncePassCondition() {
        if (pronounce) {
            if (!pronounceTextField.getText().trim().equals(wordManager.getCurrent().getPronounce())) {
                incorrectPronounce();
                return false;
            } else {
                correctTextFieldStyle(pronounceTextField);
                return true;
            }
        } else
            return true;
    }

    private void incorrectPronounce() {
        incorrectTextFieldStyle(pronounceTextField);
        pronounceLabel.setText(wordManager.getCurrent().getPronounce());
    }

    private boolean isLocalPassCodition() {
        if (local)
            if (!localTextField.getText().trim().equals(wordManager.getCurrent().getLocal())) {
                incorrectLocal();
                return false;
            } else {
                correctTextFieldStyle(localTextField);
                return true;
            }
        else
            return true;
    }

    private void incorrectLocal() {
        incorrectTextFieldStyle(localTextField);
        localLabel.setText(wordManager.getCurrent().getLocal());
    }


}
