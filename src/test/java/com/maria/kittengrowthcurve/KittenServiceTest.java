/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.Diary;
import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
import com.maria.kittengrowthcurve.domain.Weight;
import com.maria.kittengrowthcurve.service.KittenService;
import java.io.File;
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
public class KittenServiceTest {

    KittenService kittenService;

    public KittenServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        kittenService = new KittenService("sql/kittenGrowthCurveTest.db");
    }

    @AfterEach
    public void tearDown() {
        File f = new File("sql/kittenGrowthCurveTest.db");
        f.delete();
    }

    /**
     * Test of addLitter method, of class KittenService.
     */
    @Test
    public void testAddLitter() {
        System.out.println("addLitter");
        String dam = "Äiti";
        String sire = "Isä";
        String litterName = "Testipentue";
        LocalDate establishmentDate = LocalDate.of(2020, 8, 25);
        boolean addSuccessful = kittenService.addLitter(dam, sire, litterName, establishmentDate);
        ArrayList<Litter> littersFromDb = kittenService.getLitters();
        assertNotEquals(littersFromDb, null);
        assertTrue(addSuccessful);
        assertEquals(littersFromDb.get(0).getLitterName(), "Testipentue");
        assertEquals(littersFromDb.get(0).getDam(), "Äiti");
        assertEquals(littersFromDb.get(0).getSire(), "Isä");
        assertEquals(LocalDate.of(2020, 8, 25).toString(), "2020-08-25");
    }

    /**
     * Test of getAllDataFromDb method, of class KittenService.
     */
    @Test
    public void testGetAllDataFromDb() {
        System.out.println("getAllDataFromDb");
        ArrayList<Litter> expResult = null;
        kittenService.addLitter("Äiti", "Isä", "Testipentue", LocalDate.of(2020, 8, 25));
        kittenService.addKitten(1, "Ensimmäinen", "Uros", "Ensimmäinen pentu", "X123", "musta", LocalDate.of(2020, 10, 30));
        
        kittenService.addWeight(1, 120, LocalDate.of(2020, 10, 30));
        
        ArrayList<Litter> littersFromDb = kittenService.getAllDataFromDb();
        assertEquals(littersFromDb.get(0).getDam(), "Äiti");
        assertEquals(littersFromDb.get(0).getSire(), "Isä");
        assertEquals(littersFromDb.get(0).getLitterName(), "Testipentue");
        assertEquals(littersFromDb.get(0).getEstablishmentDate().toString(), "2020-08-25");
        assertEquals(littersFromDb.get(0).getKittens().get(0).getLitterId(), 1);
        assertEquals(littersFromDb.get(0).getKittens().get(0).getKittenName(), "Ensimmäinen");
        assertEquals(littersFromDb.get(0).getKittens().get(0).getSex(), "Uros");
        assertEquals(littersFromDb.get(0).getKittens().get(0).getOfficialName(), "Ensimmäinen pentu");
        assertEquals(littersFromDb.get(0).getKittens().get(0).getRegno(), "X123");
        assertEquals(littersFromDb.get(0).getKittens().get(0).getEms(), "musta");
        assertEquals(littersFromDb.get(0).getBirth().toString(), "2020-10-30");
        assertEquals(littersFromDb.get(0).getKittens().get(0).getLitterId(), 1);
        assertEquals(littersFromDb.get(0).getKittens().get(0).getWeightList().get(0).getWeight(), 120);
        assertEquals(littersFromDb.get(0).getKittens().get(0).getWeightList().get(0).getDate().toString(), "2020-10-30");
       
    }

    /**
     * Test of getLitters method, of class KittenService.
     */
    @Test
    public void testGetLitters() {
        System.out.println("getLitters");
        ArrayList<Litter> expResult = null;
        kittenService.addLitter("Äiti", "Isä", "Testipentue", LocalDate.of(2020, 8, 25));
        ArrayList<Litter> littersFromDb = kittenService.getAllDataFromDb();
        assertEquals(littersFromDb.get(0).getLitterName(), "Testipentue");
        assertEquals(littersFromDb.get(0).getDam(), "Äiti");
        assertEquals(littersFromDb.get(0).getSire(), "Isä");
        assertEquals(littersFromDb.get(0).getEstablishmentDate().toString(), "2020-08-25");
    }

    /**
     * Test of addLitterToDb method, of class KittenService.
     */
    @Test
    public void testAddLitterToDb() {
        System.out.println("addLitterToDb");
        String dam = "Äiti";
        String sire = "Isä";
        String litterName = "Testipentue";
        LocalDate establishmentDate = LocalDate.of(2020, 8, 25);
        boolean addSuccessful = kittenService.addLitter(dam, sire, litterName, establishmentDate);
        ArrayList<Litter> littersFromDb = kittenService.getLitters();
        assertNotEquals(littersFromDb, null);
        assertTrue(addSuccessful);
        assertEquals(littersFromDb.get(0).getLitterName(), "Testipentue");
        assertEquals(littersFromDb.get(0).getDam(), "Äiti");
        assertEquals(littersFromDb.get(0).getSire(), "Isä");
        assertEquals(LocalDate.of(2020, 8, 25).toString(), "2020-08-25");
    }

    /**
     * Test of addKitten method, of class KittenService.
     */
    @Test
    public void testAddKitten() {
        System.out.println("addKitten");
        int litterId = 1;
        String kittenName = "Ensimmäinen";
        String sex = "Uros";
        String officialName = "Ensimmäinen pentu";
        String regno = "X123";
        String ems = "musta";
        LocalDate birth = LocalDate.of(2020, 10, 30);
        
        int kittenIdFromDb = kittenService.addKitten(litterId, kittenName, sex, officialName, regno, ems, birth);
        ArrayList<Kitten> kittensFromDb = kittenService.getKittensByLitterId(1);
        assertEquals(kittenIdFromDb, 1);
        assertEquals(kittensFromDb.get(0).getKittenName(), "Ensimmäinen");
        assertEquals(kittensFromDb.get(0).getSex(), "Uros");
        assertEquals(kittensFromDb.get(0).getOfficialName(), "Ensimmäinen pentu");
        assertEquals(kittensFromDb.get(0).getRegno(), "X123");
        assertEquals(kittensFromDb.get(0).getEms(), "musta");
        assertEquals(LocalDate.of(2020, 10, 30).toString(), "2020-10-30");
    }

    /**
     * Test of addKittenToDb method, of class KittenService.
     */
    @Test
    public void testAddKittenToDb() {
        System.out.println("addKittenToDb");
        int litterId = 1;
        String kittenName = "Ensimmäinen";
        String sex = "Uros";
        String officialName = "Ensimmäinen pentu";
        String regno = "X123";
        String ems = "musta";
        LocalDate birth = LocalDate.of(2020, 10, 30);
        
        int kittenIdFromDb = kittenService.addKitten(litterId, kittenName, sex, officialName, regno, ems, birth);
        ArrayList<Kitten> kittensFromDb = kittenService.getKittensByLitterId(1);
        assertEquals(kittenIdFromDb, 1);
        assertEquals(kittensFromDb.get(0).getKittenName(), "Ensimmäinen");
        assertEquals(kittensFromDb.get(0).getSex(), "Uros");
        assertEquals(kittensFromDb.get(0).getOfficialName(), "Ensimmäinen pentu");
        assertEquals(kittensFromDb.get(0).getRegno(), "X123");
        assertEquals(kittensFromDb.get(0).getEms(), "musta");
        assertEquals(LocalDate.of(2020, 10, 30).toString(), "2020-10-30");
    }

    /**
     * Test of getKittensByLitterId method, of class KittenService.
     */
    @Test
    public void testGetKittensByLitterId() {
        System.out.println("getKittensByLitterId");
        int litterId = 0;
        ArrayList<Kitten> expResult = null;
        kittenService.addKitten(1, "Ensimmäinen", "Uros", "Ensimmäinen pentu", "X123", "musta", LocalDate.of(2020, 10, 30));
        ArrayList<Kitten> kittensFromDb = kittenService.getKittensByLitterId(1);
        assertEquals(kittensFromDb.get(0).getLitterId(), 1);
        assertEquals(kittensFromDb.get(0).getKittenName(), "Ensimmäinen");
        assertEquals(kittensFromDb.get(0).getSex(), "Uros");
        assertEquals(kittensFromDb.get(0).getOfficialName(), "Ensimmäinen pentu");
        assertEquals(kittensFromDb.get(0).getRegno(), "X123");
        assertEquals(kittensFromDb.get(0).getEms(), "musta");    
    }

    /**
     * Test of getKittensIdsByLitterId method, of class KittenService.
     */
    @Test
    public void testGetKittensIdsByLitterId() {
        System.out.println("getKittensIdsByLitterId");
        int litterId = 1;
        ArrayList<Integer> expResult = null;
        kittenService.addKitten(1, "Ensimmäinen", "Uros", "Ensimmäinen pentu", "X123", "musta", LocalDate.of(2020, 10, 30));
        ArrayList<Kitten> kittensFromDb = kittenService.getKittensByLitterId(1);
        assertEquals(kittensFromDb.get(0).getLitterId(), 1);
    }

    /**
     * Test of getKittenWeightsByKittenId method, of class KittenService.
     */
    @Test
    public void testGetKittenWeightsByKittenId() {
        System.out.println("getKittenWeightsByKittenId");
        int kittenId = 1;
        int weight = 120;
        LocalDate date = LocalDate.of(2020, 8, 25);
        ArrayList<Weight> expResult = null;
        kittenService.addKitten(1, "Ensimmäinen", "Uros", "Ensimmäinen pentu", "X123", "musta", LocalDate.of(2020, 10, 30));
        kittenService.addWeight(1, 120, LocalDate.of(2020, 10, 30));
        ArrayList<Weight> weightsFromDb = kittenService.getKittenWeightsByKittenId(1);
        assertEquals(weightsFromDb.get(0).getId(), 1);
        assertEquals(weightsFromDb.get(0).getWeight(), 120);
        assertEquals(LocalDate.of(2020, 8, 25).toString(), "2020-08-25"); 
    }

    /**
     * Test of addWeight method, of class KittenService.
     */
    @Test
    public void testAddWeight() {
        System.out.println("addWeight");
        int kittenId = 1;
        int weight = 120;
        LocalDate date = LocalDate.of(2020, 8, 25);
        boolean addSuccesful = kittenService.addWeight(kittenId, weight, date);
        ArrayList<Weight> weightsFromDb = kittenService.getKittenWeightsByKittenId(1);
        assertNotEquals(weightsFromDb, null);
        assertEquals(weightsFromDb.get(0).getId(), 1);
        assertEquals(weightsFromDb.get(0).getWeight(), 120);
        assertEquals(LocalDate.of(2020, 8, 25).toString(), "2020-08-25");
    }

    /**
     * Test of removeWeight method, of class KittenService.
     */
    @Test
    public void testRemoveWeight() {
        System.out.println("removeWeight");
        kittenService.addWeight(1, 120, LocalDate.of(2020, 8, 25));
        Boolean removeSuccessful = kittenService.removeWeight(1);
        ArrayList<Weight> weightsFromDb = kittenService.getKittenWeightsByKittenId(1);
        assertTrue(removeSuccessful);
        assertEquals(weightsFromDb.size(), 0);
    }

    /**
     * Test of removeWeightByKittenId method, of class KittenService.
     */
    @Test
    public void testRemoveWeightByKittenId() {
        System.out.println("removeWeightByKittenId");
        kittenService.addWeight(1, 120, LocalDate.of(2020, 8, 25));
        Boolean removeSuccessful = kittenService.removeWeight(1);
        ArrayList<Weight> weightsFromDb = kittenService.getKittenWeightsByKittenId(1);
        assertTrue(removeSuccessful);
        assertEquals(weightsFromDb.size(), 0);
    }

    /**
     * Test of removeKitten method, of class KittenService.
     */
    @Test
    public void testRemoveKitten() {
        System.out.println("removeKitten");
        kittenService.addKitten(1, "Ensimmäinen", "Uros", "Ensimmäinen pentu", "X123", "musta", LocalDate.of(2020, 10, 30));
        Boolean removeSuccessful = kittenService.removeKitten(1);
        ArrayList<Kitten> kittensFromDb = kittenService.getKittensByLitterId(1);
        assertTrue(removeSuccessful);
        assertEquals(kittensFromDb.size(), 0);
    }

    /**
     * Test of removeKittenByLitterId method, of class KittenService.
     */
    @Test
    public void testRemoveKittenByLitterId() {
        System.out.println("removeKittenByLitterId");
        kittenService.addKitten(1, "Ensimmäinen", "Uros", "Ensimmäinen pentu", "X123", "musta", LocalDate.of(2020, 10, 30));
        Boolean removeSuccessful = kittenService.removeKitten(1);
        ArrayList<Kitten> kittensFromDb = kittenService.getKittensByLitterId(1);
        assertTrue(removeSuccessful);
        assertEquals(kittensFromDb.size(), 0);
    }

    /**
     * Test of removeDiaryById method, of class KittenService.
     */
    @Test
    public void testRemoveDiaryById() {
        System.out.println("removeDiaryById");
        int id = 1;
        LocalDate diaryDate = LocalDate.of(2020, 8, 25);;
        String diaryText = "Päiväkirjamerkintä";
        Boolean removeSuccessful = kittenService.removeDiaryById(1);
        ArrayList<Diary> diariesFromDb = kittenService.getWholeDiaryByLitterId(1);
        assertTrue(removeSuccessful);
        assertEquals(diariesFromDb.size(), 0);
    }

    /**
     * Test of removeDiaryByLitterId method, of class KittenService.
     */
    @Test
    public void testRemoveDiaryByLitterId() {
        System.out.println("removeDiaryByLitterId");
        int id = 1;
        LocalDate diaryDate = LocalDate.of(2020, 8, 25);;
        String diaryText = "Päiväkirjamerkintä";
        Boolean removeSuccessful = kittenService.removeDiaryById(1);
        ArrayList<Diary> diariesFromDb = kittenService.getWholeDiaryByLitterId(1);
        assertTrue(removeSuccessful);
        assertEquals(diariesFromDb.size(), 0);
    }

    /**
     * Test of removeLitter method, of class KittenService.
     */
    @Test
    public void testRemoveLitter() {
        System.out.println("removeLitter");
        
        kittenService.addLitter("Äiti", "Isä", "Testipentue", LocalDate.of(2020, 8, 25));
        Boolean removeSuccessful = kittenService.removeLitter(1);
        ArrayList<Litter> littersFromDb = kittenService.getLitters();
        assertTrue(removeSuccessful);
        assertEquals(littersFromDb.size(), 0);
    }

    /**
     * Test of updateKitten method, of class KittenService.
     */
    @Test
    public void testUpdateKitten() {
        System.out.println("updateKitten");
        int litterId = 1;
        int kittenId = 1;
        String kittenName = "Ensimmäinen";
        String sex = "Uros";
        String officialName = "Ensimmäinen pentu";
        String regno = "X123";
        String ems = "musta";
        LocalDate birth = LocalDate.of(2020, 10, 30);
        
        int kittenIdFromDb = kittenService.addKitten(litterId, kittenName, sex, officialName, regno, ems, birth);
        ArrayList<Kitten> kittensFromDb = kittenService.getKittensByLitterId(1);
        assertEquals(kittenIdFromDb, 1);
        assertEquals(kittensFromDb.get(0).getKittenName(), "Ensimmäinen");
        assertEquals(kittensFromDb.get(0).getSex(), "Uros");
        assertEquals(kittensFromDb.get(0).getOfficialName(), "Ensimmäinen pentu");
        assertEquals(kittensFromDb.get(0).getRegno(), "X123");
        assertEquals(kittensFromDb.get(0).getEms(), "musta");
        assertEquals(LocalDate.of(2020, 10, 30).toString(), "2020-10-30");
        
        String updatedKittenName = "Ensimmäinen muokattu";
        String updatedSex = "Naaras";
        String updatedOfficialName = "Ensimmäinen pentu muokattu";
        String updatedRegno = "Y123";
        String updatedEms = "Valkoinen";
        int updatedKittenId = kittenService.updateKitten(kittenId, updatedKittenName, updatedSex, updatedOfficialName, updatedRegno, updatedEms, birth);
        ArrayList<Kitten> updatedKittensFromDb = kittenService.getKittensByLitterId(1);
        assertTrue(updatedKittenId == kittenId);
        assertEquals(updatedKittensFromDb.get(0).getKittenName(), "Ensimmäinen muokattu");
        assertEquals(updatedKittensFromDb.get(0).getSex(), "Naaras");
        assertEquals(updatedKittensFromDb.get(0).getOfficialName(), "Ensimmäinen pentu muokattu");
        assertEquals(updatedKittensFromDb.get(0).getRegno(), "Y123");
        assertEquals(updatedKittensFromDb.get(0).getEms(), "Valkoinen");

    }

    /**
     * Test of updateLitter method, of class KittenService.
     */
    @Test
    public void testUpdateLitter() {
        System.out.println("updateLitter");
        System.out.println("addLitter");
        String dam = "Äiti";
        String sire = "Isä";
        String litterName = "Testipentue";
        LocalDate establishmentDate = LocalDate.of(2020, 8, 25);
        boolean addSuccessful = kittenService.addLitter(dam, sire, litterName, establishmentDate);
        ArrayList<Litter> littersFromDb = kittenService.getLitters();
        assertNotEquals(littersFromDb, null);
        assertTrue(addSuccessful);
        assertEquals(littersFromDb.get(0).getLitterName(), "Testipentue");
        assertEquals(littersFromDb.get(0).getDam(), "Äiti");
        assertEquals(littersFromDb.get(0).getSire(), "Isä");
        assertEquals(LocalDate.of(2020, 8, 25).toString(), "2020-08-25");
        
        LocalDate updatedEstablismentDate = LocalDate.of(2020, 10, 30);
        LocalDate updatedBirthDate = LocalDate.of(2020, 11, 30);
        String updatedDamName = "Äiti muokattu";
        String updatedSireName = "Isä muokattu";
        String updatedLitterName = "Testipentue muokattu";
        boolean updateSuccesful = kittenService.updateLitter(updatedDamName,updatedSireName, updatedEstablismentDate, updatedBirthDate, updatedLitterName, 1 );
        ArrayList<Litter> updatedLittersFromDb = kittenService.getLitters();
        assertTrue(updateSuccesful);
        assertEquals(updatedLittersFromDb.get(0).getId(), 1);
        assertEquals(updatedLittersFromDb.get(0).getDam(), "Äiti muokattu");
        assertEquals(updatedLittersFromDb.get(0).getSire(), "Isä muokattu");
        assertEquals(updatedLittersFromDb.get(0).getEstablishmentDate().toString(), "2020-10-30");
        assertEquals(updatedLittersFromDb.get(0).getLitterName(), "Testipentue muokattu");
        assertEquals(updatedLittersFromDb.get(0).getBirth().toString(), "2020-11-30");
    }

    /**
     * Test of updateDiary method, of class KittenService.
     */
    @Test
    public void testUpdateDiary() {
        System.out.println("updateDiary");
        int id = 1;
        LocalDate diaryDate = LocalDate.of(2020, 8, 25);;
        String diaryText = "Päiväkirjamerkintä";
        boolean addSuccesful = kittenService.insertDiary(id, diaryDate, diaryText);
        ArrayList<Diary> diariesFromDb = kittenService.getWholeDiaryByLitterId(1);
        assertNotEquals(diariesFromDb, null);
        assertTrue(addSuccesful);
        assertEquals(diariesFromDb.get(0).getId(), 1);
        assertEquals(LocalDate.of(2020, 8, 25).toString(), "2020-08-25");
        assertEquals(diariesFromDb.get(0).getText(), "Päiväkirjamerkintä");
        
        LocalDate updatedDiaryDate = LocalDate.of(2020, 10, 30);
        String updatedDiaryText = "Päiväkirjamerkintä muokattu";
        boolean updateSuccesful = kittenService.updateDiary(updatedDiaryDate, updatedDiaryText, id);
        ArrayList<Diary> updatedDiariesFromDb = kittenService.getWholeDiaryByLitterId(1);
        assertTrue(updateSuccesful);
        assertEquals(updatedDiariesFromDb.get(0).getId(), 1);
        assertEquals(LocalDate.of(2020, 10, 30).toString(), "2020-10-30");
        assertEquals(updatedDiariesFromDb.get(0).getText(), "Päiväkirjamerkintä muokattu");
    }

    /**
     * Test of insertDiary method, of class KittenService.
     */
    @Test
    public void testInsertDiary() {
        System.out.println("insertDiary");
        int id = 1;
        LocalDate diaryDate = LocalDate.of(2020, 8, 25);;
        String diaryText = "Päiväkirjamerkintä";
        boolean addSuccesful = kittenService.insertDiary(id, diaryDate, diaryText);
        ArrayList<Diary> diariesFromDb = kittenService.getWholeDiaryByLitterId(1);
        assertNotEquals(diariesFromDb, null);
        assertTrue(addSuccesful);
        assertEquals(diariesFromDb.get(0).getId(), 1);
        assertEquals(LocalDate.of(2020, 8, 25).toString(), "2020-08-25");
        assertEquals(diariesFromDb.get(0).getText(), "Päiväkirjamerkintä");
        
    }

    /**
     * Test of getWholeDiaryByLitterId method, of class KittenService.
     */
    @Test
    public void testGetWholeDiaryByLitterId() {
        System.out.println("getWholeDiaryByLitterId");
        int litterId = 1;
        ArrayList<Diary> expResult = null;
        kittenService.insertDiary(1, LocalDate.of(2020, 8, 25), "Päiväkirjamerkintä");
        ArrayList<Diary> diariesFromDb = kittenService.getWholeDiaryByLitterId(1);
        assertEquals(diariesFromDb.get(0).getId(), 1);
        assertEquals(diariesFromDb.get(0).getDate().toString(), "2020-08-25");
        assertEquals(diariesFromDb.get(0).getText(), "Päiväkirjamerkintä");
    }

}
