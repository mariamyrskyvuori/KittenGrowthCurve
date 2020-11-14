package com.maria.kittengrowthcurve;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * JavaFX App
 */
public class App extends Application {

    

    //Luo käyttöliittymän luokan ja käynnistää ohjelman
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Kasvukäyrä");
        UI ui = new UI(stage);
        stage.setWidth(800);
        stage.setHeight(720);
        ui.start();
    }

    public static void main(String[] args) {
        launch();
    }

}