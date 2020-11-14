/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author maria
 */
public class Kitten {
    
    private String kittenName;
    private String sex;
    private String birthTime; //muuta kellonajaksi
    private int weight; //pitäisikö olla lista tai hashmap?
    private String regno;
    private String ems;
    private int litterId;
    HashMap<String, String> weighMap = new HashMap<String, String>();
    
    Kitten(String kittenName, String sex, String birthTime, int weight) {
        this.kittenName = kittenName;
        this.sex = sex;
        this.birthTime = birthTime;
        this.weight = weight;
    }
    
    Kitten(String kittenName, String sex, String birthTime, int weight, String regno, String ems, int litterId){
        this.litterId = litterId;
        this.kittenName = kittenName;
        this.sex = sex;
        this.birthTime = birthTime;
        this.weight = weight;
        this.regno = regno;
        this.ems = ems;
    }

    public String getKittenName() {
        return kittenName;
    }

    public String getSex() {
        return sex;
    }

    public String getBirthTime() {
        return birthTime;
    }

    public int getWeight() {
        return weight;
    }

    public String getRegno() {
        return regno;
    }

    public String getEms() {
        return ems;
    }

    int getLitterId() {
        return litterId;
    }
    
    public HashMap weighKitten(String weighDate, int weigh){
        return weighMap;
    }
    
    
}
