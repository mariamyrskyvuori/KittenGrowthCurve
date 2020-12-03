/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author maria
 */
public class Kitten {
    
    private String kittenName;
    private String sex;
    private String birthTime; //muuta kellonajaksi
    private String regno;
    private String ems;
    private int litterId;
    private int id;
    HashMap<LocalDate, Integer> weightMap = new HashMap<LocalDate, Integer>();
    
    Kitten(String kittenName, String sex, String birthTime) {
        this.kittenName = kittenName;
        this.sex = sex;
        this.birthTime = birthTime;
        
    }
    
    Kitten(String kittenName, String sex, String birthTime, String regno, String ems, int litterId){
        this.litterId = litterId;
        this.kittenName = kittenName;
        this.sex = sex;
        this.birthTime = birthTime;
        this.regno = regno;
        this.ems = ems;   
    }
    
    Kitten(String kittenName, String sex, String birthTime, String regno, String ems, int litterId, int kittenId){
        this.id = kittenId;
        this.litterId = litterId;
        this.kittenName = kittenName;
        this.sex = sex;
        this.birthTime = birthTime;
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

    public String getRegno() {
        return regno;
    }

    public String getEms() {
        return ems;
    }

    int getLitterId() {
        return litterId;
    }
 
    public HashMap getWeightMap(){
        return weightMap;
    }

    Integer getId() {
        return id;
    }

    public void setWeightMap(HashMap<LocalDate, Integer> weightMap) {
        this.weightMap = weightMap;
    }
}
