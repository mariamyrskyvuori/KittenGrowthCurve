/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Weight;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author maria
 */
public class KittenTest {

    Kitten kitten;

    public KittenTest() {
    }

    @BeforeEach
    public void setUp() {
        kitten = new Kitten("Aino", "Naaras", "12:20", "x", "x", 5, 1);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getKittenName method, of class Kitten.
     */
    @Test
    public void testGetKittenName() {
        assertEquals("Aino", kitten.getKittenName());
    }

    /**
     * Test of getSex method, of class Kitten.
     */
    @Test
    public void testGetSex() {
        assertEquals("Naaras", kitten.getSex());
    }

    /**
     * Test of getBirthTime method, of class Kitten.
     */
    @Test
    public void testGetBirthTime() {
        assertEquals("12:20", kitten.getOfficialName());
    }

    /**
     * Test of getRegno method, of class Kitten.
     */
    @Test
    public void testGetRegno() {
        assertEquals("x", kitten.getRegno());
    }

    /**
     * Test of getEms method, of class Kitten.
     */
    @Test
    public void testGetEms() {
        assertEquals("x", kitten.getEms());
    }

    /**
     * Test of getLitterId method, of class Kitten.
     */
    @Test
    public void testGetLitterId() {
        assertEquals(5, kitten.getLitterId());
    }

    /**
     * Test of getId method, of class Kitten.
     */
    @Test
    public void testGetId() {
        assertEquals(1, kitten.getId().intValue());
    }

    @Test
    public void testSetWeightList() {
        kitten.setWeightList(getWeightList());
        int i = 0;
        while (i < 5) {
            assertEquals(getWeightList().get(i).getId(), kitten.getWeightList().get(i).getId());
            assertEquals(getWeightList().get(i).getDate(), kitten.getWeightList().get(i).getDate());
            assertEquals(getWeightList().get(i).getWeight(), kitten.getWeightList().get(i).getWeight());
            i++;
        }
    }

    @Test
    public void testGetWeightList() {
        kitten.setWeightList(getWeightList());
        int i = 0;
        while (i < 5) {
            assertEquals(getWeightList().get(i).getId(), kitten.getWeightList().get(i).getId());
            assertEquals(getWeightList().get(i).getDate(), kitten.getWeightList().get(i).getDate());
            assertEquals(getWeightList().get(i).getWeight(), kitten.getWeightList().get(i).getWeight());
            i++;
        }
    }

    ArrayList<Weight> getWeightList() {
        ArrayList<Weight> weightList = new ArrayList<>();
        int i = 0;
        while (i < 5) {
            weightList.add(new Weight(i + 1, LocalDate.of(2020, 8, 1 + i), 100 + i));
            i++;
        }
        return weightList;
    }
}
