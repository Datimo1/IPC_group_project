/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

/**
 *
 * @author Jiaxiang Jin
 */
public class FXMLVistaVerGastoController implements Initializable{
    private boolean seModifico;
    private List<Category> listadoCategoria;
    private ObservableList<Category> datosListadoCategoria;
    private Image facturaImagen = null;
    
    @FXML
    private Label tituloLabel;
    @FXML
    private Text tituloError;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label fechaLabel;
    @FXML
    private Label descripcionLabel;
    @FXML
    private Label categoriaLabel;
    @FXML
    private Label costeLabel;
    @FXML
    private Label unidadesLabel;
    
    
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setCharge(Charge c){
        tituloLabel.setText(c.getName());
        fechaLabel.setText(c.getDate().toString());
        descripcionLabel.setText(c.getDescription());
        categoriaLabel.setText(c.getCategory().getName());
        costeLabel.setText(String.valueOf(c.getCost()) + " €");
        unidadesLabel.setText("Unidades: " + String.valueOf(c.getUnits()));
        
        facturaImagen = c.getImageScan();
        if(c.getImageScan() != null){
            profileImage.setImage(facturaImagen);
        }else{
            profileImage.setImage(new Image("/resources/document-empty.png"));
            System.out.println("mostrar imagen");
        }
    }

}
