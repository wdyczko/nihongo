package org.drvad3r.nihongo.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.drvad3r.nihongo.define.SessionKeys;
import org.drvad3r.nihongo.manager.SessionManager;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Quest;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.list.QuestList;

/**
 * Created by wdyczko on 10/30/2015.
 */
public class Command {
    private Stage dialogStage;
    private TableView tableView;

    private Label moduleNameLabel;

    @FXML
    private TextField commandTextField;

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void setTableView(TableView tableView)
    {
        this.tableView = tableView;
    }

    public void setModuleNameLabel(Label moduleNameLabel)
    {
        this.moduleNameLabel = moduleNameLabel;
    }

    @FXML
    private void onKeyEvent(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ESCAPE)
        {
            dialogStage.close();
        } else if (keyEvent.getCode() == KeyCode.ENTER)
        {
            processInput();
            dialogStage.close();
        }
    }

    private void processInput()
    {
        String input = commandTextField.getText().trim();
        if (input.equals("*"))
        {
            tableView.getSelectionModel().selectAll();
        } else if (input.matches("sel \\+\\d*"))
        {
            input = input.substring("sel ".length());
            input = input.replace("+", "");
            int value = Integer.parseInt(input);
            int start = tableView.getSelectionModel().getSelectedIndex();
            if (start >= 0)
            {
                for (int i = start; i <= start + value; i++)
                {
                    if (i > tableView.getItems().size())
                        break;
                    tableView.getSelectionModel().select(i);
                }
            }
        } else if (input.matches("sel -\\d*"))
        {
            input = input.substring("sel ".length());
            input = input.replace("-", "");
            int value = Integer.parseInt(input);
            int start = tableView.getSelectionModel().getSelectedIndex();
            if (start >= 0)
            {
                for (int i = start; i > start - value; i--)
                {
                    if (i < 0)
                        break;
                    tableView.getSelectionModel().clearSelection(i);
                    tableView.getSelectionModel().focus((i - 1 > 0) ? i - 1 : 0);
                }
            }

        } else if (input.matches("sel @[A-Za-z]*"))
        {
            input = input.substring("sel ".length());
            input = input.replace("@", "");
            tableView.getSelectionModel().clearSelection();
            String pattern = String.format("^[%s].*", input);
            for (int i = 0; i < tableView.getItems().size(); i++)
            {
                if (((Word) tableView.getItems().get(i)).getEnglish().matches(pattern))
                {
                    tableView.getSelectionModel().select(i);
                    tableView.getSelectionModel().focus(i);
                }
            }
        } else if (input.matches("sel \\d+-\\d+"))
        {
            input = input.substring("sel ".length());
            tableView.getSelectionModel().clearSelection();
            int[] range = fetchRange(input);
            int start = range[0];
            int end = range[1];
            if (start <= 0)
                start = 1;
            if (end >= tableView.getItems().size())
                end = tableView.getItems().size() - 1;
            for (int i = start - 1; i < end; i++)
            {
                tableView.getSelectionModel().select(i);
                tableView.getSelectionModel().focus(i);
            }
        } else if (input.matches("load quest \\d+"))
        {
            Integer questId = Integer.valueOf(input.substring("load quest ".length()));
            loadQuestMode(questId);
        } else if (input.matches("lq \\d+"))
        {
            Integer questId = Integer.valueOf(input.substring("lq ".length()));
            loadQuestMode(questId);
        }
    }

    private void loadQuestMode(Integer questId)
    {
        StorageManager storageManager = new StorageManager();
        QuestList questList = storageManager.loadQuestsDataFromFile();
        Quest quest = questList.getQuestList().get(questId - 1);
        SessionManager.setSessionItem(SessionKeys.CURRENT_QUEST_TITLE, quest.getTitle());
        moduleNameLabel.setText("Mode name: " + quest.getTitle());
    }

    private int[] fetchRange(String input)
    {
        int start;
        int end;
        String[] values = input.split("-");
        int first = Integer.valueOf(values[0]);
        int last = Integer.valueOf(values[1]);
        if (first > last)
        {
            start = last;
            end = first;
        } else
        {
            start = first;
            end = last;
        }
        return new int[]{start, end};
    }
}
