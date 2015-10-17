package org.drvad3r.nihongo;/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.drvad3r.nihongo.model.Word;
import org.drvad3r.nihongo.view.WordController;
import org.drvad3r.nihongo.view.WordEditController;

import java.io.IOException;

public class Nihongo extends Application
{
    Stage primaryStage;
    BorderPane rootLayout;

    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Nihongo");

        initializeRootLayout();
        showWordView();
    }

    private void initializeRootLayout()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("RootView.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void showWordView()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/WordView.fxml"));
            AnchorPane wordView = loader.load();
            rootLayout.setCenter(wordView);
            WordController controller = loader.getController();
            controller.setNihongo(this);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean showWordEditDialog(Word word)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Nihongo.class.getResource("view/WordEditView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Word");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            WordEditController controller = loader.getController();
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

    @FXML
    private void onClose()
    {
        System.exit(0);
    }
}
