/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

/**
 *
 * @author maria
 */
public class WeightCurve {
    
    public void start(Stage ikkuna) {
        
        Map<String, Map<Integer, Double>> arvot = new HashMap();
        // luodaan lukija tiedoston lukemista varten
        try ( Scanner tiedostonLukija = new Scanner(new File("puoluedata.tsv"))) {

            // luetaan tiedostoja kunnes kaikki rivit on luettu
            List<String> ekanRivinPalat = null;
            while (tiedostonLukija.hasNextLine()) {
                // luetaan yksi rivi
                String rivi = tiedostonLukija.nextLine();
                List<String> palat = Arrays.asList(rivi.split("\t"));
                if (ekanRivinPalat == null) {
                    ekanRivinPalat = palat;
                } else {
                    Map<Integer, Double> vuodet = new HashMap();
                    for (int i = 1; i < palat.size(); i++) {
                        System.out.println("ekanRivinPalat.get(i): " + ekanRivinPalat.get(i));
                        System.out.println("palat.get(i): " + palat.get(i));
                        vuodet.put(Integer.parseInt(ekanRivinPalat.get(i)), Double.valueOf(palat.get(i)));
                    }
                    System.out.println("palat.get(0): " + palat.get(0));
                    arvot.put(palat.get(0), vuodet);
                }
            }
        } catch (Exception e) {
            System.out.println("Virhe: " + e.getMessage());
        }

        // luodaan kaaviossa käytettävät x- ja y-akselit
        NumberAxis yAkseli = new NumberAxis(0, 2500, 6);
        NumberAxis xAkseli = new NumberAxis(1, 14, 7);

        // luodaan viivakaavio. Viivakaavion arvot annetaan numeroina
        // ja se käyttää aiemmin luotuja x- ja y-akseleita
        LineChart<Number, Number> viivakaavio = new LineChart<>(xAkseli, yAkseli);
        viivakaavio.setTitle("Pentueen kasvukäyrä");

        // käydään puolueet läpi ja lisätään ne kaavioon
        arvot.keySet().stream().forEach(puolue -> {
            // jokaiselle puolueelle luodaan oma datajoukko
            XYChart.Series data = new XYChart.Series();
            data.setName(puolue);

            // datajoukkoon lisätään puolueen pisteet
            arvot.get(puolue).entrySet().stream().forEach(pari -> {
                data.getData().add(new XYChart.Data(pari.getKey(), pari.getValue()));
            });

            // ja datajoukko lisätään kaavioon
            viivakaavio.getData().add(data);
        });

        // näytetään viivakaavio
        Scene nakyma = new Scene(viivakaavio, 640, 480);
        ikkuna.setScene(nakyma);
        ikkuna.show();
    }
/*
    public static void main(String[] args) {
        launch(PuolueetSovellus.class);
    }
*/
}

    

