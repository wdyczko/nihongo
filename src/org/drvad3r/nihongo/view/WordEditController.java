package org.drvad3r.nihongo.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.drvad3r.nihongo.model.Word;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
public class WordEditController
{
    @FXML
    private TextField originalTextField;
    @FXML
    private TextField pronounceTextField;
    @FXML
    private TextField englishTextField;
    @FXML
    private TextField polishTextField;

    private Stage dialogStage;
    private Word word;
    private boolean okClicked = false;

    @FXML
    private void initialize()
    {

    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setWord(Word word)
    {
        this.word = word;
        originalTextField.setText(word.getOriginal());
        pronounceTextField.setText(word.getPronounce());
        englishTextField.setText(word.getEnglish());
        polishTextField.setText(word.getPolish());
    }

    public boolean isOkClicked()
    {
        return okClicked;
    }

    @FXML
    private void onOk()
    {
        if (isInputValid())
        {
            word.setOriginal(originalTextField.getText());
            word.setPronounce(pronounceTextField.getText());
            word.setEnglish(englishTextField.getText());
            word.setPolish(polishTextField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void onCancel()
    {
        dialogStage.close();
    }

    private boolean isInputValid()
    {
        return (!originalTextField.getText().isEmpty() && !pronounceTextField.getText().isEmpty() && !englishTextField.getText().isEmpty() && !polishTextField.getText().isEmpty());
    }
}
