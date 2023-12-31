package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author david
 */
public class FXMLVistaGastosController implements Initializable {

    @FXML
    private ImageView imagenPerfil;
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
    private TableView<?> tablaGastos;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private TableColumn<?, ?> categoryColumn;
    @FXML
    private TableColumn<?, ?> costColumn;
    @FXML
    private TableColumn<?, ?> dateColumn;
    @FXML
    private ComboBox<?> categoryFIlter;
    @FXML
    private ComboBox<?> monthFilter;
    @FXML
    private ComboBox<?> yearFilter;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}