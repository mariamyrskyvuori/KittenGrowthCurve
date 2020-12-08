/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.domain;

import java.time.LocalDate;

/**
 *
 * @author maria
 */
public class Weight {
    
    private int id;
    private LocalDate date;
    private int weight;

    public Weight(int id, LocalDate date, int weight) {
        this.id = id;
        this.date = date;
        this.weight = weight;
    }

    /**
     * Get the value of weight
     *
     * @return the value of weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Set the value of weight
     *
     * @param weight new value of weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }


    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the value of id
     *
     * @param id new value of id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Get the value of date
     *
     * @return the value of date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the value of date
     *
     * @param date new value of date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

}
