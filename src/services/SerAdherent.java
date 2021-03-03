package services;

import connectsql.Database;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import Entite.Adherent;
import Intservice.AdhService;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SerAdherent implements AdhService<Adherent> {

    Connection cnx;

    public SerAdherent() {
        cnx = Database.ConnectDb();
    }

    @Override
    public void add(Adherent p) throws SQLException {

        Statement st = cnx.createStatement();
        //    String req = "insert into personne values ("+p.getId()+" , " +p.getNom()+ ", " +p.getPrenom() +")";
        String req = " insert into adherent (firstName,lastName,email,password) values ('" + p.getFirstName() + " ', '" + p.getLastName() + "' , '" + p.getEmail() + "' , '" + p.getPassword() + "')";
        st.executeUpdate(req);

    }

    @Override
    public List<Adherent> read() throws SQLException {
        List<Adherent> ls = new ArrayList<Adherent>();
        Statement st = cnx.createStatement();
        String req = "select * from Adherent";
        ResultSet rs = st.executeQuery(req);

        while (rs.next()) {
            int id = rs.getInt("idad");
            String prenom = rs.getString(2);
            String nom = rs.getString(3);
            String email = rs.getString(4);
            String password = rs.getString(5);
            int age = rs.getInt("age");
            Blob userImage = rs.getBlob(7);
            int phone = rs.getInt("phone");
            Adherent p = new Adherent(id, prenom, nom, email, password, age, userImage, phone);
            ls.add(p);
            //  System.out.println( id + ", " + nom + ", " + prenom);
        }
        return ls;

    }
    @Override
    public void update(Adherent t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Adherent t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
