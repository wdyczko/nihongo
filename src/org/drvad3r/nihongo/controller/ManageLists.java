package org.drvad3r.nihongo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.define.SessionKeys;
import org.drvad3r.nihongo.manager.SessionManager;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.*;

import java.awt.*;
import java.io.File;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
public class ManageLists
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
    @FXML
    private Label amountLabel;

    private Nihongo nihongo;
    private String filePath;
    private File file;
    private StorageManager storageManager;
    private ModuleList moduleList;
    private static Robot robot;

    public ManageLists()
    {
        storageManager = new StorageManager();
        moduleList = storageManager.loadModulesDataFromFile(new File(System.getProperty("user.dir") + Path.MODULE_FILE));
        if (SessionManager.getInstance().sessionExists())
        {
            String modulePath = SessionManager.getInstance().getSessionItem(SessionKeys.CURRENT_MODULE_PATH);
            filePath = System.getProperty("user.dir") + Path.MODULE_RESOURCE_PATH + modulePath;
        } else
        {
            Module module = moduleList.getModuleList().get(0);
            SessionManager.getInstance().setSessionItem(SessionKeys.CURRENT_MODULE_NAME, module.getName());
            SessionManager.getInstance().setSessionItem(SessionKeys.CURRENT_MODULE_PATH, module.getFile());
            filePath = System.getProperty("user.dir") + Path.MODULE_RESOURCE_PATH + module.getFile();
        }
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
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
        wordTableView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
        {
            SessionManager.getInstance().setSessionItem(SessionKeys.CURRENT_MODULE_INDICES, wordTableView.getSelectionModel().getSelectedIndices().toString());
        }));
        wordTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        if(SessionManager.getInstance().sessionExists())
        {
            String indicies = SessionManager.getInstance().getSessionItem(SessionKeys.CURRENT_MODULE_INDICES);
            indicies = indicies.replaceAll("\\[|\\]", "");
            String[] splitted = indicies.split(", ");
            if(splitted != null && splitted.length > 1)
            {
                for(String s : splitted)
                {
                    wordTableView.getSelectionModel().select(Integer.parseInt(s));
                }
            }
            else
            {
                wordTableView.getSelectionModel().select(0);
            }
        }
        else
        {
            wordTableView.getSelectionModel().select(0);
        }
        moduleChoiceBox.setItems(moduleList.getModuleList());
        moduleChoiceBox.selectionModelProperty();
        if (SessionManager.getInstance().sessionExists())
        {
            String modulePath = SessionManager.getInstance().getSessionItem(SessionKeys.CURRENT_MODULE_PATH);
            String moduleName = SessionManager.getInstance().getSessionItem(SessionKeys.CURRENT_MODULE_NAME);
            moduleList.getModuleList().stream().filter(m -> m.getName().equals(moduleName) && m.getFile().equals(modulePath)).forEach(m -> {
                moduleChoiceBox.getSelectionModel().select(moduleList.getModuleList().indexOf(m));
            });
        }
        else
        {
            moduleChoiceBox.getSelectionModel().select(0);
        }
        amountLabel.setText("Words: " + wordTableView.getItems().size());
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
        amountLabel.setText("Words: " + wordTableView.getItems().size());
    }

    private void load()
    {
        file = new File(System.getProperty("user.dir") + Path.MODULE_RESOURCE_PATH + nihongo.getCurrentModule().getFile());
        wordTableView.setItems(storageManager.loadWordDataFromFile(file).getWords());
        amountLabel.setText("Words: " + wordTableView.getItems().size());
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
        if(nihongo != null && module != null)
        {
            nihongo.setCurrentModule(module);
            SessionManager.getInstance().setSessionItem(SessionKeys.CURRENT_MODULE_NAME, module.getName());
            SessionManager.getInstance().setSessionItem(SessionKeys.CURRENT_MODULE_PATH, module.getFile());
            load();
        }
    }

    @FXML
    private void onTableViewKeyEvent(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.E)
        {
            onEditWord();
        }
        else if (keyEvent.getCode() == KeyCode.A)
        {
            onNewWord();
        }
        else if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.DELETE)
        {
            onDeleteWord();
        }
        else if (keyEvent.getCode() == KeyCode.J && keyEvent.isControlDown())
        {
            int index = moduleChoiceBox.getSelectionModel().getSelectedIndex();
            if( index < moduleChoiceBox.getItems().size() - 1 )
            {
                moduleChoiceBox.getSelectionModel().clearSelection(index);
                moduleChoiceBox.getSelectionModel().select(index + 1);
            }
        }
        else if (keyEvent.getCode() == KeyCode.J)
        {
            robot.keyPress(java.awt.event.KeyEvent.VK_DOWN);
        }
        else if (keyEvent.getCode() == KeyCode.L && keyEvent.isControlDown())
        {
            nihongo.showPronunciationAndKanji();
        }
        else if (keyEvent.getCode() == KeyCode.K && keyEvent.isControlDown())
        {
            int index = moduleChoiceBox.getSelectionModel().getSelectedIndex();
            if( index > 0 )
            {
                moduleChoiceBox.getSelectionModel().clearSelection(index);
                moduleChoiceBox.getSelectionModel().select(index - 1);
            }
        }
        else if (keyEvent.getCode() == KeyCode.K)
        {
            robot.keyPress(java.awt.event.KeyEvent.VK_UP);
        }
    }
}
