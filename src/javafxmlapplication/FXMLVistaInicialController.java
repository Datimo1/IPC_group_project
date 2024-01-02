package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Acount;
import model.AcountDAOException;

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
    
    private SimpleBooleanProperty usuarioLogeado = new SimpleBooleanProperty(false);
    @FXML
    private Label nicknameLabel;
    @FXML
    private Button configuracionButton;
    @FXML
    private HBox visionSesionIniciada;
    @FXML
    private VBox vistaInicio;
    @FXML
    private HBox vistaGastos;
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
    @FXML
    private VBox vistaGraficas;
    @FXML
    private Button comparacion;
    @FXML
    private Button caregorias;
    @FXML
    private Button tendencia;
    @FXML
    private HBox comparacionVista;
    @FXML
    private ComboBox<?> selectYear;
    @FXML
    private HBox categoriasVista;
    @FXML
    private ComboBox<?> selectMonth;
    @FXML
    private HBox tendenciaVista;
    @FXML
    private ComboBox<?> selectYear2;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inicioSesionButton.visibleProperty().setValue(Boolean.TRUE);
        visionSesionIniciada.visibleProperty().setValue(Boolean.FALSE);
        
        //Bindings
        addChargeButton.disableProperty().bind(usuarioLogeado.not());
        aPDFButton.disableProperty().bind(usuarioLogeado.not());
        
        // Vistas menu izquierda
        vistaInicio.visibleProperty().setValue(Boolean.TRUE);
        vistaGastos.visibleProperty().setValue(Boolean.FALSE);
        vistaGraficas.visibleProperty().setValue(Boolean.FALSE);

        // Vistas pagina graficas
        comparacionVista.visibleProperty().setValue(Boolean.FALSE);
        categoriasVista.visibleProperty().setValue(Boolean.FALSE);
        tendenciaVista.visibleProperty().setValue(Boolean.FALSE);
    }    

    @FXML
    private void inicioSesion(ActionEvent event) throws IOException, AcountDAOException {
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
        if(controller.getUsuarioIniciado()){
            usuarioLogeado.setValue(Boolean.TRUE);
            inicioSesionButton.visibleProperty().setValue(Boolean.FALSE);
            nicknameLabel.setText(Acount.getInstance().getLoggedUser().getNickName());
            imagenPerfil.setImage(Acount.getInstance().getLoggedUser().getImage());
            visionSesionIniciada.visibleProperty().setValue(Boolean.TRUE);
        }
    }

    @FXML
    private void configuracion(ActionEvent event) {
    }

    @FXML
    private void cambiarVistaInicio(ActionEvent event) {
        vistaInicio.visibleProperty().setValue(Boolean.TRUE);
        vistaGastos.visibleProperty().setValue(Boolean.FALSE);
        vistaGraficas.visibleProperty().setValue(Boolean.FALSE);
    }

    @FXML
    private void cambiarVistaGastos(ActionEvent event) {
        vistaInicio.visibleProperty().setValue(Boolean.FALSE);
        vistaGastos.visibleProperty().setValue(Boolean.TRUE);
        vistaGraficas.visibleProperty().setValue(Boolean.FALSE);
    }

    @FXML
    private void cambiarVistaGraficas(ActionEvent event) {
        vistaInicio.visibleProperty().setValue(Boolean.FALSE);
        vistaGastos.visibleProperty().setValue(Boolean.FALSE);
        vistaGraficas.visibleProperty().setValue(Boolean.TRUE);
        
        comparacionVista.visibleProperty().setValue(Boolean.TRUE);
        categoriasVista.visibleProperty().setValue(Boolean.FALSE);
        tendenciaVista.visibleProperty().setValue(Boolean.FALSE);
    }

    @FXML
    private void cambiarVistaComparacion(ActionEvent event) {
        comparacionVista.visibleProperty().setValue(Boolean.TRUE);
        categoriasVista.visibleProperty().setValue(Boolean.FALSE);
        tendenciaVista.visibleProperty().setValue(Boolean.FALSE);
    }

    @FXML
    private void cambiarVistaCategorias(ActionEvent event) {
        comparacionVista.visibleProperty().setValue(Boolean.FALSE);
        categoriasVista.visibleProperty().setValue(Boolean.TRUE);
        tendenciaVista.visibleProperty().setValue(Boolean.FALSE);
    }

    @FXML
    private void cambairVistaTendencia(ActionEvent event) {
        comparacionVista.visibleProperty().setValue(Boolean.FALSE);
        categoriasVista.visibleProperty().setValue(Boolean.FALSE);
        tendenciaVista.visibleProperty().setValue(Boolean.TRUE);
    }
    
}