package org.drvad3r.nihongo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.define.Style;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Adjective;
import org.drvad3r.nihongo.model.list.AdjectiveList;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Wiktor
 * Creation date: 2016-01-24.
 */
public class AdjectivesDeclination {
    @FXML
    private Label adjectiveLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private TextField answerTextField;
    @FXML
    private Label answerLabel;
    @FXML
    private Label statusLabel;

    interface QUESTION {
        int COUNT = 8;
        String AFFIRMATIVE = "Adjective ending in AFFIRMATIVE form";
        String AFFIRMATIVE_POLITE = "Adjective ending in AFFIRMATIVE POLITE form";
        String NEGATIVE = "Adjective ending in NEGATIVE form";
        String NEGATIVE_POLITE = "Adjective ending in NEGATIVE POLITE form";
        String PAST = "Adjective ending in PAST form";
        String PAST_POLITE = "Adjective ending in PAST POLITE form";
        String NEGATIVE_PAST = "Adjective ending in NEGATIVE PAST form";
        String NEGATIVE_PAST_POLITE = "Adjective ending in NEGATIVE PAST POLITE form";
    }

    private Nihongo nihongo;
    private AdjectiveList adjectives;
    private ArrayList<Integer> adjectivesPassed;
    private int index;
    private Adjective current;
    private int questionIndicator;

    public AdjectivesDeclination() {
        StorageManager storageManager = new StorageManager();
        adjectives = storageManager.loadAdjectiveDataFromFile(new File(System.getProperty("user.dir") + Path.ADJECTIVE_DECLINATION_DEFINITION_FILE));
        adjectivesPassed = new ArrayList<>();
        randAdjective();
    }

    public void setNihongo(Nihongo nihongo) {
        this.nihongo = nihongo;
    }

    @FXML
    private void initialize() {
        presentCurrentAdjective();
        answerLabel.setText("");
        updateStatus();
    }

    private void presentCurrentAdjective() {
        adjectiveLabel.setText(current.getLocal());
        questionLabel.setText(getQuestion());
    }

    private Adjective randAdjective() {
        Random random = new Random();
        index = -1;
        do {
            index = random.nextInt(getAdjectiveListSize());
        } while (adjectivesPassed.contains(index));
        adjectivesPassed.add(index);
        current = adjectives.getAdjectives().get(Math.round(index / QUESTION.COUNT));
        questionIndicator = index % QUESTION.COUNT;
        return current;
    }

    private String getQuestion() {
        switch (questionIndicator) {
            case 0:
                return QUESTION.AFFIRMATIVE;
            case 1:
                return QUESTION.AFFIRMATIVE_POLITE;
            case 2:
                return QUESTION.NEGATIVE;
            case 3:
                return QUESTION.NEGATIVE_POLITE;
            case 4:
                return QUESTION.PAST;
            case 5:
                return QUESTION.PAST_POLITE;
            case 6:
                return QUESTION.NEGATIVE_PAST;
            case 7:
                return QUESTION.NEGATIVE_PAST_POLITE;
            default:
                return "ERROR";
        }
    }

    private String getRightAnswer() {
        switch (questionIndicator) {
            case 0:
                return current.getAffirmative();
            case 1:
                return current.getAffirmativePolite();
            case 2:
                return current.getNegative();
            case 3:
                return current.getNegativePolite();
            case 4:
                return current.getPast();
            case 5:
                return current.getPastPolite();
            case 6:
                return current.getNegativePast();
            case 7:
                return current.getNegativePastPolite();
            default:
                return "ERROR";
        }
    }

    private int getAdjectiveListSize() {
        if (adjectives != null) {
            return adjectives.getAdjectives().size() * QUESTION.COUNT;
        } else {
            return 0;
        }
    }

    private void showAnswer() {
        answerLabel.setText(getRightAnswer());
    }

    private boolean isPassCondition() {
        return getRightAnswer().equals(answerTextField.getText().trim());
    }

    private void updateStatus() {
        statusLabel.setText(String.format(Style.STATUS_FORMATTER, (adjectivesPassed.size() - 1 < 0) ? 0 : adjectivesPassed.size() - 1, getAdjectiveListSize()));
    }

    private void recoverStatus() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(4),
                actionEvent -> updateStatus()
        ));
        timeline.play();
    }

    private boolean isEndingCondition() {
        return this.getAdjectiveListSize() == this.adjectivesPassed.size();
    }

    public void unpassLastIndex() {
        adjectivesPassed.remove(new Integer(index));
    }

    @FXML
    private void onAnswerKeyInput(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (isPassCondition()) {
                answerTextField.getStyleClass().removeAll(Style.Class.TextField.INCORRECT);
                answerTextField.requestLayout();
                updateStatus();
                nihongo.extendWindowTitle(String.format("%d %%", (int)( 100.00 * (double) adjectivesPassed.size() / (double) getAdjectiveListSize())));
                if (isEndingCondition()) {
                    nihongo.showManageLists();
                    nihongo.recoverWindowTitle();
                    return;
                }
                randAdjective();
                presentCurrentAdjective();
                answerTextField.setText("");
                answerLabel.setText("");
                answerTextField.requestFocus();
                statusLabel.setText("Good!");
                recoverStatus();
            } else {
                answerTextField.getStyleClass().add(Style.Class.TextField.INCORRECT);
                answerTextField.requestLayout();
                unpassLastIndex();
                showAnswer();
            }

        } else if (keyEvent.getCode() == KeyCode.F1) {
            statusLabel.setText(current.getLocal());
            recoverStatus();
        } else if (keyEvent.getCode() == KeyCode.F2) {
            statusLabel.setText(current.getFuragana());
            recoverStatus();
        } else if (keyEvent.getCode() == KeyCode.ESCAPE)
        {
            nihongo.showManageLists();
            nihongo.recoverWindowTitle();
        }
    }

}
