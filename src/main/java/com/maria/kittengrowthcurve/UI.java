/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.*;
import static com.maria.kittengrowthcurve.util.UIUtils.*;
import com.maria.kittengrowthcurve.views.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
    Set<Integer> hiddenKittensIds = new HashSet<Integer>();
    BorderPane initialLayout = new BorderPane();

    UI(Stage stage) {
        this.stage = stage;
    }

    public void start() {
        stage.setScene(new Scene(getInitialLayout()));
        stage.show();
    }

    //alkunäkymä
    private BorderPane getInitialLayout() {
        HBox topButtonsHbox = getInitialHBox();
        
        Button addLitter = new Button("Lisää pentue");
        topButtonsHbox.getChildren().addAll(addLitter);
        
        addLitter.setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getAddLitterLayout());
        });
        
        ArrayList<Litter> littersWithAllData = service.getAllDataFromDb();
        Button diaryButton = new Button("Päiväkirja");
        diaryButton.setAlignment(Pos.TOP_RIGHT);
        ComboBox<Litter> comboBox = new ComboBox<>();
        ObservableList<Litter> items = FXCollections.observableArrayList();
        
        int i = 0;
        while (i < littersWithAllData.size()) {
            items.add(littersWithAllData.get(i));
            i++;
        }
        
        comboBox.setItems(items);
        comboBox.setPromptText("Valitse pentue");
        comboBox.setOnAction((event) -> {
            Litter litter = comboBox.getValue();
            activeLitterId = litter.getId();
            if (activeLitterId != -1) {
                initialLayout.setBottom(getLitterInfoLayout(litter));
                initialLayout.setLeft(getKittenInfoLayout(litter));
                initialLayout.setCenter(getWeightCurveLayout(litter));
                diaryButton.setOnMouseClicked((diaryEvent) -> {
                    stage.getScene().setRoot(getDiaryPage(litter, null));
                });
            }
        });
        
        if (activeLitterId != -1) {
            for (Litter litter : littersWithAllData) {
                if (litter.getId() == activeLitterId) {
                    initialLayout.setBottom(getLitterInfoLayout(litter));
                    initialLayout.setLeft(getKittenInfoLayout(litter));
                    initialLayout.setCenter(getWeightCurveLayout(litter));
                    diaryButton.setOnMouseClicked((event) -> {
                        stage.getScene().setRoot(getDiaryPage(litter, null));
                    });
                }
            }
        }
        topButtonsHbox.getChildren().addAll(diaryButton);
        topButtonsHbox.getChildren().addAll(comboBox);
        initialLayout.setTop(topButtonsHbox);
        
        return initialLayout;
    }

    //näkymä pentueen lisäämistä varten
    private BorderPane getAddLitterLayout() {
        AddLitterView addLitterView = new AddLitterView();
        addLitterView.getSaveButton().setOnMouseClicked((event) -> {
            boolean litterAddSuccessfull = false;
            if (addLitterView.isValid()) {
                litterAddSuccessfull = service.addLitter(addLitterView.getDamField().getValue(),
                        addLitterView.getSireField().getValue(),
                        addLitterView.getNameField().getValue(),
                        addLitterView.getEstablishmentField().getValue());
            }
            if (litterAddSuccessfull) {
                addLitterView.setSaveResultMessage("Pentue lisätty!");
                addLitterView.clearFields();
            } else {
                addLitterView.setSaveResultMessage("Ei onnistunut!");
            }
        });
        
        addLitterView.getBackButton().setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getInitialLayout());
        });

        return addLitterView.getBorderPane();
    }

    //näkymä pentujen lisäämistä varten
    private BorderPane getAddOrUpdateKittenPage(int litterId, LocalDate birth, Kitten kitten) {
        AddOrUpdateKittenView addOrUpdateKittenView = new AddOrUpdateKittenView(litterId, kitten);
        
        addOrUpdateKittenView.getBackButton().setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getInitialLayout());
        });
        
        addOrUpdateKittenView.getSaveButton().setOnMouseClicked((event) -> {
            int kittenId = -1;
            
            if (addOrUpdateKittenView.isValid()) {
                if (kitten != null) {
                    kittenId = service.updateKitten(kitten.getId(),
                            addOrUpdateKittenView.getNameOfKittenField().getValue(),
                            addOrUpdateKittenView.getSexComboBoxField().getValue(),
                            addOrUpdateKittenView.getOfficialNameOfKittenField().getValue(),
                            Integer.valueOf(addOrUpdateKittenView.getWeightOfKittenField().getValue()),
                            addOrUpdateKittenView.getRegNoOfKittenField().getValue(),
                            addOrUpdateKittenView.getEmsCodeOfKittenField().getValue(),
                            birth);
                } else {
                    kittenId = service.addKitten(litterId, addOrUpdateKittenView.getNameOfKittenField().getValue(),
                            addOrUpdateKittenView.getSexComboBoxField().getValue(),
                            addOrUpdateKittenView.getOfficialNameOfKittenField().getValue(),
                            Integer.valueOf(addOrUpdateKittenView.getWeightOfKittenField().getValue()),
                            addOrUpdateKittenView.getRegNoOfKittenField().getValue(),
                            addOrUpdateKittenView.getEmsCodeOfKittenField().getValue(),
                            birth);
                }
            }
            
            if (kittenId != -1 && kittenId != -2) {
                addOrUpdateKittenView.setSaveResultMessage("Onnistui!");
                addOrUpdateKittenView.clearFields();
            } else {
                addOrUpdateKittenView.setSaveResultMessage("Ei onnistunut!");
            }
        });
        
        return addOrUpdateKittenView.getBorderPane();
    }

    //näkymä pentueen tiedoista
    private Node getLitterInfoLayout(Litter litter) {
        LitterInfoView litterInfoView = new LitterInfoView(litter);
        
        litterInfoView.getAddKittenButton().setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getAddOrUpdateKittenPage(litter.getId(), litter.getBirth(), null));
        });
        
        litterInfoView.getEditButton().setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getHandleLittersAndKittensPage(litter));
        });
        
        return litterInfoView.getGridPane();
    }

    //Näkymä pentujen infosta
    private Node getKittenInfoLayout(Litter litter) {
        ArrayList<Kitten> kittens = litter.getKittens();
        GridPane gridPane = new KittenInfoView().getGridPane();
        
        for (int i = 0; i < kittens.size(); i++) {
            Text kittenName = new Text(kittens.get(i).getKittenName());
            kittenName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            gridPane.add(kittenName, 0, 1 + i);
            Button weigh = new Button("punnitse");
            gridPane.add(weigh, 1, 1 + i);
            final Kitten kitten = kittens.get(i);
            weigh.setOnMouseClicked((event) -> {
                stage.getScene().setRoot(getWeightKittenPage(kitten));
            });
            boolean isSelected = hiddenKittensIds.contains(kitten.getId());
            ToggleButton showButton = new ToggleButton(isSelected ? "näytä" : "piilota");
            showButton.setSelected(isSelected);
            
            gridPane.add(showButton, 2, 1 + i);
            showButton.setOnMouseClicked((event) -> {
                if (showButton.isSelected()) {
                    hiddenKittensIds.add(kitten.getId());
                    showButton.setText("näytä");
                    initialLayout.setCenter(getWeightCurveLayout(litter));
                } else {
                    hiddenKittensIds.remove(kitten.getId());
                    showButton.setText("piilota");
                    initialLayout.setCenter(getWeightCurveLayout(litter));
                }
            });
        }
        
        return gridPane;
    }

    //näkymä pentujen punnitsemiseen 
    private BorderPane getWeightKittenPage(Kitten kitten) {
        WeightKittenView weightKittenView = new WeightKittenView(kitten);
        weightKittenView.getBorderPane().setCenter(getAllWeightsForKittenLayout(kitten.getId(),
                kitten.getWeightList(),
                weightKittenView.getBorderPane()));
        
        weightKittenView.getBackButton().setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getInitialLayout());
        });
        
        weightKittenView.getSaveWeightButton().setOnMouseClicked((event) -> {
            if (weightKittenView.isValid()) {
                int weight = Integer.valueOf(weightKittenView.getWeigthField().getValue());
                LocalDate date = weightKittenView.getDateField().getValue();
                boolean weightAddSuccessfull = service.addWeight(kitten.getId(), weight, date);
                if (weightAddSuccessfull) {
                    weightKittenView.getBorderPane().setCenter(getAllWeightsForKittenLayout(kitten.getId(), null,
                            weightKittenView.getBorderPane()));
                    weightKittenView.setSaveResultMessage("Paino lisätty!");
                    weightKittenView.clearFields();
                } else {
                    weightKittenView.setSaveResultMessage("Ei onnistunut!");
                }
            }
        });
        
        return weightKittenView.getBorderPane();
    }

    private BorderPane getHandleLittersAndKittensPage(Litter litter) {
        HandleLittersAndKittensView handleLittersAndKittensView = new HandleLittersAndKittensView(litter);
        
        handleLittersAndKittensView.getUpdateLitterButton().setOnMouseClicked((event) -> {
            String dam = handleLittersAndKittensView.getDamField().getValue();
            String sire = handleLittersAndKittensView.getSireField().getValue();
            LocalDate establishmentDate = handleLittersAndKittensView.getEstablishmentField().getValue();
            LocalDate birth = handleLittersAndKittensView.getBirthField().getValue();
            String litterName = handleLittersAndKittensView.getLitterNameField().getValue();
            boolean successful = false;
            
            if (handleLittersAndKittensView.isValid()) {
                successful = service.updateLitter(dam, sire, establishmentDate, birth, litterName, litter.getId());
            }
            if (successful) {
                handleLittersAndKittensView.setSaveResultMessage("Onnistui!");
            } else {
                handleLittersAndKittensView.setSaveResultMessage("Ei onnistunut!");
            }
        });
        
        handleLittersAndKittensView.getBorderPane().setCenter(getAllDataForKittens(litter));
        
        handleLittersAndKittensView.getBackButton().setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getInitialLayout());
        });
        
        return handleLittersAndKittensView.getBorderPane();
    }

    //Listaa kaikki pentujen tiedot. Tarvitaan pentujen muokkaamiseen.
    private Node getAllDataForKittens(Litter litter) {
        FlowPane kittensPane = new FlowPane(Orientation.VERTICAL, 20.0, 20.0);
        kittensPane.setPadding(new Insets(20, 30, 10, 30));
        ArrayList<Kitten> kittens = litter.getKittens();
        
        for (int i = 0; i < kittens.size(); i++) {
            final Kitten kitten = kittens.get(i);
            GridPane allKittens = new GridPane();
            Button mode = new Button("Muokkaa");
            allKittens.add(new Text(kitten.getKittenName()), 0, 1 + i);
            allKittens.add(new Text(kitten.getOfficialName()), 1, 1 + i);
            allKittens.add(new Text(kitten.getSex()), 2, 1 + i);
            allKittens.add(new Text(kitten.getRegno()), 3, 1 + i);
            allKittens.add(new Text(kitten.getEms()), 4, 1 + i);
            allKittens.add(mode, 5, 1 + i);
            allKittens.setHgap(10);
            allKittens = setColWidthsToGridPane(new int[]{80, 250, 40, 100, 100}, allKittens);
            mode.setOnMouseClicked((event) -> {
                stage.getScene().setRoot(getAddOrUpdateKittenPage(litter.getId(), litter.getBirth(), kitten));
            });
            kittensPane.getChildren().add(allKittens);
        }
        
        return kittensPane;
    }

    //Listaa pennun kaikki painot. Tarvitaan painojen muokkaamiseen.
    public Node getAllWeightsForKittenLayout(Integer kittenId, ArrayList<Weight> weightList, BorderPane handleWeightsPane) {
        
        if (weightList == null) {
            weightList = service.getKittenWeightsByKittenId(kittenId);
        }
        
        FlowPane weightsOfKitten = new FlowPane(Orientation.VERTICAL, 20.0, 20.0);
        weightsOfKitten.setPadding(new Insets(20, 30, 10, 30));
        weightList.sort(Comparator.comparing(Weight::getDate));
        
        for (Weight weight : weightList) {
            GridPane allWeights = new GridPane();
            allWeights.setHgap(10);
            allWeights = setColWidthsToGridPane(new int[]{80, 40}, allWeights);
            Button mode = new Button("Poista");
            mode.setOnMouseClicked((event) -> {
                service.removeWeight(weight.getId());
                handleWeightsPane.setCenter(getAllWeightsForKittenLayout(kittenId, null, handleWeightsPane));
            });
            allWeights.add(new Text(weight.getDate().toString()), 0, 0);
            allWeights.add(new Text("" + weight.getWeight()), 1, 0);
            allWeights.add(mode, 2, 0);
            weightsOfKitten.getChildren().add(allWeights);
        }
        
        return weightsOfKitten;
    }

    private BorderPane getDiaryPage(Litter litter, Diary activeDiary) {
        DiaryView diaryView = new DiaryView(litter, activeDiary);
        
        if (activeDiary != null) {
            diaryView.getDiaryDateField().setValue(activeDiary.getDate());
            diaryView.getDiaryText().setText(activeDiary.getText());
        } else {
            diaryView.getDiaryText().setText("Kirjoita tähän");
        }
        
        diaryView.getUpdateButton().setOnMouseClicked((event) -> {
            boolean isValidDiaryDateField = diaryView.getDiaryDateField().isValid();
            LocalDate diaryDate = diaryView.getDiaryDateField().getValue();
            boolean successful = false;
            
            if (isValidDiaryDateField) {
                if (activeDiary != null) {
                    successful = service.updateDiary(diaryDate, diaryView.getDiaryText().getText(), activeDiary.getId());
                } else {
                    successful = service.insertDiary(litter.getId(), diaryDate, diaryView.getDiaryText().getText());
                }
                stage.getScene().setRoot(getDiaryPage(litter, null));
            }
            if (successful) {
                diaryView.setSaveResultMessage("Onnistui!");
            } else {
                diaryView.setSaveResultMessage("Ei onnistunut!");
            }
        });
        
        diaryView.gethBox().getChildren().addAll(getWholeDiaryLayout(litter,
                service.getWholeDiaryByLitterId(litter.getId()),
                diaryView.getBorderPane()));
        
        diaryView.getBackButton().setOnMouseClicked((event) -> {
            stage.getScene().setRoot(getInitialLayout());
        });
        
        return diaryView.getBorderPane();
    }

    //Listaa kaikki päiväkirjamerkinnät
    private Node getWholeDiaryLayout(Litter litter, ArrayList<Diary> diaryList, BorderPane diaryBorderPane) {
        
        if (diaryList == null) {
            int litterId = litter.getId();
            diaryList = service.getWholeDiaryByLitterId(litterId);
        }
    
        FlowPane diaryOfLitter = getDiaryFlowPane();
        final ScrollPane scrollDiary = getDiaryScrollPane();
        scrollDiary.setContent(diaryOfLitter);
        
        scrollDiary.viewportBoundsProperty().addListener((ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) -> {
            diaryOfLitter.setPrefWidth(450);
            diaryOfLitter.setPrefHeight(bounds.getHeight());
        });

        diaryList.sort(Comparator.comparing(Diary::getDate));
        
        for (Diary diary : diaryList) {
            GridPane allDiaries = getDiaryGridPane();            
            Text date = new Text(diary.getDate().toString());
            Text ageField = new Text("Pentujen ikä: " + getAge(litter.getBirth(), diary.getDate()) + " päivää.");
            Text textField = new Text("" + diary.getText());
            textField.setWrappingWidth(410);
            Button mode = new Button("Muokkaa");
            mode.setOnMouseClicked((event) -> {
                stage.getScene().setRoot(getDiaryPage(litter, diary));
            });
            allDiaries.add(date, 0, 0);
            allDiaries.add(ageField, 1, 0);
            allDiaries.add(mode, 2, 0);
            allDiaries.add(textField, 0, 1, 3, 1);
            diaryOfLitter.getChildren().add(allDiaries);
        }
        
        return scrollDiary;
    }


    private int getAge(LocalDate birthDate, LocalDate weighDate) {
        return (int) ChronoUnit.DAYS.between(birthDate, weighDate);
    }

    private Node getWeightCurveLayout(Litter litter) {
        Map<String, Map<Integer, Integer>> weightCurve = new HashMap();
        for (Kitten kitten : litter.getKittens()) {
            Map<Integer, Integer> weightMap = new HashMap();
            
            for (Weight weight : kitten.getWeightList()) {
                weightMap.put(getAge(litter.getBirth(), weight.getDate()), weight.getWeight());
            }

            if (!hiddenKittensIds.contains(kitten.getId())) {
                weightCurve.put(kitten.getKittenName(), weightMap);
            }
        }

        NumberAxis yAkseli = new NumberAxis(0, 2500, 100);
        NumberAxis xAkseli = new NumberAxis(0, 98, 7);

        LineChart<Number, Number> weightCurveChart = new LineChart<>(xAkseli, yAkseli);
        weightCurveChart.setTitle("Pentueen kasvukäyrä");
        weightCurveChart.setPadding(new Insets(20, 30, 30, 30));

        weightCurve.keySet().stream().forEach(kittenName -> {
            XYChart.Series data = new XYChart.Series();
            data.setName(kittenName);
            weightCurve.get(kittenName).entrySet().stream().forEach(pari -> {
                data.getData().add(new XYChart.Data(pari.getKey(), pari.getValue()));
            });
            weightCurveChart.getData().add(data);
        });
        
        return weightCurveChart;
    }
}
