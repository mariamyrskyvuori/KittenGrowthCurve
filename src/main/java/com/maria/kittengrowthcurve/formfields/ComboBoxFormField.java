/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.formfields;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 *
 * @author maria
 */
public class ComboBoxFormField {

    GridPane grid = new GridPane();
    Label nameLabel;
    ComboBox<String> field;
    Label errorLabel;
    boolean isRequired;

    public ComboBoxFormField(String name, boolean isRequired, int width) {
        nameLabel = new Label(name);
        field = new ComboBox();
        errorLabel = new Label("");
        this.isRequired = isRequired;
        //grid.setGridLinesVisible(true);
        grid.setPrefSize(width, 30);
        grid.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        grid.setVgap(1);
        grid.setHgap(1);
        grid.setPadding(new Insets(1, 1, 1, 1));
        grid.add(nameLabel, 0, 0);
        grid.add(field, 0, 1);
        grid.add(errorLabel, 0, 3);
        field.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == false) {
                isValid();
            }
        });
    }

    public ComboBoxFormField(String name, boolean isRequired) {
        this(name, isRequired, 230);
    }

    public boolean isValid() {
        System.out.println("field.getValue() " + field.getValue());
        if (isRequired && field.getValue() == null) {
            errorLabel.setText("Täytä puuttuva tieto");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    public void setItems(String... values) {
        field.getItems().addAll(values);
    }

    public Node getNode() {
        return grid;
    }

    public String getValue() {
        return field.getValue();
    }
    
    public void setValue(String value) {
        field.setValue(value);
    }

    public void setPromptText(String promptText) {
        field.setPromptText(promptText);
    }

    public void clear() {
        field.setValue(null);
        errorLabel.setText("");
    }
}
