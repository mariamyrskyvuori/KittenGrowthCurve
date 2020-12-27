package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.Diary;
import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
import com.maria.kittengrowthcurve.domain.Weight;
import com.maria.kittengrowthcurve.util.DbUtils;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;

/**
 *
 * @author maria
 */
public class KittenService {
    /**
     * <p>Luo ohjelmiston käyttämän SQLite-tietokannan, kun ohjelmisto otetaan käyttöön ensimmäisen kerran</p>
     */
    public KittenService() {
        DbUtils.createSqlFolderIfNotExist();
        try (Connection conn = this.connect();  Statement stmt = conn.createStatement()) {
            stmt.execute(DbUtils.getSqlForCreateLitterTableIfNotExists());
            stmt.execute(DbUtils.getSqlForCreateKittenTableIfNotExists());
            stmt.execute(DbUtils.getSqlForCreateWeightTableIfNotExists());
            stmt.execute(DbUtils.getSqlForCreateDiaryTableIfNotExists());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /** 
     * <p>Luo pentue-olion käyttäjän syöttämillä tiedoilla ja laskee astutuspäivän perusteella arvioidut
     * ajankohdat synnytykselle sekä luovutukselle</p>
     * @param dam
     * @param sire
     * @param litterName
     * @param establishmentDate
     * @return <p>Onnistuiko tallennus</p>
     */
    public boolean addLitter(String dam, String sire, String litterName, LocalDate establishmentDate) {
        Litter litter = new Litter(dam, sire, litterName, establishmentDate);
        litter.calculateDates();
        
        return addLitterToDb(litter);
    }
    /**
     * <p>Hakee kannasta kaiken tarvittavan tiedon ohjelmiston tarpeisiin. Tekee pentueista ArrayListin, 
     * liittää pentueisiin ArrayListin pennuista ja liittää pentuihin ArrayListin painoista</p>
     * @return Ajantasainen lista pentueista
     */
    public ArrayList<Litter> getAllDataFromDb() {
        ArrayList<Litter> littersFromDb = getLitters();
        
        for (Litter litter : littersFromDb) {
            ArrayList<Kitten> kittens = getKittensByLitterId(litter.getId());
            
            for (int i = 0; i < kittens.size(); i++) {
                ArrayList<Weight> weightList = getKittenWeightsByKittenId(kittens.get(i).getId());
                kittens.get(i).setWeightList(weightList);
            }
            
            litter.setKittens(kittens);
        }
        
        return littersFromDb;
    }
    
    /**
     * <p>Toimii yhdessä seuraavan metodin kanssa. Saa seuraavalta metodilta ResultSetin ja luo niistä pentueen</p>
     * @param rs
     * @return pentue
     * @throws SQLException 
     */
    private Litter makeLitterFromQuery(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String litterName = rs.getString("litterName");
        String dam = rs.getString("dam");
        String sire = rs.getString("sire");
        String establishment = rs.getString("establishment");
        String birth = rs.getString("birth");
        String delivery = rs.getString("delivery");
        LocalDate birthDay = LocalDate.parse(birth);
        LocalDate establishmentDay = LocalDate.parse(establishment);
        LocalDate deliveryDay = LocalDate.parse(delivery);
        Litter litter = new Litter(dam, sire, litterName, establishmentDay, birthDay, deliveryDay, id);

        return litter;
    }
    /**
     * <p>Toimii yhdessä edellisen metodin kanssa. Hakee tietokannasta kaiken taulusta Litter, käyttää edellistä metodia
     * saadakseen taulun rivit olioksi ja tallentaa oliot ArrayListiin</p>
     * @return ArrayList pentueista
     */
    public ArrayList<Litter> getLitters() {
        try (Connection conn = this.connect();  Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Litter;");
            ArrayList<Litter> litters = new ArrayList();
            
            while (rs.next()) {
                litters.add(makeLitterFromQuery(rs));
            }
            rs.close();
            stmt.close();
            conn.close();
            return litters;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * <p>Tallentaa pentueen tietokantaan</p>
     * @param litter
     * @return Onnistuiko tallennus
     */
    public boolean addLitterToDb(Litter litter) {
        String sql = "INSERT INTO Litter(dam, sire, litterName, establishment, birth, delivery) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, litter.getDam());
            pstmt.setString(2, litter.getSire());
            pstmt.setString(3, litter.getLitterName());
            pstmt.setString(4, litter.getEstablishmentDate().toString());
            pstmt.setString(5, litter.getBirth().toString());
            pstmt.setString(6, litter.getDeliveryDate().toString());
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Kantayhteyden luominen</p>
     * @return yhteys
     */
    private Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:sql/kittenGrowthCurve.db");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    /**
     * <p>Toimii yhdessä seuraavan metodin kanssa. Luo pentu-olion ja kutsuu sen tietokantaan tallentavaa
     * metodia, joka palauttaa tiedon onnistumisesta numerona.</p>
     * @param litterId
     * @param kittenName
     * @param sex
     * @param officialName
     * @param regno
     * @param ems
     * @param birth
     * @return pennun id tai -1, joka tarkoittaa, ettei tallennus onnistunut.
     */
    public int addKitten(int litterId, String kittenName, String sex, String officialName, String regno, String ems, LocalDate birth) {
        Kitten kitten = new Kitten(kittenName, sex, officialName, regno, ems, litterId);
        int kittenId = addKittenToDb(kitten);
        return kittenId;
    }
    /**
     * <p>Toimii yhdessä edellisen metodin kanssa. Tallentaa metodilta saamansa pentu-olion tietokantaan.</p>
     * @param kitten
     * @return pennun id tai -1, joka tarkoittaa, ettei tallennus onnistunut.
     */
    public int addKittenToDb(Kitten kitten) {
        String sql = "INSERT INTO Kitten(litter_id, name, sex,regNo, emsCode, officialName) VALUES(?,?,?,?,?,?)";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kitten.getLitterId());
            pstmt.setString(2, kitten.getKittenName());
            pstmt.setString(3, kitten.getSex());
            pstmt.setString(4, kitten.getRegno());
            pstmt.setString(5, kitten.getEms());
            pstmt.setString(6, kitten.getOfficialName());
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            int kittenId = -1;
            while (rs.next()) {
                kittenId = rs.getInt(1);
            }
            conn.close();
            pstmt.close();
            return kittenId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }
    /**
     * <p>Hakee tietokannasta pentueen id:llä kaikki pentujen tiedot, luo niistä pentu-olioita ja tekee
     * näistä olioista ArrayListin</p>
     * @param litterId
     * @return ArrayList pennuista
     */
    ArrayList<Kitten> getKittensByLitterId(int litterId) {
        String sql = "SELECT * FROM Kitten WHERE litter_id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, litterId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Kitten> kittens = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                String kittenName = rs.getString("name");
                String sex = rs.getString("sex");
                String officialName = rs.getString("officialName");
                String regno = rs.getString("regNo");
                String ems = rs.getString("emsCode");
                Kitten kitten = new Kitten(kittenName, sex, officialName, regno, ems, litterId, id);
                kittens.add(kitten);
            }
            rs.close();
            pstmt.close();
            conn.close();
            return kittens;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * <p>Hakee tietokannasta pentueen id:llä kaikkien pentujet id:t ja tekee niistä ArrayListin</p>
     * @param litterId
     * @return ArrayList pentujen id:stä
     */
    ArrayList<Integer> getKittensIdsByLitterId(int litterId) {
        String sql = "SELECT id FROM Kitten WHERE litter_id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, litterId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Integer> kittensIds = new ArrayList();
            while (rs.next()) {
                kittensIds.add(rs.getInt("id"));
            }
            rs.close();
            pstmt.close();
            conn.close();
            return kittensIds;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * <p>Hakee tietokannasta pennun id:llä kaikki sille tallennetut painotiedot ja tekee niistä ArrayListin </p>
     * @param kittenId
     * @return ArrayList pennun painotiedoista
     */
    ArrayList<Weight> getKittenWeightsByKittenId(int kittenId) {
        String sql = "SELECT * FROM Weight WHERE kitten_id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kittenId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Weight> weightList = new ArrayList<>();
            while (rs.next()) {
                String date = rs.getString("date");
                Integer weight = rs.getInt("weight");
                Integer id = rs.getInt("id");
                Weight weightObject = new Weight(id, LocalDate.parse(date), weight);
                weightList.add(weightObject);
            }
            rs.close();
            pstmt.close();
            conn.close();
            return weightList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    /**
     * <p>Tallentaa pennun painotiedot tietokantaan</p>
     * @param kittenId
     * @param weight
     * @param date
     * @return Onnistuiko tallennus
     */
    public boolean addWeight(int kittenId, int weight, LocalDate date) {
        String sql = "INSERT INTO Weight(kitten_id, weight, date) VALUES(?,?,?)";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kittenId);
            pstmt.setInt(2, weight);
            pstmt.setString(3, date.toString());
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Poistaa yksittäisen painotiedon tietokannasta</p>
     * @param weightId
     * @return Onnistuiko poisto
     */
    public Boolean removeWeight(int weightId) {
        String sql = "DELETE FROM Weight WHERE id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, weightId);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Poistaa tietokannasta kaikki painotiedot yhdeltä pennulta</p>
     * @param kittenId
     * @return Onnistuiko poisto
     */
    public Boolean removeWeightByKittenId(int kittenId) {
        String sql = "DELETE FROM Weight WHERE kitten_id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kittenId);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Poistaa pennun tietokannasta</p>
     * @param id
     * @return Onnistuiko poisto
     */
    public Boolean removeKitten(int id) {
        removeWeightByKittenId(id);
        String sql = "DELETE FROM Kitten WHERE id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Poistaa tietokannasta kaikki pentueen pennut</p>
     * @param litterId
     * @return Onnistuiko poisto
     */
    public Boolean removeKittenByLitterId(int litterId) {
        String sql = "DELETE FROM Kitten WHERE litter_id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, litterId);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Poistaa tietokannasta päiväkirjamerkinnän</p>
     * @param id
     * @return Onnistuiko poisto
     */
    public Boolean removeDiaryById(int id) {
        String sql = "DELETE FROM Diary WHERE id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Poistaa tietokannasta kaikki pentueen päiväkirjamerkinnät</p>
     * @param litterId
     * @return Onnistuiko poisto
     */
    public Boolean removeDiaryByLitterId(int litterId) {
        String sql = "DELETE FROM Diary WHERE litter_id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, litterId);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    /**
     *<p>Poistaa pentueen tietokannasta. Koska päiväkirjalla ja pennulla on litter_id, tulee nämä
     * poistaa ennen pentuetta. Painotaululla on kitten_id, joten se on poistettava ensimmäisenä. Tämä metodi käy
     * läpi pentueen pentujen id:t ja käyttää niitä poistaakseen painotiedot. Sen jälkeen poistetaan
     * pentueen pennut ja päiväkirjamerkinnät ja lopulta pentue</p>
     * @param id
     * @return Onnistuiko poisto
     */
    public Boolean removeLitter(int id) { 
        
        for (Integer kittenId : getKittensIdsByLitterId(id)) {
            removeWeightByKittenId(kittenId);
        }
         
        removeKittenByLitterId(id);
        removeDiaryByLitterId(id);
        
        String sql = "DELETE FROM Litter WHERE Litter.id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Päivittää tietokantaan pennun tiedot, kun tietoja on korjattu tai lisätty</p>
     * @param kittenId
     * @param kittenName
     * @param sex
     * @param officialName
     * @param regno
     * @param ems
     * @param birth
     * @return pennun id tai -2, jos päivitys ei onnistunut
     */
    public int updateKitten(int kittenId, String kittenName, String sex, String officialName, String regno, String ems, LocalDate birth) {
        String sql = "UPDATE Kitten SET name = ?, sex = ?,regNo = ?, emsCode= ?, officialName = ? WHERE id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kittenName);
            pstmt.setString(2, sex);
            pstmt.setString(3, regno);
            pstmt.setString(4, ems);
            pstmt.setString(5, officialName);
            pstmt.setInt(6, kittenId);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return kittenId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return -2;
        }
    }
    /**
     * <p>Päivittää tietokantaan pentueen tiedot, kun tietoja on korjattu</p>
     * @param dam
     * @param sire
     * @param establishmentDate
     * @param birth
     * @param litterName
     * @param id
     * @return Onnistuiko päivitys
     */
    public boolean updateLitter(String dam, String sire, LocalDate establishmentDate, LocalDate birth, String litterName, int id) {
        String sql = "UPDATE Litter SET dam = ?, sire = ?, establishment = ?, birth = ?, litterName = ?  WHERE id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, dam);
            pstmt.setString(2, sire);
            pstmt.setString(3, establishmentDate.toString());
            pstmt.setString(4, birth.toString());
            pstmt.setString(5, litterName);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Päivittää tietokantaan päiväkirjamerkinnän, kun sitä on muokattu</p>
     * @param diaryDate
     * @param diaryText
     * @param id
     * @return Onnistuiko päivitys
     */
    public boolean updateDiary(LocalDate diaryDate, String diaryText, int id) {
        String sql = "UPDATE Diary SET date = ?, text = ? WHERE id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, diaryDate.toString());
            pstmt.setString(2, diaryText);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Tallentaa päiväkirjamerkinnän tietokantaan</p>
     * @param id
     * @param diaryDate
     * @param diaryText
     * @return Onnistuiko tallennus
     */
    public boolean insertDiary(int id, LocalDate diaryDate, String diaryText) {
        String sql = "INSERT INTO Diary(litter_id, date, text) VALUES(?,?,?)";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, diaryDate.toString());
            pstmt.setString(3, diaryText);
            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * <p>Hakee tietokannasta pentueen kaikki päiväkirjamerkinnät</p>
     * @param litterId
     * @return 
     */
    public ArrayList<Diary> getWholeDiaryByLitterId(int litterId) {
        String sql = "SELECT * FROM Diary WHERE litter_id = ?";
        try (Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, litterId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Diary> diaryList = new ArrayList<>();
            while (rs.next()) {
                String date = rs.getString("date");
                String text = rs.getString("text");
                Integer id = rs.getInt("id");
                Diary diaryObject = new Diary(id, LocalDate.parse(date), text);
                diaryList.add(diaryObject);
            }
            rs.close();
            pstmt.close();
            conn.close();
            return diaryList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }    
}
