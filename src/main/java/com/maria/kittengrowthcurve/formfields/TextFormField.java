/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.formfields;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 *
 * @author maria
 */
public class TextFormField {

    GridPane grid = new GridPane();
    Label nameLabel;
    TextField field;
    Label errorLabel;
    boolean isRequired;

    public TextFormField(String name, boolean isRequired, int width) {
        nameLabel = new Label(name);
        field = new TextField();
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
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            isValid();
        });
    }

    public TextFormField(String name, boolean isRequired) {
        this(name, isRequired, 230);
    }

    public boolean isValid() {
        if (isRequired && field.getText().equals("")) {
            errorLabel.setText("Täytä puuttuva tieto");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    public Node getNode() {
        return grid;
    }

    public String getValue() {
        return field.getText();
    }

    public void clear() {
        field.clear();
        errorLabel.setText("");
    }
}
