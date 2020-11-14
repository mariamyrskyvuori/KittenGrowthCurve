package com.maria.kittengrowthcurve;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDate;
import javafx.scene.control.MenuItem;

/**
 *
 * @author maria
 */
public class KittenService {

    public KittenService() {
    }

    //Luo pentueolion ja kutsuu metodia, joka laittaa sen kantaan
    boolean addLitter(String dam, String sire, String litterName, String establishmentDate) {
        Litter litter = new Litter(dam, sire, litterName, establishmentDate);
        litter.calculateDates();
        return addLitterToDb(litter); 
    }

    //hakee pentueet kannasta
    public ArrayList<Litter> getLitters() {

        try ( Connection conn = this.connect();  Statement stmt = conn.createStatement();) {
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Litter;" );
            ArrayList<Litter> litters = new ArrayList();
            while ( rs.next() ) {
               int id = rs.getInt("id");
               String litterName = rs.getString("litterName");
               String  dam = rs.getString("dam");
               String  sire = rs.getString("sire");
               Date establishment  = rs.getDate("establishment");
               Date birth  = rs.getDate("birth");
               Date delivery  = rs.getDate("delivery");
               
               Litter litter = new Litter(dam, sire, litterName, establishment.toLocalDate(),
                       birth.toLocalDate(), delivery.toLocalDate(), id);
               litters.add(litter);
               System.out.println( "Name: " + litterName );
               System.out.println( "Dam: " + dam );
               System.out.println( "Sire: " + sire );
               System.out.println( "Establishment: " + establishment );
               System.out.println( "Birth " + birth );
               System.out.println( "Delivery " + delivery );
               System.out.println();
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
            pstmt.setDate(4, Date.valueOf(litter.getEstablishmentDate()));
            pstmt.setDate(5, Date.valueOf(litter.getBirth()));
            pstmt.setDate(6, Date.valueOf(litter.getDeliveryDate()));

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
    boolean addKitten(int litterId, String kittenName, String sex, String birthTime, int weigth, String regno, String ems) {
        Kitten kitten = new Kitten(kittenName, sex, birthTime, weigth, regno, ems, litterId);
        
        return addKittenToDb(kitten);
    }
    
    //Tallentaa pennun kantaan ja kertoo, onnistuiko tallennus
    public boolean addKittenToDb(Kitten kitten) {
        String sql = "INSERT INTO Kitten(litter_id, name, sex, weight,regNo, emsCode, birthTime) VALUES(?,?,?,?,?,?,?)";

        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //Class.forName("org.sqlite.JDBC");
            pstmt.setInt(1, kitten.getLitterId());
            pstmt.setString(2, kitten.getKittenName());
            pstmt.setString(3, kitten.getSex());
            pstmt.setInt(4, kitten.getWeight());
            pstmt.setString(5, kitten.getRegno());
            pstmt.setString(6, kitten.getEms());
            pstmt.setString(7, kitten.getBirthTime());
            
            

            pstmt.executeUpdate();
            conn.close();
            pstmt.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } 
    }

    //hakee pentueen pennut kannasta
    ArrayList<Kitten> getKittensByLitterId(int litterId) {
        String sql = "SELECT * FROM Kitten WHERE litter_id = ?";
        try ( Connection conn = this.connect();  PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, litterId);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Kitten> kittens = new ArrayList();
            while ( rs.next() ) {
               int id = rs.getInt("id");
               String kittenName = rs.getString("name");
               String  sex = rs.getString("sex");
               String  birthTime = rs.getString("birthTime");
               int weight  = rs.getInt("weight");
               String regno  = rs.getString("regNo");
               String ems  = rs.getString("emsCode");
               
               Kitten kitten = new Kitten(kittenName, sex, birthTime, weight, regno, ems, id);
               kittens.add(kitten);
               System.out.println( "Id: " + id );
               System.out.println( "Nimi: " + kittenName );
               System.out.println( "Sukupuoli: " + sex );
               System.out.println( "Syntymäaika: " + birthTime );
               System.out.println( "Paino: " + weight );
               System.out.println( "RekNo: " + regno );
               System.out.println( "Väri: " + ems );
               System.out.println();
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
}
