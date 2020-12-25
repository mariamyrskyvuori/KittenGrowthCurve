--
-- File generated with SQLiteStudio v3.2.1 on Sat Nov 14 16:58:53 2020
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

DROP TABLE Kitten;

-- Table: Kitten
CREATE TABLE Kitten (
    id        INTEGER  PRIMARY KEY
                       UNIQUE
                       NOT NULL,
    litter_id INTEGER  REFERENCES Litter (id) 
                       NOT NULL,
    name      STRING,
    sex       BOOLEAN,
    weight    INTEGER,
    regNo     STRING,
    emsCode   STRING,
    birthTime DATETIME
);

INSERT INTO Kitten (
                       id,
                       litter_id,
                       name,
                       sex,
                       weight,
                       regNo,
                       emsCode,
                       birthTime
                   )
                   VALUES (
                       1,
                       1,
                       'Firestar',
                       'Uros',
                       112,
                       'x',
                       'SIB ds 09 24',
                       '18:10'
                   );

INSERT INTO Kitten (
                       id,
                       litter_id,
                       name,
                       sex,
                       weight,
                       regNo,
                       emsCode,
                       birthTime
                   )
                   VALUES (
                       2,
                       1,
                       'Shadowsight',
                       'Uros',
                       108,
                       'x',
                       'SIB ns 09 24',
                       '20:30'
                   );

INSERT INTO Kitten (
                       id,
                       litter_id,
                       name,
                       sex,
                       weight,
                       regNo,
                       emsCode,
                       birthTime
                   )
                   VALUES (
                       3,
                       1,
                       'Birchfall',
                       'Uros',
                       111,
                       'x',
                       'SIB ny 24',
                       '20:55'
                   );

INSERT INTO Kitten (
                       id,
                       litter_id,
                       name,
                       sex,
                       weight,
                       regNo,
                       emsCode,
                       birthTime
                   )
                   VALUES (
                       4,
                       1,
                       'Sparkpelt',
                       'Naaras',
                       91,
                       'x',
                       'SIB f 09 22',
                       '21:25'
                   );

INSERT INTO Kitten (
                       id,
                       litter_id,
                       name,
                       sex,
                       weight,
                       regNo,
                       emsCode,
                       birthTime
                   )
                   VALUES (
                       5,
                       1,
                       'Moonflower',
                       'Naaras',
                       99,
                       'x',
                       'SIB ns 24',
                       '23:30'
                   );

INSERT INTO Kitten (
                       id,
                       litter_id,
                       name,
                       sex,
                       weight,
                       regNo,
                       emsCode,
                       birthTime
                   )
                   VALUES (
                       6,
                       2,
                       'Gilbert',
                       'Uros',
                       123,
                       'x',
                       'x',
                       '12:20'
                   );


-- Table: Litter

DROP TABLE Litter;

CREATE TABLE Litter (
    id            INTEGER PRIMARY KEY
                          UNIQUE
                          NOT NULL,
    litterName    STRING  NOT NULL,
    dam           STRING  NOT NULL,
    sire          STRING  NOT NULL,
    establishment DATE    NOT NULL,
    birth         DATE    NOT NULL,
    delivery      DATE    NOT NULL
);

INSERT INTO Litter (
                       id,
                       litterName,
                       dam,
                       sire,
                       establishment,
                       birth,
                       delivery
                   )
                   VALUES (
                       1,
                       'I-pentue',
                       'Nadja',
                       'Svante',
                       1598302800000,
                       1604008800000,
                       1612476000000
                   );

INSERT INTO Litter (
                       id,
                       litterName,
                       dam,
                       sire,
                       establishment,
                       birth,
                       delivery
                   )
                   VALUES (
                       2,
                       'A-pentu',
                       'Maria',
                       'Antti',
                       1597266000000,
                       1602968400000,
                       1611439200000
                   );


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
