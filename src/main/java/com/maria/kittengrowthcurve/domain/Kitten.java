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
    private String officialName;
    private String regno;
    private String ems;
    private int litterId;
    private int id;
    ArrayList<Weight> weightList = new ArrayList<>();

    public Kitten(String kittenName, String sex, String officialName) {
        this.kittenName = kittenName;
        this.sex = sex;
        this.officialName = this.officialName;

    }

    public Kitten(String kittenName, String sex, String officialName, String regno, String ems, int litterId) {
        this.litterId = litterId;
        this.kittenName = kittenName;
        this.sex = sex;
        this.officialName = officialName;
        this.regno = regno;
        this.ems = ems;
    }

    public Kitten(String kittenName, String sex, String officialName, String regno, String ems, int litterId, int kittenId) {
        this.id = kittenId;
        this.litterId = litterId;
        this.kittenName = kittenName;
        this.sex = sex;
        this.officialName = officialName;
        this.regno = regno;
        this.ems = ems;
    }

    public String getKittenName() {
        return kittenName;
    }

    public String getSex() {
        return sex;
    }

    public String getOfficialName() {
        return officialName;
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
