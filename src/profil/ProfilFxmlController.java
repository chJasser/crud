/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package profil;

import Entite.Adherent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import services.SerAdherent;

/**
 * FXML Controller class
 *
 * @author jasser
 */
public class ProfilFxmlController implements Initializable {

    
    private static String add;
     private static String name;
    
    
    private StringProperty firstNameString = new SimpleStringProperty();
    private StringProperty lastNameString = new SimpleStringProperty();

    public ProfilFxmlController(String firstName, String lastName) {
        firstNameString.set(firstName);
        lastNameString.set(lastName);
    }

    @FXML
    private Label email;

    @FXML
    private Label tel;

    @FXML
    private Label password;

    @FXML
    private Label age;

    @FXML
    private Label fullName;

    public void setEmail(String email) {

        this.email.setText(email);

    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        fullName.setText(firstNameString.get());
        email.setText(lastNameString.get());
    }

    public void setFirstName(String firstNameString) {
        System.out.println(firstNameString);
        this.email.setText("dazzzzzzzzzz");
    }

    public void setLastName(String lastNameString) {
        email.setText(lastNameString);
    }
}
