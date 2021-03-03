/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import Entite.Adherent;
import connectsql.Database;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.SerAdherent;

public class GestionUtilisateursController implements Initializable {

    @FXML
    private TableView<Adherent> tableUser;

    @FXML
    private TableColumn<Adherent, Integer> coloneId;

    @FXML
    private TableColumn<Adherent, String> coloneNom;

    @FXML
    private TableColumn<Adherent, String> colonePrenom;

    @FXML
    private TableColumn<Adherent, String> coloneEmail;

    @FXML
    private TableColumn<Adherent, String> colonePass;

    @FXML
    private TableColumn<Adherent, Integer> coloneAge;

    @FXML
    private TableColumn<Adherent, Blob> coloneImage;

    @FXML
    private TableColumn<Adherent, Integer> coloneTel;
    @FXML
    private TableColumn<Adherent, String> editCol;
    @FXML
    private Button add;
    @FXML
    private Button refresh;
    String query = null;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Adherent adh = null;
    ObservableList<Adherent> listM = FXCollections.observableArrayList();

    int index = -1;

    public void initialize(URL url, ResourceBundle rb) {
        con = Database.ConnectDb();
        loadDate();
        
    }
    
       @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addUser() {

        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/Admin/AddUSer.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Admin/AddUser.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
         */
    }

    @FXML
    private void refreshTable() {

        try {
            listM.clear();
            query = "select * from Adherent";
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idad");
                String prenom = rs.getString(2);
                String nom = rs.getString(3);
                String email = rs.getString(4);
                String password = rs.getString(5);
                int age = rs.getInt("age");
                Blob userImage = rs.getBlob(7);
                int phone = rs.getInt("phone");
                listM.add(new Adherent(id, prenom, nom, email, password, age, userImage, phone));
                tableUser.setItems(listM);
            }
        } catch (Exception e) {

        }
    }

    private void loadDate() {
        con = Database.ConnectDb();
        refreshTable();
        coloneId.setCellValueFactory(new PropertyValueFactory<Adherent, Integer>("idad"));
        coloneNom.setCellValueFactory(new PropertyValueFactory<Adherent, String>("lastName"));
        colonePrenom.setCellValueFactory(new PropertyValueFactory<Adherent, String>("firstName"));
        coloneEmail.setCellValueFactory(new PropertyValueFactory<Adherent, String>("email"));
        colonePass.setCellValueFactory(new PropertyValueFactory<Adherent, String>("password"));
        coloneAge.setCellValueFactory(new PropertyValueFactory<Adherent, Integer>("age"));
        coloneImage.setCellValueFactory(new PropertyValueFactory<Adherent, Blob>("userImage"));
        coloneTel.setCellValueFactory(new PropertyValueFactory<Adherent, Integer>("phone"));

        Callback<TableColumn<Adherent, String>, TableCell<Adherent, String>> cellFoctory = (TableColumn<Adherent, String> param) -> {
            // make cell containing buttons
            final TableCell<Adherent, String> cell = new TableCell<Adherent, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                adh = tableUser.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `adherent` WHERE idad  =" + adh.getIdad();
                                con = Database.ConnectDb();
                                pst = con.prepareStatement(query);
                                pst.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(GestionUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            adh = tableUser.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/Admin/AddUSer.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(GestionUtilisateursController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddUSerController addUserController = loader.getController();
                            addUserController.setUpdate(true);
                            addUserController.setTextField(adh.getIdad(), adh.getFirstName(), adh.getLastName(), adh.getEmail(), adh.getPassword(), adh.getAge(), adh.getPhone(), adh.getUserImage());

                           

                            System.out.println(adh.getUserImage());

                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        tableUser.setItems(listM);
    }

}
