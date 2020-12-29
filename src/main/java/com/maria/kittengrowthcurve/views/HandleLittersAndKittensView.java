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
public class HandleLittersAndKittensView {

    Litter litter;
    BorderPane borderPane = new BorderPane();
    GridPane gridPane = new GridPane();
    Button updateLitterButton = new Button("Tallenna");
    Button removeLitterButton = new Button("Poista");
    TextFormField damField = new TextFormField("Emo", true);
    TextFormField sireField = new TextFormField("Uros", true);
    DatePickerFormField establishmentField = new DatePickerFormField("Astutuspäivä", true);
    DatePickerFormField birthField = new DatePickerFormField("Syntymäpäivä", true);
    TextFormField litterNameField = new TextFormField("Pentueen nimi", true);
    Label saveResultMessage = new Label("");
    Button backButton = new Button("Takaisin");

    public HandleLittersAndKittensView(Litter litter) {
        Label litterName = new Label(litter.getLitterName());
        litterName.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        damField.setValue(litter.getDam());
        sireField.setValue(litter.getSire());
        establishmentField.setValue(litter.getEstablishmentDate());
        birthField.setValue(litter.getBirth());
        litterNameField.setValue(litter.getLitterName());

        gridPane.setAlignment(Pos.TOP_LEFT);
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(20, 30, 10, 30));
        gridPane.add(updateLitterButton, 2, 1);
        gridPane.add(removeLitterButton, 3, 1);
        setContentToGridPane();
        setContentToBorderPane();
    }

    

    private void setContentToGridPane() {
        gridPane.add(damField.getNode(), 0, 0);
        gridPane.add(sireField.getNode(), 0, 1);
        gridPane.add(establishmentField.getNode(), 1, 0);
        gridPane.add(birthField.getNode(), 1, 1);
        gridPane.add(litterNameField.getNode(), 2, 0);
        gridPane.add(saveResultMessage, 3, 0);
    }

    public TextFormField getDamField() {
        return damField;
    }

    public TextFormField getSireField() {
        return sireField;
    }

    public DatePickerFormField getEstablishmentField() {
        return establishmentField;
    }

    public DatePickerFormField getBirthField() {
        return birthField;
    }
    
    public TextFormField getLitterNameField() {
        return litterNameField;
    }

    public Button getBackButton() {
        return backButton;
    }
    
    public Button getRemoveLitterButton() {
        return removeLitterButton;
    }
    
    public Button getUpdateLitterButton() {
        return updateLitterButton;
    }

    private void setContentToBorderPane() {
        borderPane.setTop(gridPane);
        borderPane.setBottom(backButton);
        borderPane.setMargin(backButton, new Insets(20, 30, 10, 30));
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public boolean isValid() {
        boolean isValidDamField = damField.isValid();
        boolean isValidSireField = sireField.isValid();
        boolean isValidEstablishmentField = establishmentField.isValid();
        boolean isValidBirthField = birthField.isValid();

        return isValidDamField && isValidSireField && isValidEstablishmentField && isValidBirthField;
    }
    
    public void setSaveResultMessage(String saveResultMessage) {
        this.saveResultMessage.setText(saveResultMessage);
    }
}
