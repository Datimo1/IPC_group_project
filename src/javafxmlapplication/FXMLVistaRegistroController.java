/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author nicogalvez
 */
public class FXMLVistaRegistroController implements Initializable {

    @FXML
    private ImageView profileImage;
    @FXML
    private Button imageButton;
    @FXML
    private ComboBox<String> eleccionPerfilCombo;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField correField;
    @FXML
    private TextField apellidosField;
    @FXML
    private PasswordField contrasenyaField;
    @FXML
    private PasswordField contrasenyaConfirmField;
    @FXML
    private Button cancelarBoton;
    @FXML
    private Button crearCuentaBoton;
    @FXML
    private TextField usuarioField;
    
    private Image imagenPerfil;
    @FXML
    private Text errorUsuario;
    @FXML
    private Text errorContrasenya;
    @FXML
    private Text errorConfirmacion;
    @FXML
    private Text errorCorreo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        eleccionPerfilCombo.getItems().addAll("/resources/perfil-1-empresario.png",
                                   "/resources/perfil-2-ingeniero.png",
                                   "/resources/perfil-3-medico.png");
        eleccionPerfilCombo.setCellFactory(c->new ComboCelda());
        eleccionPerfilCombo.setButtonCell(new ComboCelda());
        
        eleccionPerfilCombo.valueProperty().addListener((obv, oldV, newV)->{
            if(newV != null){
                imagenPerfil = new Image(newV);
                profileImage.setImage(imagenPerfil);
            }
        });
        
        cancelarBoton.setOnAction(v -> {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación de cancelar");
            alerta.setHeaderText(null);
            alerta.setContentText("Al salir de esta venta, se borrarán todos los datos introducidos.");

            Optional<ButtonType> aceptar = alerta.showAndWait();
            if(aceptar.isPresent()){
                if (aceptar.get() == ButtonType.OK) {
                    cancelarBoton.getScene().getWindow().hide();
                }
            }
        });
        
        usuarioField.textProperty().addListener((ob, oldV, newV) -> {
            if(newV.contains(" ")){
                usuarioField.setText(oldV);
                errorUsuario.setText("No puede contener espacio");
                
            }else{
                errorUsuario.setText("");
            }
        });
        
        contrasenyaField.textProperty().addListener((ob, oldV, newV) -> {
            if(!User.checkPassword(newV)){
                errorContrasenya.setText("Debe tener más de 6 caracteres alphanuméricos");
            }else{
                errorContrasenya.setText("");
            }
        });
        
        contrasenyaConfirmField.textProperty().addListener((ob, oldV, newV) -> {
            if(!newV.equals(contrasenyaField.getText())){
                errorConfirmacion.setText("Contraseña diferente");
                
            }else{
                errorConfirmacion.setText("");
            }
        });
        
        correField.textProperty().addListener((ob, oldV, newV) -> {
            if(!User.checkEmail(newV)){
                errorCorreo.setText("Correo no válido");
                
                
            }else{
                errorCorreo.setText("");
            }
        });
                
        crearCuentaBoton.setOnAction(v->{
            try {
                if(User.checkEmail(correField.getText())
                &&User.checkNickName(usuarioField.getText())
                &&User.checkPassword(contrasenyaField.getText())
                &&contrasenyaField.getText().equals(contrasenyaConfirmField.getText())){
                    Acount.getInstance().registerUser(nombreField.getText(), apellidosField.getText(),
                        correField.getText(), usuarioField.getText(), contrasenyaField.getText(),
                        imagenPerfil, LocalDate.now());
                }else{
                    if(!User.checkEmail(correField.getText())){
                        if(usuarioField.getText().equals("")){
                            errorCorreo.setText("Introduzca un correo");
                        }else{
                            errorCorreo.setText("Correo no válido");
                        }
                    }
                    
                    if(!User.checkNickName(usuarioField.getText())){
                        if(usuarioField.getText().equals("")){
                            errorUsuario.setText("Introduzca un usuario");
                        }else{
                            errorUsuario.setText("Usuario ocupado");
                        }
                        
                    }
                    
                    if(!User.checkPassword(contrasenyaField.getText())){
                        if(contrasenyaField.getText().equals("")){
                            errorUsuario.setText("Debe tener más de 6 caracteres");
                        }
                    }
                    
                    if(!contrasenyaField.getText().equals(contrasenyaConfirmField.getText())){
                        errorConfirmacion.setText("Contraseña diferente");
                        
                    }
                }
                
            } catch (AcountDAOException ex) {
                Logger.getLogger(FXMLVistaRegistroController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaRegistroController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        Stage stage = (Stage) imageButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Procesar el archivo seleccionado (puedes hacer lo que necesites con él)
        if (selectedFile != null) {
            System.out.println("Imagen seleccionada: " + selectedFile.getAbsolutePath());
            eleccionPerfilCombo.setValue(null);
            // Cargar la imagen en el ImageView
            imagenPerfil = new Image(selectedFile.toURI().toString());
            profileImage.setImage(imagenPerfil);
        }
    }

    class ComboCelda extends ComboBoxListCell<String>{

        @Override
        public void updateItem(String t, boolean bln) {
            super.updateItem(t, bln); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if (bln||t==null) {
                setText(null);
                setGraphic(null);
            } else {
                Image image = new Image(t, 25, 25, true, true);
                ImageView imView = new ImageView(image);
                setGraphic(imView);
                setText(null);
                
            }
        }
        
    }
    
}
