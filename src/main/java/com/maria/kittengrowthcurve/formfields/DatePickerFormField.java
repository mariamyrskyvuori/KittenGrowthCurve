/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.formfields;

import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 *
 * @author maria
 */
public class DatePickerFormField {

    GridPane grid = new GridPane();
    Label nameLabel;
    DatePicker field;
    Label errorLabel;
    boolean isRequired;

    public DatePickerFormField(String name, boolean isRequired, int width) {
        nameLabel = new Label(name);
        field = new DatePicker();
        this.isRequired = isRequired;
        errorLabel = new Label("");
        //grid.setGridLinesVisible(true);
        grid.setPrefSize(width, 30);
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        grid.setVgap(1);
        grid.setHgap(1);
        grid.setPadding(new Insets(1, 1, 1, 1));
        grid.add(nameLabel, 0, 0);
        grid.add(field, 0, 1);
        grid.add(errorLabel, 0, 3);
        field.valueProperty().addListener((observable, oldValue, newValue) -> {
            isValid();
        });
    }

    public DatePickerFormField(String name, boolean isRequired) {
        this(name, isRequired, 230);
    }

    public boolean isValid() {
        if (isRequired && field.getValue() == null) {
            errorLabel.setText("Valitse päivä");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    public Node getNode() {
        return grid;
    }

    public LocalDate getValue() {
        // Java fx DatePickerin bugi. Siksi date otetaan tekstistä.
        return field.getConverter().fromString(field.getEditor().getText());
    }
    
    public void setValue(LocalDate value) {
        field.setValue(value);
    }

    public void clear() {
        field.setValue(null);
        errorLabel.setText("");
    }
}
