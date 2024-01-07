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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;

/**
 * FXML Controller class
 *
 * @author nicogalvez
 */
public class FXMLVistaPerfilController implements Initializable {

    @FXML
    private ImageView profileImage;
    @FXML
    private ComboBox<String> eleccionPerfilCombo;
    @FXML
    private Button imageButton;
    @FXML
    private Button logOutButton;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField correField;
    @FXML
    private Text errorCorreo;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField usuarioField;
    @FXML
    private Text errorUsuario;
    @FXML
    private PasswordField contrasenyaField;
    @FXML
    private Text errorContrasenya;
    @FXML
    private PasswordField contrasenyaConfirmField;
    @FXML
    private Text errorConfirmacion;
    @FXML
    private Button cancelarBoton;
    
    private Image imagenPerfil;
    
    private boolean usuarioIniciado = true;
    @FXML
    private Button modCuentaBoton;
    @FXML
    private Label errorNombre;
    @FXML
    private Label errorApellido;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            imagenPerfil = Acount.getInstance().getLoggedUser().getImage();
        } catch (AcountDAOException | IOException ex) {
            Logger.getLogger(FXMLVistaPerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
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
        
        usuarioField.setDisable(Boolean.TRUE);
        
        cancelarBoton.setOnAction(v -> {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación de cancelar");
            alerta.setHeaderText(null);
            alerta.setContentText("Al salir de esta venta, no se guardarán los datos que hayas modificado.");
            ((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/resources/icono-aplicacion.png"));
            
            String css = this.getClass().getResource("alertas-estilos.css") .toExternalForm();
            alerta.getDialogPane().getStylesheets().add(css);
            
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
            if(!checkPassword(newV)){
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
        
        nombreField.focusedProperty().addListener((ob, oldV, newV)->{
            if(newV){
                errorNombre.setText("");
            }
        });
        
        apellidosField.focusedProperty().addListener((ob, oldV, newV)->{
            if(newV){
                errorApellido.setText("");
            }
        });
                
        modCuentaBoton.setOnAction(v->{
            try {
                if(User.checkEmail(correField.getText())
                &&User.checkNickName(usuarioField.getText())
                &&checkPassword(contrasenyaField.getText())
                &&contrasenyaField.getText().equals(contrasenyaConfirmField.getText())
                && !nombreField.getText().isEmpty() && !apellidosField.getText().isEmpty()){
                    Acount.getInstance().getLoggedUser().setImage(imagenPerfil);
                    Acount.getInstance().getLoggedUser().setName(nombreField.getText());
                    Acount.getInstance().getLoggedUser().setSurname(apellidosField.getText());
                    Acount.getInstance().getLoggedUser().setEmail(correField.getText());
                    Acount.getInstance().getLoggedUser().setPassword(contrasenyaField.getText());
                    modCuentaBoton.getScene().getWindow().hide();
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("Modificado correctamente");
                    alerta.setHeaderText(null);
                    alerta.setContentText("Se han guardado los datos del usuario " + usuarioField.getText() + " correctamente.");
                    ((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/resources/icono-aplicacion.png"));
                    
                    String css = this.getClass().getResource("alertas-estilos.css") .toExternalForm();
                    alerta.getDialogPane().getStylesheets().add(css);
                    
                    alerta.show();
                    
                    
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
                    
                    if(!checkPassword(contrasenyaField.getText())){
                        if(contrasenyaField.getText().equals("")){
                            errorContrasenya.setText("Debe tener más de 6 caracteres alphanuméricos");
                        }
                    }
                    
                    if(!contrasenyaField.getText().equals(contrasenyaConfirmField.getText())){
                        errorConfirmacion.setText("Contraseña diferente");
                        
                    }
                    
                    if(nombreField.getText().isEmpty()){
                        errorNombre.setText("Introduzca su nombre");
                    }
                    
                    if(apellidosField.getText().isEmpty()){
                        errorApellido.setText("Introduzca sus apellidos");
                    }
                }
                
            } catch (AcountDAOException | IOException ex) {
                Logger.getLogger(FXMLVistaRegistroController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    void setUsuario(User u){
        nombreField.setText(u.getName());
        apellidosField.setText(u.getSurname());
        correField.setText(u.getEmail());
        usuarioField.setText(u.getNickName());
        contrasenyaField.setText(u.getPassword());
        contrasenyaConfirmField.setText(u.getPassword());
        profileImage.setImage(u.getImage());
    }
    
    @FXML
    //Ya funciona jeje
    private void pickImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
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

    @FXML
    private void logOut(ActionEvent event) throws AcountDAOException, IOException {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación de Cerrar Sesión");
            alerta.setHeaderText(null);
            alerta.setContentText("¿Estás seguro de que quieres Cerrar Sesión?");
            
            String css = this.getClass().getResource("alertas-estilos.css") .toExternalForm();
            alerta.getDialogPane().getStylesheets().add(css);
            ((Stage) alerta.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/resources/icono-aplicacion.png"));
            
            Optional<ButtonType> aceptar = alerta.showAndWait();
            if(aceptar.isPresent()){
                if (aceptar.get() == ButtonType.OK) {
                    usuarioIniciado = false;
                    Acount.getInstance().logOutUser();
                    logOutButton.getScene().getWindow().hide();
                }
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
    
    public static boolean checkPassword(String cadena) {
        // Verifica la longitud mínima de seis caracteres
        if (cadena.length() < 6) {
            return false;
        }

        // Verifica si la cadena contiene al menos una letra y un número
        boolean contieneLetra = false;
        boolean contieneNumero = false;

        for (char caracter : cadena.toCharArray()) {
            if (Character.isLetter(caracter)) {
                contieneLetra = true;
            } else if (Character.isDigit(caracter)) {
                contieneNumero = true;
            }

            // Si ya se encontraron una letra y un número, se puede salir del bucle
            if (contieneLetra && contieneNumero) {
                break;
            }
        }

        return contieneLetra && contieneNumero;
    }
    
    boolean getUsuarioIniciado() {
        return usuarioIniciado;
    }
    
}