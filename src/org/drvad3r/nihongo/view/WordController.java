package org.drvad3r.nihongo.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.WordList;

import java.io.File;

/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */
public class WordController
{
    @FXML
    private TableView<Word> wordTableView;
    @FXML
    private TableColumn<Word, String> originalColumn;
    @FXML
    private TableColumn<Word, String> englishColumn;

    @FXML
    private Label originalLabel;
    @FXML
    private Label englishLabel;
    @FXML
    private Label pronounceLabel;
    @FXML
    private Label polishLabel;

    private Nihongo nihongo;
    private String filePath;
    private File file;
    private StorageManager storageManager;

    public WordController()
    {
        filePath = System.getProperty("user.dir") + "\\resources\\data\\human_body.xml";
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
    }

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }

    public void showWordDetails(Word word)
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
        Word word = new Word();
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
        Word word = wordTableView.getSelectionModel().getSelectedItem();
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
}
