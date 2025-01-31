/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.domain;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author maria
 */
public class Litter {

    private String litterName;
    private LocalDate establishmentDate;
    private LocalDate birth;
    private LocalDate deliveryDate;
    private String dam;
    private String sire;
    private int id;
    private ArrayList<Kitten> kittens;
    ArrayList<Diary> diaryList = new ArrayList<>();

    public Litter(String dam, String sire, String litterName, LocalDate establishmentDate) {
        this.dam = dam;
        this.sire = sire;
        this.litterName = litterName;
        this.establishmentDate = establishmentDate;
        this.kittens = new ArrayList<>();
    }

    public Litter(String dam, String sire, String litterName, LocalDate establishmentDate, LocalDate birth, LocalDate deliveryDate, int id) {
        this.id = id;
        this.dam = dam;
        this.sire = sire;
        this.litterName = litterName;
        this.establishmentDate = establishmentDate;
        this.birth = birth;
        this.deliveryDate = deliveryDate;
        this.kittens = new ArrayList<>();
    }

    public ArrayList<Kitten> getKittens() {
        return kittens;
    }

    public ArrayList<Diary> getDiaryList() {
        return diaryList;
    }

    public void addKitten(Kitten kitten) {
        this.kittens.add(kitten);
    }

    public void setKittens(ArrayList<Kitten> kittens) {
        this.kittens = kittens;
    }

    public void setDiaryList(ArrayList<Diary> diaryList) {
        this.diaryList = diaryList;
    }

    // laskee mahdollisen synnytyspäivän ja sen mukaan luovutusajankohdan
    public void calculateDates() {
        birth = establishmentDate.plusDays(66L);
        deliveryDate = birth.plusDays(98L);
    }

    // nimeää pentueen
    public String getLitterName() {
        return litterName;
    }

    // astutuspäivä
    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    //synnytyspäivä
    public LocalDate getBirth() {
        return birth;
    }

    //luovutuspäivä
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    //emo
    public String getDam() {
        return dam;
    }

    //isä
    public String getSire() {
        return sire;
    }

    //pentueen id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return litterName;
    }

    //pentujen lukumäärä
    public int getKittenCount() {
        return kittens.size();
    }

    //urospentujen lukumäärä
    public int getMaleKittenCount() {
        int i = 0;
        int maleCount = 0;
        while (i < kittens.size()) {
            if (kittens.get(i).getSex().equals("Uros")) {
                maleCount++;
            }
            i++;
        }
        return maleCount;
    }

    //naaraspentujen lukumäärä
    public int getFemaleKittenCount() {
        int i = 0;
        int femaleCount = 0;
        while (i < kittens.size()) {
            if (kittens.get(i).getSex().equals("Naaras")) {
                femaleCount++;
            }
            i++;
        }
        return femaleCount;
    }

    //pentujen värikoodit
    public String getEmsKitten() {
        int i = 0;
        String emsKitten = "";
        while (i < kittens.size()) {
            if (!kittens.get(i).getEms().isEmpty()) {
                emsKitten = emsKitten + kittens.get(i).getEms() + "\n";
            }
            i++;
        }
        return emsKitten;
    }
}
