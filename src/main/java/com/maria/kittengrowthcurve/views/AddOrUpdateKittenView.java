/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.views;

import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
import com.maria.kittengrowthcurve.formfields.ComboBoxFormField;
import com.maria.kittengrowthcurve.formfields.TextFormField;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author maria
 */
public class AddOrUpdateKittenView {

    Litter litter;
    Kitten kitten;
    int litterId;

    BorderPane borderPane = new BorderPane();
    GridPane gridPane = new GridPane();
    Button saveButton = new Button("Lisää pentu");
    Label saveResultMessage = new Label();
    Button backButton = new Button("Takaisin");
    TextFormField nameOfKittenField = new TextFormField("Nimi", true);
    ComboBoxFormField sexComboBoxField = new ComboBoxFormField("Sukupuoli", true);
    TextFormField officialNameOfKittenField = new TextFormField("Virallinen nimi", false);
    TextFormField weightOfKittenField = new TextFormField("Syntymäpaino", true);
    TextFormField regNoOfKittenField = new TextFormField("Rekisterinumero", false);
    TextFormField emsCodeOfKittenField = new TextFormField("EMS-koodi", false);

    public AddOrUpdateKittenView(int litterId, Kitten kitten) {
        this.litterId = litterId;
        this.kitten = kitten;
        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 30, 10, 30));
        borderPane.setMargin(backButton, new Insets(20, 30, 10, 30));
        sexComboBoxField.setItems("Uros", "Naaras");
        sexComboBoxField.setPromptText("Valitse sukupuoli");
        setContentToGridPane();
        setContentToBorderPane();
    }

    private void setContentToGridPane() {
        if (kitten != null) {
            nameOfKittenField.setValue(kitten.getKittenName());
            sexComboBoxField.setValue(kitten.getSex());
            officialNameOfKittenField.setValue(kitten.getOfficialName());
            weightOfKittenField.setValue(String.valueOf(kitten.getWeightList().get(0).getWeight()));
            regNoOfKittenField.setValue(kitten.getRegno());
            emsCodeOfKittenField.setValue(kitten.getEms());
        }
        gridPane.add(nameOfKittenField.getNode(), 0, 0);
        gridPane.add(sexComboBoxField.getNode(), 1, 0);
        gridPane.add(officialNameOfKittenField.getNode(), 0, 1);
        gridPane.add(weightOfKittenField.getNode(), 1, 1);
        gridPane.add(regNoOfKittenField.getNode(), 0, 2);
        gridPane.add(emsCodeOfKittenField.getNode(), 1, 2);
        addButtonToGridPane(saveButton, 1, 3);
        gridPane.add(saveResultMessage, 0, 4);
    }

    private void addButtonToGridPane(Button button, int col, int row) {
        gridPane.add(button, col, row);
    }

    private void setContentToBorderPane() {
        borderPane.setTop(gridPane);
        borderPane.setBottom(backButton);
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public boolean isValid() {
        boolean isValidNameOfKittenField = nameOfKittenField.isValid();
        boolean isValidsexBox = sexComboBoxField.isValid();
        boolean isValidWeightOfKittenField = weightOfKittenField.isValid();

        return isValidNameOfKittenField && isValidsexBox && isValidWeightOfKittenField;
    }

    public void clearFields() {
        nameOfKittenField.clear();
        officialNameOfKittenField.clear();
        weightOfKittenField.clear();
        sexComboBoxField.clear();
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Label getSaveResultMessage() {
        return saveResultMessage;
    }

    public Button getBackButton() {
        return backButton;
    }

    public TextFormField getNameOfKittenField() {
        return nameOfKittenField;
    }

    public ComboBoxFormField getSexComboBoxField() {
        return sexComboBoxField;
    }

    public TextFormField getOfficialNameOfKittenField() {
        return officialNameOfKittenField;
    }

    public TextFormField getWeightOfKittenField() {
        return weightOfKittenField;
    }

    public TextFormField getRegNoOfKittenField() {
        return regNoOfKittenField;
    }

    public TextFormField getEmsCodeOfKittenField() {
        return emsCodeOfKittenField;
    }

    public void setSaveResultMessage(String saveResultMessage) {
        this.saveResultMessage.setText(saveResultMessage);
    }

}
