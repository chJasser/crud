/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LoginAndLsignUpSystem;

import connectsql.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import Entite.Adherent;
import accueil.AcceuilController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javax.imageio.ImageIO;
import profil.ProfilFxmlController;
import services.SerAdherent;

public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextField nom;
    
    @FXML
    private TextField prenom;
    
    @FXML
    private TextField emailSignup;
    
    @FXML
    private PasswordField passSignup1;
    
    @FXML
    private PasswordField passSignup2;
    
    @FXML
    private Button connecSignin;
    
    @FXML
    private TextField email;
    
    @FXML
    private PasswordField mdps;
    
    @FXML
    private Button connexion;
    @FXML
    private AnchorPane AnchorSingup;
    
    @FXML
    private AnchorPane AnchorSingin;
    
    @FXML
    private Button connecter;
    
    @FXML
    private Button inscrire;
    @FXML
    private ImageView close;
    
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    private boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("validate email");
            alert.setHeaderText(null);
            alert.setContentText("please enter valid email");
            alert.showAndWait();
            email.setText("");
            return false;
        }
    }
    
    private boolean validateEmail1() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(emailSignup.getText());
        if (m.find() && m.group().equals(emailSignup.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("validate email");
            alert.setHeaderText(null);
            alert.setContentText("please enter valid email");
            alert.showAndWait();
            emailSignup.setText("");
            return false;
        }
    }
    
    @FXML
    private void login(ActionEvent event) throws Exception {
        con = Database.ConnectDb();
        String sql = "select * from adherent where email= ? and password = ?";
        List<Adherent> ls = new ArrayList<Adherent>();
        SerAdherent sp = new SerAdherent();
        ls = sp.read();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, email.getText());
            pst.setString(2, mdps.getText());
            rs = pst.executeQuery();
            if (validateEmail()) {
                if (rs.next()) {
                    
                    for (Adherent e : ls) {
                        if (e.getEmail().equals(email.getText())) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profil/profil.fxml"));
                            ProfilFxmlController controller = new ProfilFxmlController("itachi", "uchiha");
                            // Set it in the FXMLLoader
                            loader.setController(controller);
                            BorderPane root = loader.load();
                            controller = loader.getController();
                            controller.setEmail(e.getEmail());
                            // Get the Controller from the FXMLLoader

                            // Set data in the controller
                            controller.setFirstName(e.getFirstName());
                            controller.setLastName(e.getLastName());
                            FXMLLoader fx = new FXMLLoader();
                            fx.setLocation(getClass().getResource("/accueil/FXML.fxml"));
                            AcceuilController Ac = new AcceuilController();
                            Ac.getEmail(e.getEmail());
                            // System.out.println(id);
                        }
                        // System.out.println(id);
                    }
                    
                    JOptionPane.showMessageDialog(null, "email and password is correct");
                    connecter.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("/accueil/FXML.fxml"));
                    Stage mainStage = new Stage();
                    Scene scene = new Scene(root);
                    mainStage.setScene(scene);
                    mainStage.show();
                    
                } else {
                    JOptionPane.showMessageDialog(null, "incorrecte email or password");
                }
                
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    private boolean validateFields() {
        if (nom.getText().isEmpty() | prenom.getText().isEmpty() | emailSignup.getText().isEmpty() | passSignup1.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("validate  Fildes");
            alert.setHeaderText(null);
            alert.setContentText("please enter all info");
            alert.showAndWait();
            
            return false;
        }
        return true;
    }
    
    private boolean password() {
        if (!passSignup1.getText().equals(passSignup2.getText())) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("validate  password");
            alert.setHeaderText(null);
            alert.setContentText("passwords must match");
            passSignup1.setText("");
            passSignup2.setText("");
            alert.showAndWait();
            
            return false;
        }
        return true;
    }
    
    private boolean validatePassword() {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,154})");
        Matcher m = p.matcher(passSignup1.getText());
        if (m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("validate  password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one(Digital,LowerCase,UperCase and special Character)and length must be between 6-15");
            passSignup1.setText("");
            passSignup2.setText("");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean firstNameValidation() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(prenom.getText());
        if (m.find() && m.group().equals(prenom.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("validate  firstName");
            alert.setHeaderText(null);
            alert.setContentText("please enter a valid firsName");
            prenom.setText("");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean lastNameValidation() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(nom.getText());
        if (m.find() && m.group().equals(nom.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("validate  firstName");
            alert.setHeaderText(null);
            alert.setContentText("please enter a valid lastName");
            nom.setText("");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean emailExist() throws SQLException {
        List<Adherent> ls = new ArrayList<Adherent>();
        SerAdherent sp = new SerAdherent();
        ls = sp.read();
        for (Adherent e : ls) {
            if (e.getEmail().equals(emailSignup.getText())) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("validate  email");
                alert.setHeaderText(null);
                alert.setContentText("email deja existe");
                passSignup1.setText("");
                passSignup2.setText("");
                alert.showAndWait();
                emailSignup.setText("");
                return true;
            }
        }
        System.out.println(ls);
        return false;
    }
    
    @FXML
    private void ajouterpersonne(ActionEvent event) {
        try {
            con = Database.ConnectDb();
            FileInputStream fin = new FileInputStream("C:/Users/jasser/Documents/NetBeansProjects/JavaFXApplication3/src/img/user.png");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for (int readNum; (readNum = fin.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            photo = bos.toByteArray();
            String query = "INSERT INTO `adherent`(`firstName`, `lastName`, `email`, `password`,`userImage`) VALUES (?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, prenom.getText());
            pst.setString(2, nom.getText());
            pst.setString(3, emailSignup.getText());
            pst.setString(4, passSignup1.getText());
            pst.setBytes(5, photo);

            // Adherent p = new Adherent(nom.getText(), prenom.getText(), emailSignup.getText(), passSignup1.getText(),photo);
            if (validateFields()) {
                if (lastNameValidation()) {
                    if (firstNameValidation()) {
                        if (validateEmail1()) {
                            if (!emailExist()) {
                                if (validatePassword()) {
                                    if (password()) {
                                        pst.execute();
                                        AnchorSingin.toFront();
                                        AnchorSingup.toBack();
                                        email.setText(emailSignup.getText());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == connecter) {
            AnchorSingin.toFront();
        } else if (event.getSource() == inscrire) {
            AnchorSingup.toFront();
        }
    }
    
    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    byte[] photo = null;
}
