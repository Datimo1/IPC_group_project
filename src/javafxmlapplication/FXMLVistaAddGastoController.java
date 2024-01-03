/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;

/**
 * FXML Controller class
 *
 * @author nicogalvez
 */
public class FXMLVistaAddGastoController implements Initializable {

    @FXML
    private Button newCatButton;
    @FXML
    private Button facturaButton;
    @FXML
    private ImageView profileImage;
    
    private Image facturaImagen = null;
    @FXML
    private TextField tituloField;
    @FXML
    private DatePicker fechaSelector;
    @FXML
    private ComboBox<Category> categoriaCombox;
    @FXML
    private TextField costeField;
    @FXML
    private Button addButton;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField unidadesField;
    @FXML
    private Text tituloError;
    @FXML
    private Text fechaError;
    @FXML
    private Text descripcionError;
    @FXML
    private Text categoriaError;
    @FXML
    private Text costeError;
    @FXML
    private Text unidadesError;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addButton.setOnAction((ev) -> {

            try {
                if (!tituloField.getText().isEmpty() && fechaSelector.getValue() != null
                        && categoriaCombox.getValue() != null && !costeField.getText().isEmpty()
                        && !descriptionArea.getText().isEmpty() && !unidadesField.getText().isEmpty()) {
                    Acount.getInstance().registerCharge(tituloField.getText(), descriptionArea.getText(),
                            Double.parseDouble(costeField.getText()), Integer.parseInt(unidadesField.getText()),
                            facturaImagen, fechaSelector.getValue(), categoriaCombox.getValue());
                }else{
                    //completar: mensajes de error
                }
                
                

            } catch (AcountDAOException ex) {
                Logger.getLogger(FXMLVistaAddGastoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaAddGastoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        costeField.textProperty().addListener((ob, oldV, newV)->{
            if(!newV.matches("\\d*\\.?\\d+")){
                costeField.setText(oldV);
            }
        });
        
        unidadesField.textProperty().addListener((ob, oldV, newV)->{
            if(!newV.matches("\\d+")){
                costeField.setText(oldV);
            }
//            
//            if(newV.contains(".")){
//                costeField.setText(oldV);
//            }
        });
        
        
    }
    
    @FXML
    //Ya funciona jeje
    private void pickImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.gif"),
                new ExtensionFilter("Todos los archivos", "*.*")
        );

        // Mostrar el diálogo de selección de archivos
        Stage stage = (Stage) facturaButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Procesar el archivo seleccionado (puedes hacer lo que necesites con él)
        if (selectedFile != null) {
            System.out.println("Imagen seleccionada: " + selectedFile.getAbsolutePath());
            // Cargar la imagen en el ImageView
            facturaImagen = new Image(selectedFile.toURI().toString());
            profileImage.setImage(facturaImagen);
        }
    }

    @FXML
    private void createCategory(ActionEvent event) {
        //Faltaría añadir a la lista de categorías que se meten al comboBox
        TextInputDialog dialog = new TextInputDialog(null); // texto por defecto
        dialog.setTitle("Crear nueva categoría");
        dialog.setHeaderText(null);
        dialog.setContentText("Introduzca el nombre de la categoría:");
        Optional<String> result = dialog.showAndWait();
        
        // ESTO HAY QUE CAMBIARLO
        // Obteniendo el resultado con una lambda
        result.ifPresent(name -> System.out.println("Hola " + name));
    }
    
}
