package org.drvad3r.nihongo;/**
 * Author: Wiktor
 * Creation: 2015-10-17
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
