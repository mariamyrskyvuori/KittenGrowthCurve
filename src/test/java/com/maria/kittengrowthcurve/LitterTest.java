/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

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
        litter = new Litter("Maria", "Antti", "pentue", "05.11.2020");
        litter.calculateDates();
        litter.setId(1);
        
        int i = 0;
        while(i<5){
            if (i % 2 == 0) {
                litter.addKitten(new Kitten("pentu" + i, "Uros", "19:22", 100 + i, "x", "x", i));
            } else {
            litter.addKitten(new Kitten("pentu" + i, "Naaras", "19:23", 100 + i, "x", "x", i));
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
    /*@Test
    public void testGetKittens() {
        
        assertEquals(expResult, result);
        
    }*/

    /**
     * Test of setKittens method, of class Litter.
     */
    //@Test
    /*public void testSetKittens() {
        System.out.println("setKittens");
        ArrayList<Kitten> kittens = null;
        Litter instance = null;
        instance.setKittens(kittens);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/

    /**
     * Test of calculateDates method, of class Litter.
     */
    //@Test
    public void testCalculateDates() {
        System.out.println("calculateDates");
        Litter instance = null;
        instance.calculateDates();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
        assertEquals("2020-11-05", litter.getEstablishmentDate().toString());
    }

    /**
     * Test of getBirth method, of class Litter.
     */
    @Test
    public void testGetBirth() {
        
        assertEquals("2021-01-10", litter.getBirth().toString());
        
    }

    /**
     * Test of getDeliveryDate method, of class Litter.
     */
    @Test
    public void testGetDeliveryDate() {
        
        assertEquals("2021-04-18", litter.getDeliveryDate().toString());
        
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
    //@Test
    public void testGetKittenCount() {
        
        assertEquals(5, litter.getKittenCount());
        
    }

    /**
     * Test of getMaleKittenCount method, of class Litter.
     */
    //@Test
    public void testGetMaleKittenCount() {
        
        assertEquals(2, litter.getMaleKittenCount());
        
    }

    /**
     * Test of getFemaleKittenCount method, of class Litter.
     */
    //@Test
    public void testGetFemaleKittenCount() {
        
        assertEquals(3, litter.getFemaleKittenCount());
        
    }

    /**
     * Test of getEmsKitten method, of class Litter.
     */
    //@Test
    public void testGetEmsKitten() {
        System.out.println("getEmsKitten");
        Litter instance = null;
        String expResult = "";
        String result = instance.getEmsKitten();
        assertEquals(expResult, litter.getEmsKitten());
        
    }
    
}
