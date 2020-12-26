/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.views;

import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
import java.sql.Array;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
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
public class LitterInfoView {
    
    Litter litter;
    GridPane gridPane = new GridPane();
    Button editButton = new Button("Tarkastele");
    Button addKittenButton = new Button("Lisää pentu");

    public LitterInfoView(Litter litter) {
        this.litter = litter;
        gridPane.setPrefSize(1024, 50);
        gridPane.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        setColWidths(new int[]{100, 80, 50, 100, 100, 30, 50, 100});
        gridPane.setPadding(new Insets(20, 30, 30, 30));
        gridPane.setVgap(2);
        gridPane.setHgap(15);
        gridPane.setAlignment(Pos.TOP_LEFT);
        setContent();
    }
    
    private void setContent() {
        ArrayList<Kitten> kittens = litter.getKittens();
        addTextToGridPane(litter.getLitterName(), 0, 0, 20, FontWeight.BOLD);
        addTextToGridPane("Syntymäaika:", 0, 1, 15, FontWeight.BOLD);
        addTextToGridPane(litter.getBirth().toString(), 1, 1, 1,1);
        addTextToGridPane("Luovutusaika:", 0, 2, 15, FontWeight.BOLD);
        addTextToGridPane(litter.getDeliveryDate().toString(), 1, 2, 1, 1);
        addTextToGridPane("Emo:", 2, 1, 15, FontWeight.BOLD);
        addTextToGridPane(litter.getDam(), 3, 1, 1, 1);
        addTextToGridPane("Uros:", 2, 2, 15, FontWeight.BOLD);
        addTextToGridPane(litter.getSire(), 3, 2, 1, 1);
        addTextToGridPane("Lukumäärä:", 4, 1, 15, FontWeight.BOLD);
        addTextToGridPane(kittens != null ? String.valueOf(litter.getKittenCount()) : "", 5, 1, 1, 1);
        addTextToGridPane("Uroksia:", 4, 2, 15, FontWeight.BOLD);
        addTextToGridPane(kittens != null ? String.valueOf(litter.getMaleKittenCount()) : "", 5, 2, 1, 1);
        addTextToGridPane("Naaraita:", 4, 3, 15, FontWeight.BOLD);
        addTextToGridPane(kittens != null ? String.valueOf(litter.getFemaleKittenCount()) : "", 5, 3, 1, 1);
        addTextToGridPane("Värit:", 6, 1, 15, FontWeight.BOLD);
        addTextToGridPane(kittens != null ? litter.getEmsKitten() : "", 7, 1, 1, 4);
               
        addButtonToGridPane(editButton, 0, 4, 1, 1);
        addButtonToGridPane(addKittenButton, 1, 4, 2, 1);
    }
    
    private void setColWidths(int[] widths) {
        for (int width : widths) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
        }
    }
    
    private void addButtonToGridPane(Button button, int col, int row, int colSpan, int rowSpan) {
        gridPane.add(button, col, row, colSpan, rowSpan);
    }
    
    private void addTextToGridPane(String text, int col, int row, int fontSize, FontWeight fontWeight) {
        Text textNode = new Text(text);
        textNode.setFont(Font.font("Arial", fontWeight, fontSize));
        gridPane.add(textNode, col, row);
    }
    
    private void addTextToGridPane(String text, int col, int row, int colSpan, int rowSpan) {
        Text textNode = new Text(text);
        textNode.setFont(Font.font("Arial"));
        GridPane.setValignment(textNode, VPos.TOP);
        gridPane.add(textNode, col, row, colSpan, rowSpan);
    }
    
    public GridPane getGridPane() {
        return gridPane;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getAddKittenButton() {
        return addKittenButton;
    }
    
}
