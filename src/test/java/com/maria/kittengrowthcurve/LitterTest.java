/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
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
public class LitterTest {
    
    Litter litter;
    
    public LitterTest() {
    }
    
    
    @BeforeEach
    public void setUp() {
        litter = new Litter("Maria", "Antti", "pentue", LocalDate.of(2020, 8, 25));
        litter.calculateDates();
        litter.setId(1);
        
        int i = 0;
        while(i<5){
            if (i % 2 == 0) {
                litter.addKitten(new Kitten("pentu" + i, "Uros", "19:22", "y", "x", i, i));
            } else {
                litter.addKitten(new Kitten("pentu" + i, "Naaras", "19:23", "y", "x", i, i));
            }
            i++;
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getKittens method, of class Litter.
     */
    @Test
    public void testGetKittens() {
        assertEquals(5, litter.getKittens().size());
    }

    /**
     * Test of setKittens method, of class Litter.
     */
    @Test
    public void testSetKittens() {
        Litter localLitter = new Litter("Maria", "Antti", "pentue", LocalDate.of(2020, 8, 25));
        localLitter.addKitten(new Kitten("pentu", "Uros", "19:22", "x", "x", 1, 1));
        assertEquals(1, localLitter.getKittens().size());
    }

    /**
     * Test of calculateDates method, of class Litter.
     */
    @Test
    public void testCalculateDates() {
        assertEquals(LocalDate.of(2020, 10, 30), litter.getBirth());
        assertEquals(LocalDate.of(2021, 2, 5), litter.getDeliveryDate());
    }

    /**
     * Test of getLitterName method, of class Litter.
     */
    @Test
    public void testGetLitterName() {
        assertEquals("pentue", litter.getLitterName());
    }

    /**
     * Test of getEstablishmentDate method, of class Litter.
     */
    @Test
    public void testGetEstablishmentDate() {
        assertEquals(LocalDate.of(2020, 8, 25), litter.getEstablishmentDate());
    }

    /**
     * Test of getBirth method, of class Litter.
     */
    @Test
    public void testGetBirth() {
        assertEquals(LocalDate.of(2020, 10, 30), litter.getBirth());   
    }

    /**
     * Test of getDeliveryDate method, of class Litter.
     */
    @Test
    public void testGetDeliveryDate() {
        assertEquals(LocalDate.of(2021, 2, 5), litter.getDeliveryDate());
    }

    /**
     * Test of getDam method, of class Litter.
     */
    @Test
    public void testGetDam() {
        assertEquals("Maria", litter.getDam());
    }

    /**
     * Test of getSire method, of class Litter.
     */
    @Test
    public void testGetSire() {    
        assertEquals("Antti", litter.getSire());
    }

    /**
     * Test of getId method, of class Litter.
     */
    //@Test
    public void testGetId() {
        assertEquals(1, litter.getId());
    }

    /**
     * Test of setId method, of class Litter.
     */
    //@Test
    /*public void testSetId() {
        System.out.println("setId");
        int id = 0;
        Litter instance = null;
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of toString method, of class Litter.
     */
    //@Test
    /*public void testToString() {
        System.out.println("toString");
        Litter instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of getKittenCount method, of class Litter.
     */
    @Test
    public void testGetKittenCount() {    
        assertEquals(5, litter.getKittenCount());
    }

    /**
     * Test of getMaleKittenCount method, of class Litter.
     */
    @Test
    public void testGetMaleKittenCount() {
        assertEquals(3, litter.getMaleKittenCount());
    }

    /**
     * Test of getFemaleKittenCount method, of class Litter.
     */
    @Test
    public void testGetFemaleKittenCount() {    
        assertEquals(2, litter.getFemaleKittenCount());
    }

    /**
     * Test of getEmsKitten method, of class Litter.
     */
    //@Test
    public void testGetEmsKitten() {
        assertEquals("x", litter.getEmsKitten());
    }
}
