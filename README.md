# KittenGrowthcurve

Ohjelma on työkalu kissankasvattajalle. Ohjelma seuraa pentujen painonkehitystä luovutusikään (14vk) saakka. Ohjelma tuottaa pennuille kasvukäyrät, joita voi tarkastella yksittäin, pentueittain tai useampaa pentuetta keskenään vertaamalla. Lisäksi pentueella on päiväkirja, jonne voi lisätä muistiinpanoja. Päiväkirja laskee pentujen iän automaattisesti jokaiseen merkintään. 

### Asennus

Asenna [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

Asenna [SQLite](https://sqlite.org/index.html)

Asenna [Maven](https://maven.apache.org/)


Käynnistä ohjelma
```sh
$ cd ..
$ mvn clean package
$ java -jar target/KittenGrowthCurve-1.0-SNAPSHOT.jar
```

### Luo suoritettava ohjelma OSX:lle
Laita Java11 SDK:n sijainti pom.xml -tiedostoon
```sh
<jrePath>/polku/omalla/koneellasi/adoptopenjdk-11.jdk/</jrePath>
```
Generoi KittenGrowthCurve-1.0-SNAPSHOT.dmg -tiedosto target -hakemistoon
```sh
$ mvn clean package appbundle:bundle
```
Kopioi KittenGrowthCurve.app omalle koneellesi ja suorita se

### Generoi testikattavuusraportti
```sh
$ mvn clean package
$ mvn org.pitest:pitest-maven:mutationCoverage
```
Generoi testikattavuusraportin target -hakemistoon

### Generoi tyyliraportti
```sh
$ mvn checkstyle:checkstyle
```
Generoi tyyliraportin target -hakemistoon
