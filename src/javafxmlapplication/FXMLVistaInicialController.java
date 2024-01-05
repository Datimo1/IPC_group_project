package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

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
    @FXML
    private TableView<String> tablaCategoriasGasto;
    @FXML
    private TableColumn<String, String> categoriaColumna;
    @FXML
    private TableColumn<String, String> gastoColumna;
    @FXML
    private Label gastoTotalLabel;
    
    private SimpleDoubleProperty gastoTotal;

     
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
        
        //EventHandlers
        addChargeButton.setOnAction((ev)->{
            try {
                //completar: lanzar registrar gastos fxml
                FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLVistaAddGasto.fxml"));
                Parent root = loader.load();
                
                //No hace falta su controlador a priori
                //FXMLVistaInicioSesionController controller = loader.getController();
                
                
                
                Scene inicioSesionScene = new Scene(root);
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/resources/icono-aplicacion.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(inicioSesionScene);
                stage.setTitle("Añadir gasto");
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.showAndWait();
                actualizarModelo();
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
       
        //Trabajo de la tabla que recoge los gastos de cada categoria
        
        
        
        
        
        
        
        
    }    

    @FXML
    private void inicioSesion(ActionEvent event) throws IOException, AcountDAOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLVistaInicioSesion.fxml"));
        Parent root = loader.load();
        
        FXMLVistaInicioSesionController controller = loader.getController();
        
        Scene inicioSesionScene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/resources/icono-aplicacion.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(inicioSesionScene);
        stage.setTitle("Inicio Sesion");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.showAndWait();
        if(controller.getUsuarioIniciado()){
            usuarioLogeado.setValue(Boolean.TRUE);
            inicioSesionButton.visibleProperty().setValue(Boolean.FALSE);
            nicknameLabel.setText(Acount.getInstance().getLoggedUser().getNickName());
            imagenPerfil.setImage(Acount.getInstance().getLoggedUser().getImage());
            visionSesionIniciada.visibleProperty().setValue(Boolean.TRUE);
            actualizarModelo();
        }
    }

    @FXML
    private void configuracion(ActionEvent event) throws IOException, AcountDAOException{
        FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLVistaPerfil.fxml"));
        Parent root = loader.load();
        
        FXMLVistaPerfilController controller = loader.getController();
        controller.setUsuario(Acount.getInstance().getLoggedUser());
        
        Scene inicioSesionScene = new Scene(root);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/resources/icono-aplicacion.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(inicioSesionScene);
        stage.setTitle("Perfil");
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.showAndWait();
        
        if(controller.getUsuarioIniciado()){
            usuarioLogeado.setValue(Boolean.TRUE);
            inicioSesionButton.visibleProperty().setValue(Boolean.FALSE);
            nicknameLabel.setText(Acount.getInstance().getLoggedUser().getNickName());
            imagenPerfil.setImage(Acount.getInstance().getLoggedUser().getImage());
            visionSesionIniciada.visibleProperty().setValue(Boolean.TRUE);
            actualizarModelo();
        }else{
            usuarioLogeado.setValue(Boolean.FALSE);
            inicioSesionButton.visibleProperty().setValue(Boolean.TRUE);
            visionSesionIniciada.visibleProperty().setValue(Boolean.FALSE);
            //Acount.getInstance().logOutUser();
            actualizarModelo();
        }
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

    private void actualizarModelo() {
        
        tablaCategoriasGasto.getItems().clear();
        tablaCategoriasGasto.setItems(null);
        ObservableList<String> listaObservable = null;
         
        List<Charge> listaGastos;
        try {
            //Recopilacion del gasto total
            listaGastos = Acount.getInstance().getUserCharges();
            double gastoTotalMes = 0;
            for(Charge i : listaGastos){
                if(i.getDate().getMonth().equals(LocalDate.now().getMonth()))
                    gastoTotalMes += i.getCost() * i.getUnits();
            }
            gastoTotalLabel.setText(Double.toString(gastoTotalMes));
            
            
            
            //Tabla de recopilacion de gasto por categoria
            Map<String,Double> gastosPorCategoria = new HashMap<>();
            for(Charge i : listaGastos){
                Double gastoPrevio = gastosPorCategoria.get(i.getCategory().getName());
                if(gastoPrevio != null){
                    if(i.getDate().getMonth().equals(LocalDate.now().getMonth()))
                        gastosPorCategoria.put(i.getCategory().getName(), gastoPrevio + i.getCost() * i.getUnits());
                }else{
                    if(i.getDate().getMonth().equals(LocalDate.now().getMonth()))
                        gastosPorCategoria.put(i.getCategory().getName(), i.getCost() * i.getUnits());
                }
            }
            
            listaObservable = FXCollections.observableArrayList(gastosPorCategoria.keySet());
            tablaCategoriasGasto.setItems(listaObservable);
            categoriaColumna.setCellValueFactory(
                    categoria -> new SimpleStringProperty(categoria.getValue()));
            gastoColumna.setCellValueFactory(
                    categoria -> new SimpleStringProperty(Double.toString(gastosPorCategoria.get(categoria.getValue()))));
            
            
            
            //Construccion del PieChart
            tartaCategorias.getData().clear();
            Set<String> categorias = gastosPorCategoria.keySet();
            System.out.print(categorias.toArray());
            categorias.forEach(i -> {
                tartaCategorias.getData().add(new PieChart.Data(i, gastosPorCategoria.get(i)));
            }); //System.out.println(tartaCategorias.getData().toArray().toString());
        } catch (AcountDAOException ex) {
            Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
}