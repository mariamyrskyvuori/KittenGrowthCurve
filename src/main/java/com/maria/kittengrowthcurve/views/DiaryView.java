/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.views;

import com.maria.kittengrowthcurve.domain.Diary;
import com.maria.kittengrowthcurve.domain.Litter;
import com.maria.kittengrowthcurve.formfields.DatePickerFormField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author maria
 */
public class DiaryView {

    Litter litter;
    Diary activeDiary;
    BorderPane borderPane = new BorderPane();
    GridPane gridPane = new GridPane();
    HBox hBox = new HBox();
    Label litterNameLabel;
    DatePickerFormField diaryDateField = new DatePickerFormField("Päivämäärä", true);
    TextArea diaryText = new TextArea();
    Label saveResultMessage = new Label("");
    Button updateButton = new Button("Tallenna");
    Button backButton = new Button("Takaisin");
    

    public DiaryView(Litter litter, Diary activeDiary) {
        this.litter = litter;
        this.activeDiary = activeDiary;
        diaryText.setPrefColumnCount(3);
        diaryText.setPrefHeight(120);
        diaryText.setPrefWidth(450);
        diaryText.setWrapText(true);
        diaryText.setMaxWidth(450);
        litterNameLabel = new Label(litter.getLitterName());
        litterNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        hBox.setSpacing(60);
        hBox.setPadding(new Insets(0, 30, 10, 30));
        hBox.getChildren().addAll(diaryText);
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 30, 10, 30));
        borderPane.setMargin(backButton, new Insets(20, 30, 10, 30));
        setContent();
    }
    
    private void setContent() {
        gridPane.add(diaryDateField.getNode(), 0, 0);
        gridPane.add(saveResultMessage, 2, 1);
        gridPane.add(updateButton, 2, 0);
        borderPane.setTop(gridPane);
        borderPane.setCenter(hBox);
        borderPane.setBottom(backButton);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public HBox gethBox() {
        return hBox;
    }

    public Label getLitterNameLabel() {
        return litterNameLabel;
    }

    public DatePickerFormField getDiaryDateField() {
        return diaryDateField;
    }

    public TextArea getDiaryText() {
        return diaryText;
    }
    
    public void setSaveResultMessage(String saveResultMessage) {
        this.saveResultMessage.setText(saveResultMessage);
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getBackButton() {
        return backButton;
    }
}
