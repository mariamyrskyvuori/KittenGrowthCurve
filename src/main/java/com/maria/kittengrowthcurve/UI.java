/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
import com.maria.kittengrowthcurve.domain.Weight;
import com.maria.kittengrowthcurve.formfields.ComboBoxFormField;
import com.maria.kittengrowthcurve.formfields.DatePickerFormField;
import com.maria.kittengrowthcurve.formfields.TextFormField;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author maria
 */
public class UI {

    private KittenService service = new KittenService();
    private final Stage stage;
    private int activeLitterId = -1;

    UI(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        stage.setScene(getInitialScene());
        stage.show();
    }

    //alkunäkymä
    private Scene getInitialScene() {
        //Yläosa: Pentueen lisäys ja valinta.

        BorderPane initialLayout = new BorderPane();
        Scene addInitialScene = new Scene(initialLayout);
        HBox topButtonsHbox = new HBox();
        topButtonsHbox.setPadding(new Insets(20, 30, 10, 30));
        topButtonsHbox.setSpacing(33);

        Button addLitter = new Button("Lisää pentue");
        topButtonsHbox.getChildren().addAll(addLitter);

        addLitter.setOnMouseClicked((event) -> {
            stage.setScene(getAddLitterScene());
        });

        ArrayList<Litter> littersFromDb = service.getLitters();
        for (Litter litter : littersFromDb) {
            ArrayList<Kitten> kittens = service.getKittensByLitterId(litter.getId());
            for (int i = 0; i < kittens.size(); i++) {
                ArrayList<Weight> weightList = service.getKittenWeightsByKittenId(kittens.get(i).getId());
                kittens.get(i).setWeightList(weightList);
            }
            litter.setKittens(kittens);
        }

        Button diaryButton = new Button("Päiväkirja");
        diaryButton.setAlignment(Pos.TOP_RIGHT);

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
            activeLitterId = litter.getId();
            //stage.setScene(getLitterInfoScene(litter));
            if (activeLitterId != -1) {
                initialLayout.setBottom(getLitterInfoLayout(litter));
                initialLayout.setLeft(getKittenInfoLayout(litter));
                initialLayout.setCenter(getWeightCurveLayout(litter));
                diaryButton.setOnMouseClicked((diaryEvent) -> {
                    stage.setScene(getDiaryScene(litter));
                });
                topButtonsHbox.getChildren().addAll(diaryButton);
            } else {
                //initialLayout.setBottom();
            }

        });

        if (activeLitterId != -1) {
            for (Litter litter : littersFromDb) {
                if (litter.getId() == activeLitterId) {
                    initialLayout.setBottom(getLitterInfoLayout(litter));
                    initialLayout.setLeft(getKittenInfoLayout(litter));
                    initialLayout.setCenter(getWeightCurveLayout(litter));
                    diaryButton.setOnMouseClicked((event) -> {
                        stage.setScene(getDiaryScene(litter));
                    });
                    topButtonsHbox.getChildren().addAll(diaryButton);
                }
            }

        }

        topButtonsHbox.getChildren().addAll(comboBox);

        initialLayout.setTop(topButtonsHbox);

        return addInitialScene;
    }

    //näkymä pentueen lisäämistä varten
    private Scene getAddLitterScene() {
        GridPane addLitterLayout = new GridPane();
        BorderPane addLitterPane = new BorderPane();
        Scene addLitterScene = new Scene(addLitterPane);

        Label pentue = new Label("Pentue");
        pentue.setFont(Font.font("Monospaced", 10));

        TextFormField damField = new TextFormField("Emo", true);
        TextFormField sireField = new TextFormField("Isä", true);
        TextFormField nameField = new TextFormField("Nimi", true);
        DatePickerFormField establishmentField = new DatePickerFormField("Astutuspäivä", true);
        Label feedback = new Label("");

        addLitterLayout.setAlignment(Pos.TOP_LEFT);
        addLitterLayout.setVgap(10);
        addLitterLayout.setHgap(10);
        addLitterLayout.setPadding(new Insets(20, 30, 10, 30));

        Button save = new Button("Lisää pentue");
        

        addLitterLayout.add(damField.getNode(), 0, 0);
        addLitterLayout.add(sireField.getNode(), 1, 0);
        addLitterLayout.add(nameField.getNode(), 0, 1);
        addLitterLayout.add(establishmentField.getNode(), 1, 1);
        addLitterLayout.add(save, 1, 4);
        addLitterLayout.add(feedback, 1, 5);

        

        save.setOnMouseClicked((event) -> {
            boolean isValidDamField = damField.isValid();
            boolean isValidSireField = sireField.isValid();
            boolean isValidNameField = nameField.isValid();
            boolean isValidEstablishmentField = establishmentField.isValid();

            String dam = damField.getValue();
            String sire = sireField.getValue();
            String litterName = nameField.getValue();
            LocalDate establishmentDate = establishmentField.getValue();

            boolean litterAddSuccessfull = false;

            if (isValidDamField && isValidSireField && isValidNameField && isValidEstablishmentField) {
                litterAddSuccessfull = service.addLitter(dam, sire, litterName, establishmentDate);
            }

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
        
        
        Button back = new Button("Takaisin");
        addLitterPane.setTop(addLitterLayout);
        addLitterPane.setBottom(back);
        addLitterPane.setMargin(back, new Insets(20, 30, 10, 30));
        back.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        stage.setScene(getInitialScene());

        stage.show();

        return addLitterScene;
    }

    //näkymä pentujen lisäämistä varten
    private Scene getAddOrUpdateKittenScene(int litterId, LocalDate birth, Kitten kitten) {

        Label kittenLabel = new Label("Pentu");
        kittenLabel.setFont(Font.font("Monospaced", 10));
        BorderPane handleKittensPane = new BorderPane();
        GridPane addKittenLayout = new GridPane();
        Scene addKittenScene = new Scene(handleKittensPane);

        TextFormField nameOfKittenField = new TextFormField("Nimi", true);
        ComboBoxFormField sexBox = new ComboBoxFormField("Sukupuoli", true);
        sexBox.setItems("Uros", "Naaras");
        sexBox.setPromptText("Valitse sukupuoli");
        TextFormField birthTimeOfKittenField = new TextFormField("Virallinen nimi", false);
        TextFormField weightOfKittenField = new TextFormField("Syntymäpaino", true);
        TextFormField regNoOfKittenField = new TextFormField("Rekisterinumero", false);
        TextFormField emsCodeOfKittenField = new TextFormField("EMS-koodi", false);
        if (kitten != null) {
            nameOfKittenField.setValue(kitten.getKittenName());
            sexBox.setValue(kitten.getSex());
            birthTimeOfKittenField.setValue(kitten.getBirthTime());
            weightOfKittenField.setValue(String.valueOf(kitten.getWeightList().get(0).getWeight()));
            regNoOfKittenField.setValue(kitten.getRegno());
            emsCodeOfKittenField.setValue(kitten.getEms());
        }

        Label feedbackKitten = new Label();

        addKittenLayout.setAlignment(Pos.TOP_LEFT);
        addKittenLayout.setVgap(10);
        addKittenLayout.setHgap(10);
        addKittenLayout.setPadding(new Insets(20, 30, 10, 30));

        Button saveKitten = new Button("Lisää pentu");

        addKittenLayout.add(nameOfKittenField.getNode(), 0, 0);
        addKittenLayout.add(sexBox.getNode(), 1, 0);
        addKittenLayout.add(birthTimeOfKittenField.getNode(), 0, 1);
        addKittenLayout.add(weightOfKittenField.getNode(), 1, 1);
        addKittenLayout.add(regNoOfKittenField.getNode(), 0, 2);
        addKittenLayout.add(emsCodeOfKittenField.getNode(), 1, 2);
        addKittenLayout.add(saveKitten, 1, 3);
        //addKittenLayout.add(backKitten, 0, 3);
        addKittenLayout.add(feedbackKitten, 0, 4);

        handleKittensPane.setTop(addKittenLayout);

        //En tiedä, mitä tähän voisi laittaa parametriksi
        //handleKittensPane.setCenter(getAllDataForKittens(litter.getKittens()));
        Button backKitten = new Button("Takaisin");
        handleKittensPane.setBottom(backKitten);
        handleKittensPane.setMargin(backKitten, new Insets(20, 30, 10, 30));
        backKitten.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        saveKitten.setOnMouseClicked((event) -> {
            boolean isValidNameOfKittenField = nameOfKittenField.isValid();
            boolean isValidsexBox = sexBox.isValid();
            boolean isValidWeightOfKittenField = weightOfKittenField.isValid();

            String kittenName = nameOfKittenField.getValue();
            String sex = sexBox.getValue();
            String birthTime = birthTimeOfKittenField.getValue();
            String weigth = weightOfKittenField.getValue();
            String regNo = regNoOfKittenField.getValue();
            String emsCode = emsCodeOfKittenField.getValue();

            int kittenId = -1;

            if (isValidNameOfKittenField && isValidsexBox && isValidWeightOfKittenField) {
                if (kitten != null) {
                    kittenId = service.updateKitten(kitten.getId(), kittenName, sex, birthTime, Integer.valueOf(weigth), regNo, emsCode, birth);
                } else {
                    kittenId = service.addKitten(litterId, kittenName, sex, birthTime, Integer.valueOf(weigth), regNo, emsCode, birth);
                }
            }

            if (kittenId != -1 && kittenId != -2) {
                feedbackKitten.setText("Onnistui!");
                // ota weigth ja laita sen kantaan kittenId:llä service.add
                nameOfKittenField.clear();
                birthTimeOfKittenField.clear();
                weightOfKittenField.clear();
                sexBox.clear();
            } else {
                feedbackKitten.setText("Ei onnistunut!");
            }

        });

        stage.setScene(getInitialScene());

        stage.show();

        return addKittenScene;
    }

    //näkymä pentueen tiedoista
    private Node getLitterInfoLayout(Litter litter) {

        ArrayList<Kitten> kittens = litter.getKittens();
        litter.setKittens(kittens);
        litter.getMaleKittenCount();
        litter.getFemaleKittenCount();

        GridPane litterInfoLayout = new GridPane();
        litterInfoLayout.setPrefSize(1024, 50);
        litterInfoLayout.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(100));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(80));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(50));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(100));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(100));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(30));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(50));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(100));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(100));
        litterInfoLayout.getColumnConstraints().add(new ColumnConstraints(100));
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
        litterInfoLayout.add(d, 2, 1);
        Text damInfo = new Text(litter.getDam());
        litterInfoLayout.add(damInfo, 3, 1);
        Text s = new Text("Isä:");
        s.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(s, 2, 2);
        Text sireInfo = new Text(litter.getSire());
        litterInfoLayout.add(sireInfo, 3, 2);
        Text count = new Text("Lukumäärä:");
        count.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(count, 4, 1);
        Text countInfo = new Text(kittens != null ? String.valueOf(litter.getKittenCount()) : "");
        litterInfoLayout.add(countInfo, 5, 1);
        Text countMales = new Text("Uroksia:");
        countMales.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(countMales, 4, 2);
        Text countMalesInfo = new Text(kittens != null ? String.valueOf(litter.getMaleKittenCount()) : "");
        litterInfoLayout.add(countMalesInfo, 5, 2);
        Text countFemales = new Text("Naaraita:");
        countFemales.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(countFemales, 4, 3);
        Text countFemalesInfo = new Text(kittens != null ? String.valueOf(litter.getFemaleKittenCount()) : "");
        litterInfoLayout.add(countFemalesInfo, 5, 3);
        Text emsCodes = new Text("Värit:");
        emsCodes.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(emsCodes, 6, 1);
        Text emsInfo = new Text(kittens != null ? litter.getEmsKitten() : "");
        litterInfoLayout.add(emsInfo, 7, 1, 1, 6);
        //Tähän mahtuu päiväkirja tilalle
        /*Text dec = new Text("Menehtyneet:");
        dec.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        litterInfoLayout.add(dec, 8, 1);
        Text decInfo = new Text("");
        litterInfoLayout.add(decInfo, 9, 1);*/
        Button edit = new Button("Muokkaa");
        litterInfoLayout.add(edit, 0, 5);
        Button addKitten = new Button("Lisää pentu");
        litterInfoLayout.add(addKitten, 1, 5, 2, 1);
        Button back = new Button("takaisin");
        litterInfoLayout.add(back, 2, 5);
        int litterId = litter.getId();

        addKitten.setOnMouseClicked((event) -> {
            stage.setScene(getAddOrUpdateKittenScene(litter.getId(), litter.getBirth(), null));
        });

        edit.setOnMouseClicked((event) -> {
            stage.setScene(handleLittersAndKittens(litter));
        });

        litterInfoLayout.setPadding(new Insets(20, 30, 30, 30));

        litterInfoLayout.setVgap(2);
        litterInfoLayout.setHgap(15);

        litterInfoLayout.setAlignment(Pos.TOP_LEFT);

        BorderPane curve = new BorderPane();
        curve.setBottom(litterInfoLayout);
        curve.setLeft(back);

        return litterInfoLayout;
    }

    //Näkymä pentujen infosta
    private Node getKittenInfoLayout(Litter litter) {
        ArrayList<Kitten> kittens = litter.getKittens();
        GridPane kittenInfoLayout = new GridPane();
        kittenInfoLayout.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        kittenInfoLayout.getColumnConstraints().add(new ColumnConstraints(120));
        kittenInfoLayout.getColumnConstraints().add(new ColumnConstraints(80));
        Text info = new Text("Pennut:");
        info.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        kittenInfoLayout.add(info, 0, 0);

        for (int i = 0; i < kittens.size(); i++) {
            Text kittenName = new Text(kittens.get(i).getKittenName());
            kittenName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            kittenInfoLayout.add(kittenName, 0, 1 + i);
            Button weigh = new Button("punnitse");
            kittenInfoLayout.add(weigh, 1, 1 + i);
            final Kitten kitten = kittens.get(i);
            weigh.setOnMouseClicked((event) -> {
                stage.setScene(getWeightKittenScene(kitten));
            });

            ToggleButton show = new ToggleButton("piilota");
            kittenInfoLayout.add(show, 2, 1 + i);
            show.setOnMouseClicked((event) -> {
                if (show.isSelected()) {
                    show.setText("näytä");
                } else {
                    show.setText("piilota");
                }

            });
        }

        kittenInfoLayout.setPadding(new Insets(15, 30, 15, 30));
        kittenInfoLayout.setVgap(9);
        return kittenInfoLayout;
    }

    //näkymä pentujen punnitsemiseen 
    private Scene getWeightKittenScene(Kitten kitten) {
        Label kittenName = new Label(kitten.getKittenName());
        kittenName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        BorderPane handleWeightsPane = new BorderPane();
        GridPane addWeightLayout = new GridPane();
        Scene addWeightKittenScene = new Scene(handleWeightsPane);

        TextFormField weigthField = new TextFormField("Paino", true);
        DatePickerFormField dateField = new DatePickerFormField("Päivämäärä", true);

        Label feedback = new Label("");

        addWeightLayout.add(kittenName, 0, 0);
        addWeightLayout.add(weigthField.getNode(), 1, 1);
        addWeightLayout.add(dateField.getNode(), 0, 1);
        addWeightLayout.add(feedback, 0, 2);

        addWeightLayout.setAlignment(Pos.TOP_LEFT);
        addWeightLayout.setVgap(10);
        addWeightLayout.setHgap(10);
        addWeightLayout.setPadding(new Insets(20, 30, 10, 30));

        Button saveWeight = new Button("Tallenna");
        addWeightLayout.add(saveWeight, 2, 1);

        handleWeightsPane.setTop(addWeightLayout);

        handleWeightsPane.setCenter(getAllWeightsForKittenLayout(kitten.getId(), kitten.getWeightList(), handleWeightsPane));

        Button backKitten = new Button("Takaisin");
        handleWeightsPane.setBottom(backKitten);
        handleWeightsPane.setMargin(backKitten, new Insets(20, 30, 10, 30));

        //addWeightLayout.add(backKitten, 3, 1);
        backKitten.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        saveWeight.setOnMouseClicked((event) -> {
            boolean isWeightValid = weigthField.isValid();
            boolean isDateValid = dateField.isValid();
            if (isWeightValid && isDateValid) {
                int weight = Integer.valueOf(weigthField.getValue());
                LocalDate date = dateField.getValue();
                boolean weightAddSuccessfull = service.addWeight(kitten.getId(), weight, date);
                if (weightAddSuccessfull) {
                    handleWeightsPane.setCenter(getAllWeightsForKittenLayout(kitten.getId(), null, handleWeightsPane));
                    feedback.setText("Paino lisätty!");

                    dateField.clear();
                    weigthField.clear();

                } else {
                    feedback.setText("Ei onnistunut!");
                }
            }
        });

        return addWeightKittenScene;

    }

    private Scene handleLittersAndKittens(Litter litter) {

        Label litterName = new Label(litter.getLitterName());
        litterName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        BorderPane handleLittersAndKittensPane = new BorderPane();
        GridPane handleLittersLayout = new GridPane();
        Scene handleLittersAndKittensScene = new Scene(handleLittersAndKittensPane);

        TextFormField damField = new TextFormField("Emo", true);
        damField.setValue(litter.getDam());
        TextFormField sireField = new TextFormField("Isä", true);
        sireField.setValue(litter.getSire());
        DatePickerFormField establishmentField = new DatePickerFormField("Astutuspäivä", true);
        establishmentField.setValue(litter.getEstablishmentDate());
        DatePickerFormField birthField = new DatePickerFormField("Syntymäpäivä", true);
        birthField.setValue(litter.getBirth());
        Label feedbackLitter = new Label("");
        handleLittersLayout.add(damField.getNode(), 0, 0);
        handleLittersLayout.add(sireField.getNode(), 0, 1);
        handleLittersLayout.add(establishmentField.getNode(), 1, 0);
        handleLittersLayout.add(birthField.getNode(), 1, 1);
        handleLittersLayout.add(feedbackLitter, 2, 0);

        handleLittersLayout.setAlignment(Pos.TOP_LEFT);
        handleLittersLayout.setVgap(10);
        handleLittersLayout.setHgap(10);
        handleLittersLayout.setPadding(new Insets(20, 30, 10, 30));

        Button updateLitter = new Button("Tallenna");
        handleLittersLayout.add(updateLitter, 2, 1);

        updateLitter.setOnMouseClicked((event) -> {

            boolean isValidDamField = damField.isValid();
            boolean isValidSireField = sireField.isValid();
            boolean isValidEstablishmentField = establishmentField.isValid();
            boolean isValidBirthField = birthField.isValid();

            String dam = damField.getValue();
            String sire = sireField.getValue();
            LocalDate establishmentDate = establishmentField.getValue();
            LocalDate birth = birthField.getValue();

            boolean successful = false;

            if (isValidDamField && isValidSireField && isValidEstablishmentField && isValidBirthField) {

                successful = service.updateLitter(dam, sire, establishmentDate, birth, litter.getId());

            }

            if (successful) {
                feedbackLitter.setText("Onnistui!");
            } else {
                feedbackLitter.setText("Ei onnistunut!");
            }

        });

        Button back = new Button("Takaisin");

        handleLittersAndKittensPane.setTop(handleLittersLayout);
        handleLittersAndKittensPane.setBottom(back);
        handleLittersAndKittensPane.setCenter(getAllDataForKittens(litter));
        handleLittersAndKittensPane.setMargin(back, new Insets(20, 30, 10, 30));

        back.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        return handleLittersAndKittensScene;
    }

    //Listaa kaikki pentujen tiedot. Tarvitaan pentujen muokkaamiseen.
    private Node getAllDataForKittens(Litter litter) {
        FlowPane kittensPane = new FlowPane(Orientation.VERTICAL, 20.0, 20.0);
        kittensPane.setPadding(new Insets(20, 30, 10, 30));
        ArrayList<Kitten> kittens = litter.getKittens();
        for (int i = 0; i < kittens.size(); i++) {
            final Kitten kitten = kittens.get(i);
            GridPane allKittens = new GridPane();
            Text kittenName = new Text(kitten.getKittenName());
            Text kittenOfficialName = new Text(kitten.getBirthTime());
            Text kittenSex = new Text(kitten.getSex());
            Text kittenRegNo = new Text(kitten.getRegno());
            Text kittenEms = new Text(kitten.getEms());
            Button mode = new Button("Muokkaa");
            allKittens.add(kittenName, 0, 1 + i);
            allKittens.add(kittenOfficialName, 1, 1 + i);
            allKittens.add(kittenSex, 2, 1 + i);
            allKittens.add(kittenRegNo, 3, 1 + i);
            allKittens.add(kittenEms, 4, 1 + i);
            allKittens.add(mode, 5, 1 + i);

            allKittens.setHgap(10);
            allKittens.getColumnConstraints().add(new ColumnConstraints(80));
            allKittens.getColumnConstraints().add(new ColumnConstraints(250));
            allKittens.getColumnConstraints().add(new ColumnConstraints(40));
            allKittens.getColumnConstraints().add(new ColumnConstraints(100));
            allKittens.getColumnConstraints().add(new ColumnConstraints(100));

            mode.setOnMouseClicked((event) -> {
                //Keksi, miten voi muokata pennun tietoja.
                stage.setScene(getAddOrUpdateKittenScene(litter.getId(), litter.getBirth(), kitten));
            });

            kittensPane.getChildren().add(allKittens);
        }
        return kittensPane;
    }

    //Listaa pennun kaikki painot. Tarvitaan painojen muokkaamiseen.
    private Node getAllWeightsForKittenLayout(Integer kittenId, ArrayList<Weight> weightList, BorderPane handleWeightsPane) {
        if (weightList == null) {
            weightList = service.getKittenWeightsByKittenId(kittenId);
        }

        FlowPane weightsOfKitten = new FlowPane(Orientation.VERTICAL, 20.0, 20.0);
        weightsOfKitten.setPadding(new Insets(20, 30, 10, 30));

        weightList.sort(Comparator.comparing(Weight::getDate));

        for (Weight weight : weightList) {
            GridPane allWeights = new GridPane();
            allWeights.setHgap(10);
            allWeights.getColumnConstraints().add(new ColumnConstraints(80));
            allWeights.getColumnConstraints().add(new ColumnConstraints(40));
            Text date = new Text(weight.getDate().toString());
            Text weightField = new Text("" + weight.getWeight());
            Button mode = new Button("Poista");
            mode.setOnMouseClicked((event) -> {
                //Poista kannasta valittu punnitustapahtuma.
                service.removeWeight(weight.getId());
                handleWeightsPane.setCenter(getAllWeightsForKittenLayout(kittenId, null, handleWeightsPane));
            });

            allWeights.add(date, 0, 0);
            allWeights.add(weightField, 1, 0);
            allWeights.add(mode, 2, 0);
            weightsOfKitten.getChildren().add(allWeights);
        }
        return weightsOfKitten;
    }

    //laskee pennun iän päivinä. tarvitaan viivakaaviossa ja päiväkirjassa.
    private int getAge(LocalDate birthDate, LocalDate weighDate) {
        return (int) ChronoUnit.DAYS.between(birthDate, weighDate);
    }

    //viivakaavio. ei näytä vielä painoja.
    private Node getWeightCurveLayout(Litter litter) {
        Map<String, Map<Integer, Integer>> weightCurve = new HashMap();
        for (Kitten kitten : litter.getKittens()) {
            Map<Integer, Integer> weightMap = new HashMap();
            for (Weight weight : kitten.getWeightList()) {
                weightMap.put(getAge(litter.getBirth(), weight.getDate()), weight.getWeight());
            }

            weightCurve.put(kitten.getKittenName(), weightMap);
        }

        // luodaan kaaviossa käytettävät x- ja y-akselit
        NumberAxis yAkseli = new NumberAxis(0, 2500, 100);
        NumberAxis xAkseli = new NumberAxis(0, 98, 7);

        // luodaan viivakaavio. Viivakaavion arvot annetaan numeroina
        // ja se käyttää aiemmin luotuja x- ja y-akseleita
        LineChart<Number, Number> weightCurveChart = new LineChart<>(xAkseli, yAkseli);
        weightCurveChart.setTitle("Pentueen kasvukäyrä");
        weightCurveChart.setPadding(new Insets(20, 30, 30, 30));

        // käydään pennut läpi ja lisätään ne kaavioon
        weightCurve.keySet().stream().forEach(kittenName -> {
            // jokaiselle pennulle luodaan oma datajoukko
            XYChart.Series data = new XYChart.Series();
            data.setName(kittenName);
            // datajoukkoon lisätään pentujen datapisteet
            weightCurve.get(kittenName).entrySet().stream().forEach(pari -> {
                data.getData().add(new XYChart.Data(pari.getKey(), pari.getValue()));
            });

            // ja datajoukko lisätään kaavioon
            weightCurveChart.getData().add(data);
        });

        // näytetään viivakaavio
        return weightCurveChart;
    }

    private Scene getDiaryScene(Litter litter) {
        
        Label litterNameLabel = new Label(litter.getLitterName());
        litterNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        BorderPane diaryBorderPane = new BorderPane();
        GridPane diaryLayoutGridPane = new GridPane();
        Scene diaryScene = new Scene(diaryBorderPane);

        DatePickerFormField diaryDateField = new DatePickerFormField("Päivämäärä", true);

        //Label label = new Label("Sisältö");
        //Font font = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 10);
        //label.setFont(font);

        TextArea diaryText = new TextArea();

        diaryText.setText("Kirjoita tähän");
        diaryText.setPrefColumnCount(15);
        diaryText.setPrefHeight(120);
        diaryText.setPrefWidth(300);

        HBox diaryBox = new HBox();
        diaryBox.setSpacing(20);
        diaryBox.setPadding(new Insets(20, 50, 50, 60));
        diaryBox.getChildren().addAll( diaryText);

        Label feedbackDiary = new Label("");
        diaryLayoutGridPane.add(diaryDateField.getNode(), 0, 0);
        diaryLayoutGridPane.add(feedbackDiary, 2, 1);

        diaryLayoutGridPane.setAlignment(Pos.TOP_LEFT);
        diaryLayoutGridPane.setVgap(10);
        diaryLayoutGridPane.setHgap(10);
        diaryLayoutGridPane.setPadding(new Insets(20, 30, 10, 30));

        Button updateDiary = new Button("Tallenna");
        diaryLayoutGridPane.add(updateDiary, 2, 0);

        updateDiary.setOnMouseClicked((event) -> {

            boolean isValidDiaryDateField = diaryDateField.isValid();

            LocalDate diaryDate = diaryDateField.getValue();

            boolean successful = false;

            if (isValidDiaryDateField) {

                successful = service.updateDiary(litter.getId(), diaryDate, diaryText);

            }

            if (successful) {
                feedbackDiary.setText("Onnistui!");
            } else {
                feedbackDiary.setText("Ei onnistunut!");
            }

        });

        Button back = new Button("Takaisin");

        diaryBorderPane.setTop(diaryLayoutGridPane);
        diaryBorderPane.setCenter(diaryBox);
        diaryBorderPane.setBottom(back);
        //diaryPane.setCenter(getAllDataForKittens(litter));
        diaryBorderPane.setMargin(back, new Insets(20, 30, 10, 30));

        back.setOnMouseClicked((event) -> {
            stage.setScene(getInitialScene());
        });

        return diaryScene;

    }
}
