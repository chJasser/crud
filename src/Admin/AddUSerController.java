package Admin;

import Entite.Adherent;
import LoginAndLsignUpSystem.JavaFXApplication3;

import connectsql.Database;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import services.SerAdherent;

public class AddUSerController implements Initializable {

    @FXML
    private Button browse;
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField addresse;
    @FXML
    private TextField mdps;
    @FXML
    private TextField ag;
    @FXML
    private TextField tel;
    @FXML
    private Button add;
    @FXML
    private Button annuler;
    String query = null;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Adherent adh = null;
    private JFileChooser fileChooser;
    String filename = null;
    @FXML
    private ImageView img;
    int adhId;
    private boolean update;

    @FXML
    private void UploadImageActionPerformed(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
            img.setImage(image);
            img.setFitHeight(1200);
            img.setFitHeight(300);
            img.scaleXProperty();
            img.rotateProperty();
            img.setRotate(270);
            img.setSmooth(true);
            img.setCache(true);

            FileInputStream fin = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            for (int readNum; (readNum = fin.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
            photo = bos.toByteArray();

        } catch (IOException ex) {
            Logger.getLogger("ss");
        }
    }

    @Override

    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) annuler.getScene().getWindow();

        stage.close();
    }

    private boolean emailExist() throws SQLException {
        List<Adherent> ls = new ArrayList<Adherent>();
        SerAdherent sp = new SerAdherent();
        ls = sp.read();
        for (Adherent e : ls) {
            if (e.getEmail().equals(addresse.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("validate  email");
                alert.setHeaderText(null);
                alert.setContentText("email deja existe");
                addresse.setText("");

                return true;
            }
        }
        System.out.println(ls);
        return false;
    }

    private boolean validatePassword() {
        Pattern p = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,154})");
        Matcher m = p.matcher(mdps.getText());
        if (m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("validate  password");
            alert.setHeaderText(null);
            alert.setContentText("Password must contain at least one(Digital,LowerCase,UperCase and special Character)and length must be between 6-15");
            mdps.setText("");
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("validate  firstName");
            alert.setHeaderText(null);
            alert.setContentText("please enter a valid lastName");
            nom.setText("");
            alert.showAndWait();
            return false;
        }
    }

    private boolean validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(addresse.getText());
        if (m.find() && m.group().equals(addresse.getText())) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("validate email");
            alert.setHeaderText(null);
            alert.setContentText("please enter valid email");
            alert.showAndWait();
            addresse.setText("");
            return false;
        }
    }

    @FXML
    void save(ActionEvent event) {
        con = Database.ConnectDb();
        String firstName = prenom.getText();
        String lastName = nom.getText();
        String email = addresse.getText();
        String password = mdps.getText();
        String age = ag.getText();
        String phone = tel.getText();
        Image image = img.getImage();
        System.out.println();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || image == null || image.isError() || age.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("please fill all filed");
            alert.showAndWait();
        } else {
            try {
                if (lastNameValidation()) {
                    if (firstNameValidation()) {
                        if (validateEmail()) {
                            if (!emailExist()) {
                                if (validatePassword()) {
                                    getQuery();
                                    insert();
                                    Stage stage = (Stage) add.getScene().getWindow();
                                    stage.close();
                                }
                            }
                        }

                    }

                }

            } catch (SQLException ex) {
                Logger.getLogger(AddUSerController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    private void getQuery() {
        if (update == false) {
            query = "INSERT INTO `adherent`(`firstName`, `lastName`, `email`, `password`, `age`, `userImage`, `phone`) VALUES (?,?,?,?,?,?,?)";
        } else {
            query = "UPDATE `adherent` SET "
                    + "`firstName`=?,"
                    + "`lastName`=?,"
                    + "`email`=?,"
                    + "`password`=?,"
                    + "`age`=?,"
                    + "`userImage`=?,"
                    + "`phone`= ? WHERE idad = '" + adhId + "'";
        }

    }

    private void insert() {
        try {

            pst = con.prepareStatement(query);
            pst.setString(1, prenom.getText());
            pst.setString(2, nom.getText());
            pst.setString(3, addresse.getText());
            pst.setString(4, mdps.getText());
            pst.setInt(5, Integer.parseInt(ag.getText()));
            pst.setBytes(6, photo);
            pst.setInt(7, Integer.parseInt(tel.getText()));
            pst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddUSerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    byte[] photo = null;

    void setTextField(int idad, String firstName, String lastName, String email, String password, int age, int phone, Blob userImage) {
        adhId = idad;
        prenom.setText(firstName);
        nom.setText(lastName);
        addresse.setText(email);
        mdps.setText(password);
        ag.setText(String.valueOf(age));
        tel.setText(String.valueOf(phone));
        try {
            int blobLength = (int) userImage.length();
            photo = userImage.getBytes(1, blobLength);
        } catch (SQLException ex) {
            Logger.getLogger(GestionUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            InputStream in = userImage.getBinaryStream();
            Image image = new Image(in);
            img.setImage(image);

        } catch (SQLException ex) {
            Logger.getLogger(AddUSerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setUpdate(boolean b) {
        this.update = b;
    }
}
