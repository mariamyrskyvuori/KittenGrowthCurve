# KittenGrowthcurve

Ohjelma on työkalu kissankasvattajalle. Ohjelma seuraa tiineysajan kestoa ja synnytyksen jälkeen pentujen painonkehitystä luovutusikään (14vk) saakka. Ohjelma tuottaa pennuille kasvukäyrät, joita voi tarkastella yksittäin, pentueittain tai useampaa pentuetta keskenään vertaamalla. Lisäksi pentueella on päiväkirja, jonne voi lisätä muistiinpanoja. Päiväkirja laskee pentujen iän automaattisesti jokaiseen merkintään. 

### Asennus

Asenna [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

Asenna [Java FX](https://www.oracle.com/java/technologies/install-javafx-sdk.html)

Asenna [SQLite](https://sqlite.org/index.html)

Luo tietokanta ja taulut. Sql-skripti on tiedostossa CreateTables.sql

```sh
$ cd KittenGrowthCurve
$ cd sql
$ sqlite3 kittenGrowthCurve.db
$ sqlite> .read CreateTables.sql
$ sqlite> .exit
```

Käynnistä ohjelma
```sh
$ cd ..
$ mvn clean package
$ java -jar target/KittenGrowthCurve-1.0-SNAPSHOT.jar
```