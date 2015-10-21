package org.drvad3r.nihongo;/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Module;
import org.drvad3r.nihongo.model.ModuleList;
import org.drvad3r.nihongo.model.WordList;
import org.drvad3r.nihongo.controller.WordDetail;
import org.drvad3r.nihongo.controller.WordEdit;
import org.drvad3r.nihongo.controller.WordLearn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Nihongo extends Application
{
    Stage primaryStage;
    static BorderPane rootLayout;
    static Module currentModule;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Nihongo");

        StorageManager storageManager = new StorageManager();
        ModuleList moduleList = storageManager.loadModulesDataFromFile(new File(System.getProperty("user.dir") + Path.MODULE_FILE));
        currentModule = moduleList.getModuleList().get(0);

        initializeRootLayout();
        showWordView();
    }

    private void initializeRootLayout()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("Root.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showWordView()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/WordDetail.fxml"));
            AnchorPane wordView = loader.load();
            rootLayout.setCenter(wordView);
            WordDetail controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void showLearnView(WordList wordList)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/WordLearn.fxml"));
            AnchorPane wordLearnView = loader.load();
            rootLayout.setCenter(wordLearnView);
            WordLearn controller = loader.getController();
            controller.setNihongo(this);
            controller.setWordList(wordList);
            controller.init();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public boolean showWordEditDialog(org.drvad3r.nihongo.model.Word word)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/WordEdit.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Word");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            WordEdit controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setWord(word);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Module getCurrentModule()
    {
        return currentModule;
    }

    public void setCurrentModule(Module currentModule)
    {
        this.currentModule = currentModule;
    }

    @FXML
    private void onWordLearn()
    {
        StorageManager storageManager = new StorageManager();
        WordList list = storageManager.loadWordDataFromFile(new File(System.getProperty("user.dir") + Path.MODULE_RESOURCE_PATH + currentModule.getFile()));
        showLearnView(list);
    }

    @FXML
    private void onWordView()
    {
        showWordView();
    }

    @FXML
    private void onClose()
    {
        System.exit(0);
    }
}
