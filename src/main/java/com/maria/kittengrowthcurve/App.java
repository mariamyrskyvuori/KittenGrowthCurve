package com.maria.kittengrowthcurve;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    

    //Luo käyttöliittymän luokan ja käynnistää ohjelman
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("KittenGrowthCurve");
        UI ui = new UI(stage);
        stage.setWidth(1024);
        stage.setHeight(768);
        ui.start();
    }

    public static void main(String[] args) {
        launch();
    }

}