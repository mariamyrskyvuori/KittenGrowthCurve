/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.views;

import com.maria.kittengrowthcurve.domain.Litter;
import com.maria.kittengrowthcurve.formfields.DatePickerFormField;
import com.maria.kittengrowthcurve.formfields.TextFormField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 *
 * @author maria
 */
public class AddLitterView {

    Litter litter;
    GridPane gridPane = new GridPane();
    BorderPane borderPane = new BorderPane();
    Button saveButton = new Button("Lisää pentue");
    Button backButton = new Button("Takaisin");
    Label saveResultMessage = new Label("");
    TextFormField damField = new TextFormField("Emo", true);
    TextFormField sireField = new TextFormField("Isä", true);
    TextFormField nameField = new TextFormField("Nimi", true);
    DatePickerFormField establishmentField = new DatePickerFormField("Astutuspäivä", true);

    public AddLitterView() {
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 30, 10, 30));
        setContentToGridPane();
        setContentToBorderPane();
    }

    private void setContentToGridPane() {

        gridPane.add(damField.getNode(), 0, 0);
        gridPane.add(sireField.getNode(), 1, 0);
        gridPane.add(nameField.getNode(), 0, 1);
        gridPane.add(establishmentField.getNode(), 1, 1);
        gridPane.add(saveButton, 1, 4);
        gridPane.add(saveResultMessage, 1, 5);
    }

    private void setContentToBorderPane() {
        borderPane.setTop(gridPane);
        borderPane.setBottom(backButton);
        borderPane.setMargin(backButton, new Insets(20, 30, 10, 30));
    }

    public boolean isValid() {
        boolean isValidDamField = damField.isValid();
        boolean isValidSireField = sireField.isValid();
        boolean isValidNameField = nameField.isValid();
        boolean isValidEstablishmentField = establishmentField.isValid();

        return isValidDamField && isValidSireField && isValidNameField && isValidEstablishmentField;
    }

    public void clearFields() {
        damField.clear();
        sireField.clear();
        nameField.clear();
        establishmentField.clear();
    }

    public Button getSaveButton() {
        return saveButton;
    }
    
    public Button getBackButton() {
        return backButton;
    }

    public void setSaveResultMessage(String saveResultMessage) {
        this.saveResultMessage.setText(saveResultMessage);
    }

    public TextFormField getDamField() {
        return damField;
    }

    public TextFormField getSireField() {
        return sireField;
    }

    public TextFormField getNameField() {
        return nameField;
    }

    public DatePickerFormField getEstablishmentField() {
        return establishmentField;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }
}
