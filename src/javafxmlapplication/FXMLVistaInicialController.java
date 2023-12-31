package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLVistaInicialController implements Initializable {

    @FXML
    private VBox categoriesContainer;
    @FXML
    private Button inicioSesionButton;
    @FXML
    private Button addChargeButton;
    @FXML
    private Button inicioButton;
    @FXML
    private Button listaButton;
    @FXML
    private Button graficasButton;
    @FXML
    private Button aPDFButton;
    @FXML
    private PieChart tartaCategorias;
    @FXML
    private ImageView imagenPerfil;
    @FXML
    private VBox menu;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // Crear una transición de escala
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), menu);
        scaleTransition.setToX(1.5);
        scaleTransition.setToY(1.0);

        
    }    

    @FXML
    private void inicioSesion(ActionEvent event) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLVistaInicioSesion.fxml"));
        Parent root = loader.load();
        FXMLVistaInicioSesionController controller = loader.getController();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Inicio Sesion");
        stage.setResizable(false);
        stage.showAndWait();
    }
    
}