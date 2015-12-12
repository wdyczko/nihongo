package org.drvad3r.nihongo;/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.drvad3r.nihongo.controller.*;
import org.drvad3r.nihongo.controller.dialog.Command;
import org.drvad3r.nihongo.define.Option;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Module;
import org.drvad3r.nihongo.model.Quest;
import org.drvad3r.nihongo.model.list.ModuleList;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.controller.dialog.WordEdit;
import org.drvad3r.nihongo.model.list.QuestList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
        try
        {
            Image image = new Image(new FileInputStream(new File(System.getProperty("user.dir") + Path.ICON)));
            this.primaryStage.getIcons().add(image);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        StorageManager storageManager = new StorageManager();
        ModuleList moduleList = storageManager.loadModulesDataFromFile(new File(System.getProperty("user.dir") + Path.MODULE_FILE));
        currentModule = moduleList.getModuleList().get(0);

        initializeRootLayout();
        showManageLists();
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

    public void showManageLists()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/ManageLists.fxml"));
            AnchorPane manageListsRoot = loader.load();
            rootLayout.setCenter(manageListsRoot);
            ManageLists controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showPronunciationAndKanji()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/PronunciationAndKanji.fxml"));
            AnchorPane pronunciationAndKanjiRoot = loader.load();
            rootLayout.setCenter(pronunciationAndKanjiRoot);
            PronunciationAndKanji controller = loader.getController();
            controller.setNihongo(this);
            controller.init();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showPronunciation()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/Pronunciation.fxml"));
            AnchorPane pronunciationRoot = loader.load();
            rootLayout.setCenter(pronunciationRoot);
            Pronunciation controller = loader.getController();
            controller.setNihongo(this);
            controller.init();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showVerbDeclination()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/VerbDeclination.fxml"));
            AnchorPane verbDeclinationRoot = loader.load();
            rootLayout.setCenter(verbDeclinationRoot);
            VerbDeclination controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void showConfiguration()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/Configuration.fxml"));
            AnchorPane configurationRoot = loader.load();
            rootLayout.setCenter(configurationRoot);
            Configuration controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showGeneralQuest()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/GeneralQuest.fxml"));
            AnchorPane configurationRoot = loader.load();
            rootLayout.setCenter(configurationRoot);
            GeneralQuest controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showWordEditDialog(Word word)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/dialog/WordEdit.fxml"));
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

    public boolean showCommandDialog(TableView tableView)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/dialog/Command.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Command");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.setX(primaryStage.getX() + tableView.getLayoutX() + 20);
            dialogStage.setY(primaryStage.getY() + tableView.getLayoutY() + 120);
            dialogStage.setWidth(tableView.getWidth() - 40);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            Command controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTableView(tableView);

            dialogStage.showAndWait();

            return true;
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
        Nihongo.currentModule = currentModule;
    }

    @FXML
    private void onWordLearn()
    {
        showPronunciationAndKanji();
    }

    @FXML
    private void onWordView()
    {
        showManageLists();
    }

    @FXML
    private void onPronunciation()
    {
        showPronunciation();
    }

    @FXML
    private void onVerbDeclination()
    {
        showVerbDeclination();
    }

    @FXML
    private void onClose()
    {
        System.exit(0);
    }

    @FXML
    public void onConfiguration()
    {
        showConfiguration();
    }

    public void onGeneralQuest()
    {
        showGeneralQuest();
    }
}
