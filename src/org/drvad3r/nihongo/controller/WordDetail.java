package org.drvad3r.nihongo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Module;
import org.drvad3r.nihongo.model.ModuleList;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.WordList;

import java.io.File;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
public class WordDetail
{
    @FXML
    private TableView<org.drvad3r.nihongo.model.Word> wordTableView;
    @FXML
    private TableColumn<org.drvad3r.nihongo.model.Word, String> originalColumn;
    @FXML
    private TableColumn<org.drvad3r.nihongo.model.Word, String> englishColumn;

    @FXML
    private Label originalLabel;
    @FXML
    private Label englishLabel;
    @FXML
    private Label pronounceLabel;
    @FXML
    private Label polishLabel;
    @FXML
    private ChoiceBox<Module> moduleChoiceBox;

    private Nihongo nihongo;
    private String filePath;
    private File file;
    private StorageManager storageManager;
    private ModuleList moduleList;

    public WordDetail()
    {
        storageManager = new StorageManager();
        moduleList = storageManager.loadModulesDataFromFile(new File(System.getProperty("user.dir") + Path.MODULE_FILE));
        Module module = moduleList.getModuleList().get(0);
        filePath = System.getProperty("user.dir") + Path.MODULE_RESOURCE_PATH + module.getFile();
    }

    @FXML
    private void initialize()
    {
        originalColumn.setCellValueFactory(cellData -> cellData.getValue().originalProperty());
        englishColumn.setCellValueFactory(cellData -> cellData.getValue().englishProperty());
        storageManager = new StorageManager();
        file = new File(filePath);
        wordTableView.setItems(storageManager.loadWordDataFromFile(file).getWords());
        wordTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showWordDetails(newValue)));
        wordTableView.getSelectionModel().select(0);
        moduleChoiceBox.setItems(moduleList.getModuleList());
        moduleChoiceBox.selectionModelProperty();
        moduleChoiceBox.getSelectionModel().select(0);
    }

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    public void showWordDetails(org.drvad3r.nihongo.model.Word word)
    {
        if (word != null)
        {
            originalLabel.setText(word.getOriginal());
            pronounceLabel.setText(word.getPronounce());
            englishLabel.setText(word.getEnglish());
            polishLabel.setText(word.getPolish());
        }
        else
        {
            originalLabel.setText("");
            pronounceLabel.setText("");
            englishLabel.setText("");
            polishLabel.setText("");
        }
    }

    private void save()
    {
        WordList wordList = new WordList();
        wordList.setWords(wordTableView.getItems());
        storageManager.saveWordDataToFile(file, wordList);
    }

    private void load()
    {
        File file = new File(System.getProperty("user.dir") + Path.MODULE_RESOURCE_PATH + nihongo.getCurrentModule().getFile());
        wordTableView.setItems(storageManager.loadWordDataFromFile(file).getWords());
    }

    @FXML
    private void onDeleteWord()
    {
        int selectedIndex = wordTableView.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0)
        {
            wordTableView.getItems().remove(selectedIndex);
            save();
        }
    }

    @FXML
    private void onNewWord()
    {
        org.drvad3r.nihongo.model.Word word = new org.drvad3r.nihongo.model.Word();
        boolean okClicked = nihongo.showWordEditDialog(word);
        if (okClicked)
        {
            wordTableView.getItems().add(word);
            save();
        }
    }

    @FXML
    private void onEditWord()
    {
        org.drvad3r.nihongo.model.Word word = wordTableView.getSelectionModel().getSelectedItem();
        if (word != null)
        {
            boolean okClicked = nihongo.showWordEditDialog(word);
            if(okClicked)
            {
                showWordDetails(word);
                save();
            }
        }
    }

    @FXML
    private void onModuleSelect(ActionEvent actionEvent)
    {
        ChoiceBox<Module> choice = (ChoiceBox<Module>) actionEvent.getSource();
        Module module = choice.getSelectionModel().getSelectedItem();
        if(nihongo != null)
        {
            nihongo.setCurrentModule(module);
            load();
        }
    }
}