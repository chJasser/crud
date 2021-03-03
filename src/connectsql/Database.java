/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectsql;

import Entite.Adherent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;

/**
 *
 * @author JAIDI
 */
public class Database {

    private static String url = "jdbc:mysql://localhost:3306/test";
    private static String user = "root";
    private static String pwd = "";

    public static Object getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Connection conn = null;

    public static Connection ConnectDb() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pwd);
            System.out.println(" connecté !!!!");

            return conn;
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }

    ;
    
    /*public static ObservableList<Adherent> getDataUseres()  {
        Connection con = ConnectDb();
        ObservableList<Adherent> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = con.prepareStatement("select * from Adherent");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Adherent(Integer.parseInt(rs.getString("idad")), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), Integer.parseInt(rs.getString("age")), rs.getString(7), Integer.parseInt(rs.getString("phone"))));
                
            }
        }catch(Exception e){
            
        }
        return list;
    }*/

};
