/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import model.Acount;
import model.AcountDAOException;

/**
 *
 * @author Jiaxiang Jin
 */
public class FXMLVistaNuevaCategoriaController implements Initializable {

    @FXML
    private TextField tituloField;
    @FXML
    private Text tituloError;
    
    @FXML
    private Text descripcionError;
    @FXML
    private Button cancelarButton;
    @FXML
    private Button anyadirButton;
    @FXML
    private TextArea descripcionArea;
    
    private boolean anyadido = false;
    
    
    public void initialize(URL url, ResourceBundle rb) {
        //EventHandler:
        anyadirButton.setOnAction((ev)->{
            if(!tituloField.getText().isEmpty() && !descripcionArea.getText().isEmpty()){
                try {
                    Acount.getInstance().registerCategory(tituloField.getText(), descripcionArea.getText());
                    anyadido = true;
                    anyadirButton.getScene().getWindow().hide();
                } catch (AcountDAOException ex) {
                    //Logger.getLogger(FXMLVistaNuevaCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
                    tituloError.setText("Ya existe esta categoría");
                } catch (IOException ex) {
                    Logger.getLogger(FXMLVistaNuevaCategoriaController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                if (descripcionArea.getText().isEmpty()) {
                        descripcionError.setText("Introduzca una descripción");
                    }
                
                if(tituloField.getText().isEmpty()){
                        tituloError.setText("Introduzca un título");
                    }
            }
        });
                
        cancelarButton.setOnAction((ev)->{
            cancelarButton.getScene().getWindow().hide();
        });
        
        //-----------------------------------------------------------------------------------------------------------------------------------
        //Listener:
        tituloField.focusedProperty().addListener((ob, oldV, newV)->{
            if(newV){
                tituloError.setText("");
            }
        });
        
        descripcionArea.focusedProperty().addListener((ob, oldV, newV)->{
            if(newV){
                descripcionError.setText("");
            }
        });
        
        //-----------------------------------------------------------------------------------------------------------------------------------
        //Configurar el tituloField (empieza por mayúscula)
        // Crear un UnaryOperator para modificar el texto
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change; // No hacer cambios si el texto está vacío
            }

            // Convertir la primera letra a mayúscula y mantener el resto del texto
            change.setText(newText.substring(0, 1).toUpperCase() + newText.substring(1));
            change.setRange(0, change.getControlText().length()); // Seleccionar todo el texto
            return change;
        };

        // Aplicar el UnaryOperator al TextFormatter del TextField
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        tituloField.setTextFormatter(textFormatter);
    }
    
    //----------------------------------------------------------------------------------------------------------------------------------------
    //Métodos:
    protected String getTitulo(){
        return tituloField.getText();
    }

    protected boolean isAnyadido() {
        return anyadido;
    }
    
    
}
