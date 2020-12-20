/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.views;

import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author maria
 */
public class KittenInfoView {
    GridPane gridPane = new GridPane();
    Text infoText = new Text("Pennut:");
    
    public KittenInfoView() {
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        gridPane.getColumnConstraints().add(new ColumnConstraints(120));
        gridPane.getColumnConstraints().add(new ColumnConstraints(80));
        infoText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        gridPane.setPadding(new Insets(15, 30, 15, 30));
        gridPane.setVgap(9);
        gridPane.add(infoText, 0, 0);
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
