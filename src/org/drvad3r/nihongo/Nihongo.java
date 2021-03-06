package org.drvad3r.nihongo;/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.drvad3r.nihongo.controller.*;
import org.drvad3r.nihongo.controller.dialog.Command;
import org.drvad3r.nihongo.controller.dialog.CreateList;
import org.drvad3r.nihongo.controller.dialog.WordEdit;
import org.drvad3r.nihongo.define.Path;
import org.drvad3r.nihongo.manager.StorageManager;
import org.drvad3r.nihongo.model.Module;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.model.list.ModuleList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Nihongo extends Application {
    public static final String APP_NAME = "Nihongo";
    public static Stage primaryStage;
    static BorderPane rootLayout;
    static Module currentModule;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Nihongo.primaryStage = primaryStage;
        Nihongo.primaryStage.setTitle("Nihongo");
        try {
            Image image = new Image(new FileInputStream(new File(System.getProperty("user.dir") + Path.ICON)));
            Nihongo.primaryStage.getIcons().add(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StorageManager storageManager = new StorageManager();
        ModuleList moduleList = storageManager.loadModulesDataFromFile(new File(System.getProperty("user.dir") + Path.MODULE_FILE));
        currentModule = moduleList.getModuleList().get(0);

        initializeRootLayout();
        showManageLists();
    }

    private void initializeRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("Root.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showManageLists() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/ManageLists.fxml"));
            AnchorPane manageListsRoot = loader.load();
            rootLayout.setCenter(manageListsRoot);
            ManageLists controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showVerbDeclination() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/VerbDeclination.fxml"));
            AnchorPane verbDeclinationRoot = loader.load();
            rootLayout.setCenter(verbDeclinationRoot);
            VerbDeclination controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showAdjectivesDeclination() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/AdjectivesDeclination.fxml"));
            AnchorPane adjectivesDeclination = loader.load();
            rootLayout.setCenter(adjectivesDeclination);
            AdjectivesDeclination controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showConfiguration() {
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

    public void showGeneralQuest() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/WordsQuest.fxml"));
            AnchorPane configurationRoot = loader.load();
            rootLayout.setCenter(configurationRoot);
            WordsQuest controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showWordEditDialog(Word word) {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCommandDialog(TableView tableView, Label modeNameLabel) {
        try {
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
            controller.setModuleNameLabel(modeNameLabel);

            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showCreateList(ModuleList moduleList) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/dialog/CreateList.fxml"));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Name");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            CreateList controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setModuleList(moduleList);

            dialogStage.showAndWait();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Module getCurrentModule() {
        return currentModule;
    }

    public void setCurrentModule(Module currentModule) {
        Nihongo.currentModule = currentModule;
    }

    public void onWordView() {
        showManageLists();
    }

    public void onVerbDeclination() {
        showVerbDeclination();
    }

    public void onAdjectiveDeclination() {
        showAdjectivesDeclination();
    }

    public void onClose() {
        System.exit(0);
    }

    public void onConfiguration() {
        showConfiguration();
    }

    public void onGeneralQuest() {
        showGeneralQuest();
    }

    public void extendWindowTitle(String extension) {
        primaryStage.setTitle(String.format("%s - %s", APP_NAME, extension));
    }

    public void recoverWindowTitle() {
        primaryStage.setTitle(APP_NAME);
    }
}
