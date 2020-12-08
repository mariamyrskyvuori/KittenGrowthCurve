/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.Kitten;
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
        kitten = new Kitten("Aino", "Naaras", "12:20", "x", "x", 5);
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
        assertEquals("12:20", kitten.getBirthTime());
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
    
}
