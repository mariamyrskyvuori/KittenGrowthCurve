/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.views;

import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.formfields.DatePickerFormField;
import com.maria.kittengrowthcurve.formfields.TextFormField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author maria
 */
public class WeightKittenView {

    BorderPane borderPane = new BorderPane();
    GridPane gridPane = new GridPane();
    Kitten kitten;
    Button saveWeightButton = new Button("Tallenna");
    Button backButton = new Button("Takaisin");
    TextFormField weigthField = new TextFormField("Paino", true);
    DatePickerFormField dateField = new DatePickerFormField("Päivämäärä", true);
    Label saveResultMessage = new Label("");

    public WeightKittenView(Kitten kitten) {
        this.kitten = kitten;
        Label kittenName = new Label(kitten.getKittenName());
        kittenName.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 30, 10, 30));
        setContentToGridPane();
        setContentToBorderPane();
    }

    private void setContentToGridPane() {
        gridPane.add(new Label(kitten.getKittenName()), 0, 0);
        gridPane.add(weigthField.getNode(), 1, 1);
        gridPane.add(dateField.getNode(), 0, 1);
        gridPane.add(saveResultMessage, 0, 2);
        gridPane.add(saveWeightButton, 2, 1);
    }

    private void setContentToBorderPane() {
        borderPane.setTop(gridPane);
        borderPane.setBottom(backButton);
        borderPane.setMargin(backButton, new Insets(20, 30, 10, 30));
    }
    
    public TextFormField getWeigthField() {
        return weigthField;
    }
    
    public DatePickerFormField getDateField() {
        return dateField;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void clearFields() {
        dateField.clear();
        weigthField.clear();
    }

    public Button getSaveWeightButton() {
        return saveWeightButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    public boolean isValid() {
        boolean isWeightValid = weigthField.isValid();
        boolean isDateValid = dateField.isValid();

        return isWeightValid && isDateValid;
    }
    
    public void setSaveResultMessage(String saveResultMessage) {
        this.saveResultMessage.setText(saveResultMessage);
    }
}
