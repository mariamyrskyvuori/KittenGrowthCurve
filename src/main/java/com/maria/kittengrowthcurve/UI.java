/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author maria
 */
public class UI {

    private KittenService service = new KittenService();
    private final Stage stage;

    UI(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        stage.setScene(getInitialScene());
        stage.show();
    }

    //alkunäkymä
    private Scene getInitialScene() {
        BorderPane initialLayout = new BorderPane();
        Scene addInitialScene = new Scene(initialLayout);
        VBox buttons = new VBox();
        buttons.setPadding(new Insets(20, 20, 20, 20));
        buttons.setSpacing(10);

        Button addLitter = new Button("Lisää pentue");
        buttons.getChildren().addAll(addLitter);
        initialLayout.setTop(buttons);

        addLitter.setOnMouseClicked((event) -> {
            stage.setScene(getAddLitterScene());
        });

        ArrayList<Litter> littersFromDb = service.getLitters();

        ComboBox<Litter> comboBox = new ComboBox<>();
        ObservableList<Litter> items = FXCollections.observableArrayList();
        int i = 0;
        while (i < littersFromDb.size()) {
            items.add(littersFromDb.get(i));
            i++;
        }
        comboBox.setItems(items);
        comboBox.setPromptText("Valitse pentue");
        comboBox.setOnAction((event) -> {
            Litter litter = comboBox.getValue();
            System.out.println("litterin nimi" + litter.getLitterName());
            stage.setScene(getLitterInfoScene(litter));
        });

        buttons.getChildren().addAll(comboBox);
        initialLayout.setTop(buttons);

        return addInitialScene;
    }

    //näkymä pentueen lisäämistä varten
    private Scene getAddLitterScene() {
        GridPane addLitterLayout = new GridPane();
        Scene addLitterScene = new Scene(addLitterLayout);

        Label pentue = new Label("Pentue");
        pentue.setFont(Font.font("Monospaced", 10));

        Label damName = new Label("Emo");
        TextField damField = new TextField();
        Label sireName = new Label("Isä");
        TextField sireField = new TextField();
        Label name = new Label("Nimi");
        TextField nameField = new TextField();
        Label establishment = new Label("Astutuspäivä");
        TextField establishmentField = new TextField();
        Label feedback = new Label("");

        addLitterLayout.setAlignment(Pos.CENTER);
        addLitterLayout.setVgap(10);
        addLitterLayout.setHgap(10);
        addLitterLayout.setPadding(new Insets(10, 10, 10, 10));

        Button save = new Button("Lisää pentue");
        Button back = new Button("Takaisin");

        addLitterLayout.add(damName, 0, 0);
        addLitterLayout.add(damField, 0, 1);
        addLitterLayout.add(sireName, 1, 0);
        addLitterLayout.add(sireField, 1, 1);
        addLitterLayout.add(name, 0, 2);
        addLitterLayout.add(nameField, 0, 3);
        addLitterLayout.add(establishment, 1, 2);
        addLitterLayout.add(establishmentField, 1, 3);
        addLitterLayout.add(save, 2, 4);
        addLitterLayout.add(back, 0, 4);
        addLitterLayout.add(feedback, 1, 4);

        back.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        save.setOnMouseClicked((event) -> {
            String dam = damField.getText();
            String sire = sireField.getText();
            String litterName = nameField.getText();
            String establishmentDate = establishmentField.getText();

            boolean litterAddSuccessfull = service.addLitter(dam, sire, litterName, establishmentDate);

            if (litterAddSuccessfull) {

                feedback.setText("Pentue lisätty!");

                damField.clear();
                sireField.clear();
                nameField.clear();
                establishmentField.clear();

            } else {
                feedback.setText("Ei onnistunut!");
            }

        });

        stage.setScene(getInitialScene());

        stage.show();

        return addLitterScene;
    }

    //näkymä pentujen lisäämistä varten
    private Scene getAddKittenScene(int litterId) {
        Label kitten = new Label("Pentu");
        kitten.setFont(Font.font("Monospaced", 10));
        GridPane addKittenLayout = new GridPane();
        Scene addKittenScene = new Scene(addKittenLayout);

        Label nameOfKitten = new Label("Nimi");
        TextField kittenNameField = new TextField();

        Label sexOfKitten = new Label("Sukupuoli");
        ComboBox<String> sexBox = new ComboBox<>();
        sexBox.getItems().addAll("Uros", "Naaras");
        sexBox.setPromptText("Valitse sukupuoli");

        Label birthTimeOfKitten = new Label("Syntymäaika (klo)");
        TextField birthTimeField = new TextField();
        Label weigthOfKitten = new Label("Paino");
        TextField weigthField = new TextField();
        Label regNoOfKitten = new Label("Rekisterinumero");
        TextField regNoField = new TextField("x");
        Label emsCodeOfKitten = new Label("EMS-koodi");
        TextField emsCodeField = new TextField("x");
        Label feedbackKitten = new Label();

        addKittenLayout.setAlignment(Pos.CENTER);
        addKittenLayout.setVgap(10);
        addKittenLayout.setHgap(10);
        addKittenLayout.setPadding(new Insets(10, 10, 10, 10));

        Button saveKitten = new Button("Lisää pentu");
        Button backKitten = new Button("Takaisin");

        addKittenLayout.add(nameOfKitten, 0, 0);
        addKittenLayout.add(kittenNameField, 0, 1);
        addKittenLayout.add(sexOfKitten, 1, 0);
        addKittenLayout.add(sexBox, 1, 1);
        addKittenLayout.add(birthTimeOfKitten, 0, 2);
        addKittenLayout.add(birthTimeField, 0, 3);
        addKittenLayout.add(weigthOfKitten, 1, 2);
        addKittenLayout.add(weigthField, 1, 3);
        addKittenLayout.add(regNoOfKitten, 0, 4);
        addKittenLayout.add(regNoField, 0, 5);
        addKittenLayout.add(emsCodeOfKitten, 1, 4);
        addKittenLayout.add(emsCodeField, 1, 5);
        addKittenLayout.add(saveKitten, 2, 6);
        addKittenLayout.add(backKitten, 0, 6);
        addKittenLayout.add(feedbackKitten, 1, 6);

        backKitten.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        saveKitten.setOnMouseClicked((event) -> {
            String kittenName = kittenNameField.getText();
            String sex = sexBox.getValue();
            String birthTime = birthTimeField.getText();
            String weigth = weigthField.getText();
            String regNo = regNoField.getText();
            String emsCode = emsCodeField.getText();

            boolean kittenAddSuccessfull = service.addKitten(litterId, kittenName, sex, birthTime, Integer.valueOf(weigth), regNo, emsCode);

            if (kittenAddSuccessfull) {

                feedbackKitten.setText("Pentu lisätty!");

                kittenNameField.clear();
                birthTimeField.clear();
                weigthField.clear();

            } else {
                feedbackKitten.setText("Ei onnistunut!");
            }

        });

        stage.setScene(getInitialScene());

        stage.show();

        return addKittenScene;
    }

    //näkymä pentueen tiedoista
    private Scene getLitterInfoScene(Litter litter) {

        ArrayList<Kitten> kittens = service.getKittensByLitterId(litter.getId());
        litter.setKittens(kittens);
        litter.getMaleKittenCount();
        litter.getFemaleKittenCount();

        GridPane litterInfoLayout = new GridPane();
        Text info = new Text(litter.getLitterName());
        info.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        litterInfoLayout.add(info, 0, 0);
        Text birth = new Text("Syntymäaika:");
        birth.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(birth, 0, 1);
        Text birthInfo = new Text(litter.getBirth().toString());
        litterInfoLayout.add(birthInfo, 1, 1);
        Text delivery = new Text("Luovutusaika:");
        delivery.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(delivery, 0, 2);
        Text deliveryInfo = new Text(litter.getDeliveryDate().toString());
        litterInfoLayout.add(deliveryInfo, 1, 2);
        Text d = new Text("Emo:");
        d.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(d, 0, 3);
        Text damInfo = new Text(litter.getDam());
        litterInfoLayout.add(damInfo, 1, 3);
        Text s = new Text("Isä:");
        s.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(s, 0, 4);
        Text sireInfo = new Text(litter.getSire());
        litterInfoLayout.add(sireInfo, 1, 4);
        Text count = new Text("Lukumäärä:");
        count.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(count, 0, 5);
        Text countInfo = new Text(kittens != null ? String.valueOf(litter.getKittenCount()) : "");
        litterInfoLayout.add(countInfo, 1, 5);
        Text countMales = new Text("Uroksia:");
        countMales.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(countMales, 0, 6);
        Text countMalesInfo = new Text(kittens != null ? String.valueOf(litter.getMaleKittenCount()) : "");
        litterInfoLayout.add(countMalesInfo, 1, 6);
        Text countFemales = new Text("Naaraita:");
        countFemales.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(countFemales, 0, 7);
        Text countFemalesInfo = new Text(kittens != null ? String.valueOf(litter.getFemaleKittenCount()) : "");
        litterInfoLayout.add(countFemalesInfo, 1, 7);
        Text emsCodes = new Text("Värit:");
        emsCodes.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(emsCodes, 0, 8);
        Text emsInfo = new Text(kittens != null ? litter.getEmsKitten() : "");
        litterInfoLayout.add(emsInfo, 1, 8);
        Text dec = new Text("Menehtyneet:");
        dec.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(dec, 0, 9);
        Text decInfo = new Text("");
        litterInfoLayout.add(decInfo, 1, 9);
        Button edit = new Button("Muokkaa");
        litterInfoLayout.add(edit, 0, 11);
        Button addKitten = new Button("Lisää pentu");
        litterInfoLayout.add(addKitten, 0, 12);
        Button back = new Button("takaisin");
        litterInfoLayout.add(back, 0, 13);
        int litterId = litter.getId();

        addKitten.setOnMouseClicked((event) -> {
            stage.setScene(getAddKittenScene(litter.getId()));
        });

        back.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        litterInfoLayout.setPadding(new Insets(30, 30, 30, 30));

        litterInfoLayout.setVgap(25);
        litterInfoLayout.setHgap(15);

        litterInfoLayout.setAlignment(Pos.TOP_LEFT);

        /*ObservableList infoList = litterInfoLayout.getChildren();

        infoList.addAll(info, birth, birthInfo, delivery, deliveryInfo, d, damInfo, s,
                sireInfo, count, countInfo, countMales, countMalesInfo,
                countFemales, countFemalesInfo, emsCodes, emsInfo, dec, decInfo);

        litterInfoLayout.getChildren().add(edit);
        litterInfoLayout.getChildren().add(addKitten);*/
        BorderPane curve = new BorderPane();
        curve.setRight(litterInfoLayout);
        curve.setLeft(back);

        return new Scene(curve);
    }

    
}
