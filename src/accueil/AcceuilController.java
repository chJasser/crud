/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accueil;

import Admin.GestionUtilisateursController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import profil.ProfilFxmlController;

/**
 * FXML Controller class
 *
 * @author jasser
 */
public class AcceuilController implements Initializable {

    @FXML
    private Button profil;
    private static String emailSinIn;

    @FXML
    void onClickProfil(ActionEvent event) throws IOException, SQLException {
        if (emailSinIn.equals("chaieb.jasser@esprit.tn")) {
            Parent root = FXMLLoader.load(getClass().getResource("/Admin/gestionUtilisateurs.fxml"));
            Stage mainStage = new Stage();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        }
        else {
           Parent root = FXMLLoader.load(getClass().getResource("/profil/profil.fxml"));
            Stage mainStage = new Stage();
            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void getEmail(String e) {
        System.out.println(e);
        this.emailSinIn = e;

    }
}
