package com.maria.kittengrowthcurve;

import com.maria.kittengrowthcurve.domain.Diary;
import com.maria.kittengrowthcurve.domain.Kitten;
import com.maria.kittengrowthcurve.domain.Litter;
import com.maria.kittengrowthcurve.domain.Weight;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;

/**
 *
 * @author maria
 */
public class KittenService {

    public boolean addLitter(String dam, String sire, String litterName, LocalDate establishmentDate) {
        Litter litter = new Litter(dam, sire, litterName, establishmentDate);
        litter.calculateDates();
        
        return addLitterToDb(litter);
    }

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

    public int addKitten(int litterId, String kittenName, String sex, String officialName, int weigth, String regno, String ems, LocalDate birth) {
        Kitten kitten = new Kitten(kittenName, sex, officialName, regno, ems, litterId);
        int kittenId = addKittenToDb(kitten);
        if (kittenId != -1) {
            addWeight(kittenId, weigth, birth);
        }
        return kittenId;
    }

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

    public int updateKitten(int kittenId, String kittenName, String sex, String officialName, int weigth, String regno, String ems, LocalDate birth) {
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
