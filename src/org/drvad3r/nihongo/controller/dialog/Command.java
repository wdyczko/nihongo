package org.drvad3r.nihongo.controller.dialog;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by wdyczko on 10/30/2015.
 */
public class Command {
    private Stage dialogStage;
    private TableView tableView;

    @FXML
    private TextField commandTextField;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTableView(TableView tableView)
    {
        this.tableView = tableView;
    }

    @FXML
    private void onKeyEvent(KeyEvent keyEvent)
    {
        if(keyEvent.getCode() == KeyCode.ESCAPE)
        {
            dialogStage.close();
        }
        else if(keyEvent.getCode() == KeyCode.ENTER)
        {
            processInput();
            dialogStage.close();
        }
    }

    private void processInput()
    {
        String input = commandTextField.getText().trim();
        if(input.equals("*"))
        {
            tableView.getSelectionModel().selectAll();
        }
        else if(input.matches("\\+\\d*"))
        {
            input = input.replace("+", "");
            int value = Integer.parseInt(input);
            int start = tableView.getSelectionModel().getSelectedIndex();
            if(start >= 0)
            {
                for(int i = start; i <= start + value; i++)
                {
                    if(i > tableView.getItems().size())
                        break;
                    tableView.getSelectionModel().select(i);
                }
            }
        }
        else if(input.matches("-\\d*"))
        {
            input = input.replace("-", "");
            int value = Integer.parseInt(input);
            int start = tableView.getSelectionModel().getSelectedIndex();
            if(start >= 0)
            {
                for(int i = start; i > start - value; i--)
                {
                    if(i < 0)
                        break;
                    tableView.getSelectionModel().clearSelection(i);
                    tableView.getSelectionModel().focus( ( i - 1 > 0 ) ? i - 1 : 0);
                }
            }

        }
    }
}
