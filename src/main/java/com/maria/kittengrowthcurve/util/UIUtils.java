/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.util;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author maria
 */
public class UIUtils {

    public static GridPane setColWidthsToGridPane(int[] widths, GridPane gridPane) {
        for (int width : widths) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
        }
        return gridPane;
    }

    public static ScrollPane getDiaryScrollPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scrollPane;
    }

    public static FlowPane getDiaryFlowPane() {
        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 10.0, 10.0);
        flowPane.setPadding(new Insets(5, 5, 5, 5));
        return flowPane;
    }

    public static GridPane getDiaryGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        gridPane.maxWidth(450);
        gridPane.maxHeight(500);
        gridPane = setColWidthsToGridPane(new int[]{80, 200, 80, 80}, gridPane);
        return gridPane;
    }
    
    public static HBox getInitialHBox() {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(20, 30, 10, 30));
        hBox.setSpacing(33);
        return hBox;
    }
}
