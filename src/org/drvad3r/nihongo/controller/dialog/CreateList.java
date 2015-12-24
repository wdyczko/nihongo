package org.drvad3r.nihongo.controller.dialog;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Module;
import org.drvad3r.nihongo.model.list.ModuleList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Author: Wiktor
 * Creation date: 2015-12-24.
 */
public class CreateList {

    public TextField nameTextField;
    private Stage dialogStage;
    private ModuleList moduleList;
    private final StorageManager storageManager;

    public CreateList()
    {
        storageManager = new StorageManager();
    }

    public void setModuleList(ModuleList moduleList)
    {
        this.moduleList = moduleList;
    }

    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }

    public void onKeyEvent(KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {

            if(moduleList != null)
            {
                String[] elements = processInput(nameTextField.getText());
                String fileName = elements[elements.length-1];
                String path = createDirectoryStructure(elements);
                moduleList.getModuleList().add(new Module(fileName, "generic_module" + path + fileName + ".xml"));
                storageManager.saveModulesDataToFile(new File(System.getProperty("user.dir") + Path.MODULE_FILE), moduleList);
            }
            dialogStage.close();
        } else if (keyEvent.getCode() == KeyCode.ESCAPE)
        {
            dialogStage.close();
        }
    }

    private String createDirectoryStructure(String[] elements)
    {
        String path = "\\";
        for (int i = 0; i < elements.length - 1; i++)
        {
            path += elements[i] + "\\";
        }
        try
        {
            Files.createDirectories(new File(System.getProperty("user.dir") + Path.MODULE_GENERIC_PATH + path).toPath());
            Files.copy(new File(System.getProperty("user.dir") + Path.MODULE_TEMPLATE_FILE).toPath(),
                    new File(System.getProperty("user.dir") + Path.MODULE_GENERIC_PATH + path + "\\" + elements[elements.length - 1] + ".xml").toPath());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return path;
    }

    private String[] processInput(String text)
    {
        String[] result = new String[0];
        if (text.contains("\\"))
        {
            result = text.split("\\\\");
        }
        return result;
    }

}
