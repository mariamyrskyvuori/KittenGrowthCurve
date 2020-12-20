/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.domain;

import java.util.ArrayList;

/**
 *
 * @author maria
 */
public class Kitten {

    private String kittenName;
    private String sex;
    private String birthTime;
    private String regno;
    private String ems;
    private int litterId;
    private int id;
    ArrayList<Weight> weightList = new ArrayList<>();

    public Kitten(String kittenName, String sex, String birthTime) {
        this.kittenName = kittenName;
        this.sex = sex;
        this.birthTime = birthTime;

    }

    public Kitten(String kittenName, String sex, String birthTime, String regno, String ems, int litterId) {
        this.litterId = litterId;
        this.kittenName = kittenName;
        this.sex = sex;
        this.birthTime = birthTime;
        this.regno = regno;
        this.ems = ems;
    }

    public Kitten(String kittenName, String sex, String birthTime, String regno, String ems, int litterId, int kittenId) {
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

    public int getLitterId() {
        return litterId;
    }

    public ArrayList<Weight> getWeightList() {
        return weightList;
    }

    public Integer getId() {
        return id;
    }

    public void setWeightList(ArrayList<Weight> weightList) {
        this.weightList = weightList;
    }
}
