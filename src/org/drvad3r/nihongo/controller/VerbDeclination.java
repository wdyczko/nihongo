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
import org.drvad3r.nihongo.model.Verb;
import org.drvad3r.nihongo.model.list.VerbList;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * Author: Wiktor
 * Creation: 2015-11-11
 */
public class VerbDeclination {
    @FXML
    private Label verbLabel;
    @FXML
    private Label questionLabel;
    @FXML
    private TextField answerTextField;
    @FXML
    private Label answerLabel;
    @FXML
    private Label statusLabel;

    interface QUESTION {
        int COUNT = 5;
        String NEGATIVE = "Decline verb to negative form";
        String CONJUNCTIVE = "Decline verb to conjunctive form";
        String PLAIN = "Decline verb to plain form";
        String CONDITIONAL = "Decline verb to conditional form";
        String VOLITIONAL = "Decline verb to volitional form";
    }

    private Nihongo nihongo;
    private VerbList verbs;
    private ArrayList<Integer> verbsPassed;
    private int index;
    private Verb current;
    private int questionIndicator;

    public VerbDeclination() {
        StorageManager storageManager = new StorageManager();
        verbs = storageManager.loadVerbDataFromFile(new File(System.getProperty("user.dir") + Path.VERB_DECLINATION_FILE));
        verbsPassed = new ArrayList<>();
        randVerb();
    }

    public void setNihongo(Nihongo nihongo) {
        this.nihongo = nihongo;
    }

    @FXML
    private void initialize() {
        presentCurrentVerb();
        answerLabel.setText("");
        updateStatus();
    }

    private void presentCurrentVerb() {
        verbLabel.setText(current.getPlain());
        questionLabel.setText(getQuestion());
    }

    private Verb randVerb() {
        Random random = new Random();
        index = -1;
        do {
            index = random.nextInt(getVerbsListSize());
        } while (verbsPassed.contains(index));
        verbsPassed.add(index);
        current = verbs.getVerbs().get(Math.round(index / QUESTION.COUNT));
        questionIndicator = index % QUESTION.COUNT;
        return current;
    }

    private String getQuestion() {
        switch (questionIndicator) {
            case 0:
                return QUESTION.NEGATIVE;
            case 1:
                return QUESTION.CONJUNCTIVE;
            case 2:
                return QUESTION.PLAIN;
            case 3:
                return QUESTION.CONDITIONAL;
            case 4:
                return QUESTION.VOLITIONAL;
            default:
                return "ERROR";
        }
    }

    private String getRightAnswer() {
        switch (questionIndicator) {
            case 0:
                return current.getNegative();
            case 1:
                return current.getConjunctive();
            case 2:
                return current.getPlain();
            case 3:
                return current.getConditional();
            case 4:
                return current.getVolitional();
            default:
                return "ERROR";
        }
    }

    private int getVerbsListSize() {
        if (verbs != null) {
            return verbs.getVerbs().size() * QUESTION.COUNT;
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
        statusLabel.setText(String.format(Style.STATUS_FORMATTER, (verbsPassed.size() - 1 < 0) ? 0 : verbsPassed.size() - 1, getVerbsListSize()));
    }

    private void recoverStatus() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(4),
                actionEvent -> updateStatus()
        ));
        timeline.play();
    }

    private boolean isEndingCondition() {
        return this.getVerbsListSize() == this.verbsPassed.size();
    }

    public void unpassLastIndex() {
        verbsPassed.remove(new Integer(index));
    }

    @FXML
    private void onAnswerKeyInput(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (isPassCondition()) {
                answerTextField.getStyleClass().removeAll(Style.Class.TextField.INCORRECT);
                answerTextField.requestLayout();
                updateStatus();
                if (isEndingCondition()) {
                    nihongo.showManageLists();
                    return;
                }
                randVerb();
                presentCurrentVerb();
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
            statusLabel.setText(current.getKanaBase());
            recoverStatus();
        }
    }
}
