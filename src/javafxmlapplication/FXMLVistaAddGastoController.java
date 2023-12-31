/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
            Image image = new Image(selectedFile.toURI().toString());
            profileImage.setImage(image);
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
