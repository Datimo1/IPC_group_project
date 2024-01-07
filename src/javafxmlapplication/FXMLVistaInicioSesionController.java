/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

/**
 * FXML Controller class
 *
 * @author nicogalvez
 */
public class FXMLVistaInicioSesionController implements Initializable {

    @FXML
    private Button registroButton;
    @FXML
    private TextField nicknameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Button iniciarSesionButton;
    @FXML
    private Label nicknameErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    
    private boolean usuarioIniciado = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciarSesionButton.disableProperty().bind(
                Bindings.or(nicknameTextField.textProperty().isEmpty()
                        , passwordTextField.textProperty().isEmpty()));
    }    

    @FXML
    private void registrarse(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLVistaRegistro.fxml"));
        Parent root = loader.load();
        FXMLVistaRegistroController controller = loader.getController();
        
        Scene registroScene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/resources/icono-aplicacion.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(registroScene);
        stage.setTitle("Registrarse");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.showAndWait();
    }

    @FXML
    private void iniciarSesion(ActionEvent event) throws AcountDAOException, IOException {
        if(Acount.getInstance().logInUserByCredentials(nicknameTextField.getText(), 
                                                            passwordTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATION
            alert.setTitle("Inicio sesión");
            alert.setHeaderText("Sesión iniciada");
                // ó null si no queremos cabecera
            alert.setContentText(null);
            
            String css = this.getClass().getResource("alertas-estilos.css") .toExternalForm();
            alert.getDialogPane().getStylesheets().add(css);
            
            alert.showAndWait();
            
            usuarioIniciado = true;
            ((Stage)iniciarSesionButton.getScene().getWindow()).close();
                
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
                // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATION
            alert.setTitle("Inicio sesión");
            String tipoError;
            if(!Acount.getInstance().existsLogin(nicknameTextField.getText())){
                tipoError = "Usuario no registrado";
                nicknameErrorLabel.visibleProperty().set(true);
                passwordErrorLabel.visibleProperty().set(false);
                nicknameTextField.setText("");
                passwordTextField.setText("");
            }else{
                tipoError = "Contraseña incorrecta";
                nicknameErrorLabel.visibleProperty().set(false);
                passwordErrorLabel.visibleProperty().set(true);
                passwordTextField.setText("");
            }
            alert.setHeaderText(tipoError);
                // ó null si no queremos cabecera
            alert.setContentText(null);
            String css = this.getClass().getResource("alertas-estilos.css") .toExternalForm();
            alert.getDialogPane().getStyleClass().add("error");
            alert.getDialogPane().getStylesheets().add(css);
            alert.showAndWait();
            
              
        }
    }

    boolean getUsuarioIniciado() {
        return usuarioIniciado;
    }
    
}
