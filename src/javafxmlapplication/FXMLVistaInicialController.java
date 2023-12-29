package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLVistaInicialController implements Initializable {

    @FXML
    private Button readyButton;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        readyButton.setOnAction((event) -> {
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
// ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATION
        alert.setTitle("Esa es");
        alert.setHeaderText("Yes why not");
// ó null si no queremos cabecera
        alert.setContentText("Siu");
        alert.showAndWait();
        
        });
    }    
    
}
