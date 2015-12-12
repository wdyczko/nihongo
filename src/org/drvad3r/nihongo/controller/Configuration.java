package org.drvad3r.nihongo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.define.Option;
import org.drvad3r.nihongo.define.SessionKeys;
import org.drvad3r.nihongo.manager.SessionManager;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Quest;
import org.drvad3r.nihongo.model.list.QuestList;

import javax.crypto.AEADBadTagException;

/**
 * Created by Wiktor on 2015-12-05.
 */
public class Configuration {
    private Nihongo nihongo;
    private QuestList questList;
    private StorageManager storageManager;

    @FXML
    private RadioButton englishRadioButton;
    @FXML
    private RadioButton originalRadioButton;
    @FXML
    private RadioButton pronounceRadioButton;
    @FXML
    private RadioButton localRadioButton;
    @FXML
    private CheckBox englishCheckBox;
    @FXML
    private CheckBox originalCheckBox;
    @FXML
    private CheckBox pronounceCheckBox;
    @FXML
    private CheckBox localCheckBox;
    @FXML
    private Button loadButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField titleTextField;
    @FXML
    private ComboBox<Quest> questComboBox;

    public Configuration()
    {
        storageManager = new StorageManager();
        questList = storageManager.loadQuestsDataFromFile();
    }

    public Nihongo getNihongo()
    {
        return nihongo;
    }

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    public void initialize()
    {
        questComboBox.setItems(questList.getQuestList());
    }

    private void load(Quest quest)
    {
        Option input = Option.toOption(Integer.parseInt(quest.getInput()));
        int output = Integer.parseInt(quest.getOutput());
        switch(input)
        {
            case ENGLISH: englishRadioButton.setSelected(true); break;
            case ORIGINAL: originalRadioButton.setSelected(true); break;
            case PRONOUNCE: pronounceRadioButton.setSelected(true); break;
            case LOCAL: localRadioButton.setSelected(true); break;
        }

        englishCheckBox.setSelected(false);
        originalCheckBox.setSelected(false);
        pronounceCheckBox.setSelected(false);
        localCheckBox.setSelected(false);

        if(Option.isEnglish(output))
        {
            englishCheckBox.setSelected(true);
        }
        if(Option.isOriginal(output))
        {
            originalCheckBox.setSelected(true);
        }
        if(Option.isPronounce(output))
        {
            pronounceCheckBox.setSelected(true);
        }
        if(Option.isLocal(output))
        {
            localCheckBox.setSelected(true);
        }

        titleTextField.setText(quest.getTitle());
        SessionManager.getInstance().setSessionItem(SessionKeys.CURRENT_QUEST_TITLE, quest.getTitle());
    }

    @FXML
    private void onLoad(ActionEvent event)
    {
        Quest quest = questComboBox.getSelectionModel().getSelectedItem();
        if (quest != null)
        {
            load(quest);
        }
    }

    @FXML
    private void onAdd(ActionEvent event)
    {
        Quest quest = new Quest();
        if(englishRadioButton.isSelected()) quest.setInput(Option.ENGLISH.toString());
        else if(originalRadioButton.isSelected()) quest.setInput(Option.ORIGINAL.toString());
        else if(pronounceRadioButton.isSelected()) quest.setInput(Option.PRONOUNCE.toString());
        else if(localRadioButton.isSelected()) quest.setInput(Option.LOCAL.toString());
        else quest.setInput(Option.ENGLISH.toString());
        int output = 0;
        if(englishCheckBox.isSelected())
            output |= 1;
        if(originalCheckBox.isSelected())
            output |= 2;
        if(pronounceCheckBox.isSelected())
            output |= 4;
        if(localCheckBox.isSelected())
            output |= 8;
        quest.setOutput(Integer.toString(output));
        String title = titleTextField.getText().trim();
        quest.setTitle(title);
        boolean exists = false;
        for (Quest q :
                questList.getQuestList())
        {
            if (q.getTitle().equals(quest.getTitle()))
            {
                exists = true;
            }
        }

        if(exists)
        {
            questList.getQuestList().forEach(element -> {
                if (element.getTitle().equals(title))
                {
                    element.setInput(quest.getInput());
                    element.setOutput(quest.getOutput());
                    questComboBox.getSelectionModel().select(element);
                }
            });
        } else {
            questList.getQuestList().add(quest);
            questComboBox.getSelectionModel().selectLast();
        }
        titleTextField.setText("");
        storageManager.saveQuestDataToFile(questList);
    }
}
