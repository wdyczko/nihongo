package org.drvad3r.nihongo.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.drvad3r.nihongo.Nihongo;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Word;

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

    public WordController()
    {
    }

    @FXML
    private void initialize()
    {
        originalColumn.setCellValueFactory(cellData -> cellData.getValue().originalProperty());
        englishColumn.setCellValueFactory(cellData -> cellData.getValue().englishProperty());
        StorageManager storageManager = new StorageManager();
        File file = new File(System.getProperty("user.dir") + "\\resources\\data\\simple.xml");
        wordTableView.setItems(storageManager.loadWordDataFromFile(file).getWords());
    }

    public void setNihongo(Nihongo nihongo)
    {
        this.nihongo = nihongo;
    }
}
