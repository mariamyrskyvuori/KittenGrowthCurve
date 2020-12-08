package com.maria.kittengrowthcurve;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import javafx.scene.text.Text;

/**
 *
 * @author maria
 */
public class KittenService {

    public KittenService() {
    }

    //Luo pentueolion ja kutsuu metodia, joka laittaa sen kantaan
    boolean addLitter(String dam, String sire, String litterName, LocalDate establishmentDate) {
        Litter litter = new Litter(dam, sire, litterName, establishmentDate);
        litter.calculateDates();
        return addLitterToDb(litter);
    }

    //hakee pentueet kannasta
    public ArrayList<Litter> getLitters() {

        try ( Connection conn = this.connect();  Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Litter;");
            ArrayList<Litter> litters = new ArrayList();
            while (rs.next()) {
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
                litters.add(litter);
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

    //lisää pentueen kantaan ja kertoo, onnistuiko lisäys
    public boolean addLitterToDb(Litter litter) {
        String sql = "INSERT INTO Litter(dam, sire, litterName, establishment, birth, delivery) VALUES(?,?,?,?,?,?)";

        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //Class.forName("org.sqlite.JDBC");
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

    //Luo yhteyden kantaan
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

    //Luo pentuolion ja kutsuu sen kantaan tallentavaa metodia
    public int addKitten(int litterId, String kittenName, String sex, String birthTime, int weigth, String regno, String ems, LocalDate birth) {
        Kitten kitten = new Kitten(kittenName, sex, birthTime, regno, ems, litterId);
        int kittenId = addKittenToDb(kitten);
        if (kittenId != -1) {
            addWeight(kittenId, weigth, birth);
        }

        return kittenId;
    }

    //Tallentaa pennun kantaan ja kertoo, onnistuiko tallennus
    public int addKittenToDb(Kitten kitten) {
        String sql = "INSERT INTO Kitten(litter_id, name, sex,regNo, emsCode, birthTime) VALUES(?,?,?,?,?,?)";

        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //Class.forName("org.sqlite.JDBC");
            pstmt.setInt(1, kitten.getLitterId());
            pstmt.setString(2, kitten.getKittenName());
            pstmt.setString(3, kitten.getSex());
            pstmt.setString(4, kitten.getRegno());
            pstmt.setString(5, kitten.getEms());
            pstmt.setString(6, kitten.getBirthTime());

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

    //hakee pentueen pennut kannasta
    ArrayList<Kitten> getKittensByLitterId(int litterId) {
        String sql = "SELECT * FROM Kitten WHERE litter_id = ?";
        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, litterId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Kitten> kittens = new ArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                String kittenName = rs.getString("name");
                String sex = rs.getString("sex");
                String birthTime = rs.getString("birthTime");
                String regno = rs.getString("regNo");
                String ems = rs.getString("emsCode");

                Kitten kitten = new Kitten(kittenName, sex, birthTime, regno, ems, litterId, id);
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

    HashMap<LocalDate, Integer> getKittenWeigthsByKittenId(int kittenId) {
        String sql = "SELECT * FROM Weight WHERE kitten_id = ?";
        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, kittenId);
            ResultSet rs = pstmt.executeQuery();
            HashMap<LocalDate, Integer> weightMap = new HashMap();
            while (rs.next()) {
                String date = rs.getString("date");
                Integer weight = rs.getInt("weight");
                
                LocalDate weightDay = LocalDate.parse(date);

                weightMap.put(weightDay, weight);

            }
            rs.close();
            pstmt.close();
            conn.close();
            return weightMap;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean addWeight(int kittenId, int weight, LocalDate date) {
        String sql = "INSERT INTO Weight(kitten_id, weight, date) VALUES(?,?,?)";
        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //Class.forName("org.sqlite.JDBC");
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
    
    public Boolean removeWeight(int kittenId, LocalDate date) {
        
        String sql = "DELETE FROM Weight WHERE kitten_id = ? AND date = ?";
        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //Class.forName("org.sqlite.JDBC");
            pstmt.setInt(1, kittenId);
            pstmt.setString(2, date.toString());

            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
}
