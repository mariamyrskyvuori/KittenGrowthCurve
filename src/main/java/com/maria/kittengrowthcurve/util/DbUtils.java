/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maria.kittengrowthcurve.util;
 
import java.io.File;
 
public class DbUtils {
    public static String getSqlForCreateLitterTableIfNotExists() {
        return "CREATE TABLE IF NOT EXISTS Litter (\n" +
                "                        id            INTEGER PRIMARY KEY\n" +
                "                                              UNIQUE\n" +
                "                                              NOT NULL,\n" +
                "                        litterName    STRING  NOT NULL,\n" +
                "                        dam           STRING  NOT NULL,\n" +
                "                        sire          STRING  NOT NULL,\n" +
                "                        establishment TEXT    NOT NULL,\n" +
                "                        birth         DATE    NOT NULL,\n" +
                "                        delivery      DATE    NOT NULL\n" +
                ");";
    }
 
    public static String getSqlForCreateKittenTableIfNotExists() {
        return "CREATE TABLE IF NOT EXISTS Kitten (\n" +
                "                        id           INTEGER PRIMARY KEY\n" +
                "                                     UNIQUE\n" +
                "                                     NOT NULL,\n" +
                "                        litter_id    INTEGER REFERENCES Litter (id)\n" +
                "                                     NOT NULL,\n" +
                "                        name         STRING,\n" +
                "                        sex          BOOLEAN,\n" +
                "                        regNo        STRING,\n" +
                "                        emsCode      STRING,\n" +
                "                        officialName STRING\n" +
                ");";
    }
 
    public static String getSqlForCreateWeightTableIfNotExists() {
        return "CREATE TABLE IF NOT EXISTS Weight (\n" +
                "                        id        INTEGER PRIMARY KEY\n" +
                "                                          UNIQUE\n" +
                "                                          NOT NULL,\n" +
                "                        kitten_id INTEGER REFERENCES Kitten (id)\n" +
                "                                          NOT NULL,\n" +
                "                        weight    INTEGER NOT NULL,\n" +
                "                        date      DATE    NOT NULL\n" +
                ");";
    }
 
    public static String getSqlForCreateDiaryTableIfNotExists() {
        return "CREATE TABLE IF NOT EXISTS Diary (\n" +
                "                       id        INTEGER PRIMARY KEY\n" +
                "                                         UNIQUE\n" +
                "                                         NOT NULL,\n" +
                "                       litter_id INTEGER REFERENCES Litter (id)\n" +
                "                                         NOT NULL,\n" +
                "                       date      DATE    NOT NULL,\n" +
                "                       text      TEXT    NOT NULL\n" +
                ");";
    }
 
    public static void createSqlFolderIfNotExist() {
        File dir = new File("sql");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
