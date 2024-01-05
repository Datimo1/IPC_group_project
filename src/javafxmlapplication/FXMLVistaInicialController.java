package javafxmlapplication;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

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
    private TableView<Charge> tablaGastos;
    @FXML
    private TableColumn<Charge, String> nameColumn;
    @FXML
    private TableColumn<Charge, String> categoryColumn;
    @FXML
    private TableColumn<Charge, String> costColumn;
    @FXML
    private TableColumn<Charge, String> dateColumn;
    @FXML
    private ComboBox<Category> categoryFIlter;
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
    private ComboBox<Integer> selectYear;
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
    @FXML
    private DatePicker initDateFilter;
    @FXML
    private DatePicker finDateFilter;
    @FXML
    private Button verGastosButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button eliminarButton;
    
    private boolean tablaConstruida;
    @FXML
    private Button eliminarButton1;
    @FXML
    private BarChart<String, Double> tablaComparacionMeses;

     
    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        //Asegurarse que el modelo esta vacio
        borrarModelo();
        tablaConstruida = false;
        
        inicioSesionButton.visibleProperty().setValue(Boolean.TRUE);
        visionSesionIniciada.visibleProperty().setValue(Boolean.FALSE);
        
        vistaInicio.visibleProperty().setValue(Boolean.TRUE);
        vistaGastos.visibleProperty().setValue(Boolean.FALSE);
        vistaGraficas.visibleProperty().setValue(Boolean.FALSE);
        
        //Bindings
        addChargeButton.disableProperty().bind(usuarioLogeado.not());
        aPDFButton.disableProperty().bind(usuarioLogeado.not());
        //inicioButton.disableProperty().bind(usuarioLogeado.not());
        //listaButton.disableProperty().bind(usuarioLogeado.not());
        //graficasButton.disableProperty().bind(usuarioLogeado.not());
        categoryFIlter.disableProperty().bind(usuarioLogeado.not());
        initDateFilter.disableProperty().bind(usuarioLogeado.not());
        finDateFilter.disableProperty().bind(usuarioLogeado.not());
        verGastosButton.disableProperty().bind
        (Bindings.equal(tablaGastos.getSelectionModel().selectedIndexProperty(), -1));
        editarButton.disableProperty().bind
        (Bindings.equal(tablaGastos.getSelectionModel().selectedIndexProperty(), -1));
        eliminarButton.disableProperty().bind
        (Bindings.equal(tablaGastos.getSelectionModel().selectedIndexProperty(), -1));
        
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
        
        //Crear pdf a partir de una captura de un nodo JavaFX
        aPDFButton.setOnAction((ev)->{
            try {
                // Cargar el FXML
                //
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLVistaReportePDF.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                
//                Stage stage = new Stage();
//                stage.setScene(scene);
//                stage.setTitle("FXML to PDF Converter");
//                stage.show();
                
                // Crear un archivo PDF
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Guardar PDF");
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PDF (*.pdf)", "*.pdf");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showSaveDialog(new Stage());
                
                if (file != null) {
                    PDDocument document = new PDDocument();
                    PDPage page = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
                    document.addPage(page);
                    
                    // Renderizar la escena en un BufferedImage
                    WritableImage snapshot = scene.snapshot(null);
                    try {
                        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(snapshot, null);
                        
                        // Guardar el BufferedImage como PDF usando Apache PDFBox
                        //ImageIO.write(bufferedImage, "png", file); // Convertir la imagen a un formato compatible con PDF

                        PDPageContentStream contentStream = new PDPageContentStream(document, page);
                        float pageWidth = page.getMediaBox().getWidth();
                        float pageHeight = page.getMediaBox().getHeight();
                        float imageWidth = bufferedImage.getWidth();
                        float imageHeight = bufferedImage.getHeight();

                        float startX = (pageWidth - imageWidth) / 2; // Calcular la posición X centrada
                        float startY = (pageHeight - imageHeight) / 2; // Calcular la posición Y centrada
                        contentStream.drawImage(LosslessFactory.createFromImage(document, bufferedImage), startX, startY);
                        contentStream.close();
                        
                        document.save(file);
                        document.close();
                        System.out.println("FXML convertido a PDF correctamente en " + file.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                
                //stage.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        //Listeners
        categoryFIlter.valueProperty().addListener((c,old,nw)->{try {
            filtrarTabla();
            } catch (AcountDAOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
});
        initDateFilter.valueProperty().addListener((c,old,nw)->{try {
            filtrarTabla();
            } catch (AcountDAOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
});
        finDateFilter.valueProperty().addListener((c,old,nw)->{try {
            filtrarTabla();
            } catch (AcountDAOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
});
        
        
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
            graficarTablaMesesAnos();
            
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
            borrarModelo();
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
        graficarTablaMesesAnos();
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
            categorias.forEach(i -> {
                tartaCategorias.getData().add(new PieChart.Data(i, gastosPorCategoria.get(i)));
            });
            
            //Construccion de la tabla
            if(!tablaConstruida)
                contruirTablaGastos();
            
            
            
        } catch (AcountDAOException ex) {
            Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void borrarModelo() {
        gastoTotalLabel.setText("0");
        tablaCategoriasGasto.getItems().clear();
        tartaCategorias.getData().clear();
        tablaGastos.getItems().clear();
    }

    private void contruirTablaGastos() throws AcountDAOException, IOException {
        //Construccion de la tabla de gastos
            // Crear combobox
            List<Category> listadoCategoria = Acount.getInstance().getUserCategories();
            ObservableList<Category> datosListadoCategoria = 
                    FXCollections.observableArrayList(listadoCategoria);
            if(listadoCategoria != null){
//                for(int i = 0; i < listadoCategoria.size(); i++){
//                    categoriaCombox.getItems().add(listadoCategoria.get(i));
//                }
                  
                  categoryFIlter.setItems(datosListadoCategoria);
            }
            categoryFIlter.setCellFactory(c->new ComboCelda());
            categoryFIlter.setButtonCell(new ComboCelda());
            
            ObservableList<Charge> listaObservableGastos = 
                    FXCollections.observableArrayList(Acount.getInstance().getUserCharges());
            tablaGastos.setItems(listaObservableGastos);
            nameColumn.setCellValueFactory(
                    cargo -> new SimpleStringProperty(cargo.getValue().getName()));
            categoryColumn.setCellValueFactory(
                    cargo -> new SimpleStringProperty(cargo.getValue().getCategory().getName()));
            costColumn.setCellValueFactory(
                    cargo -> new SimpleStringProperty(Double.toString(cargo.getValue().getCost() * cargo.getValue().getUnits())));
            dateColumn.setCellValueFactory(
                    cargo -> new SimpleStringProperty(cargo.getValue().getDate().toString())); 
    }

    private void filtrarTabla() throws AcountDAOException, IOException {
        List<Charge> listadoCargos = Acount.getInstance().getUserCharges();
        ObservableList<Charge> listaFiltrada = 
                FXCollections.observableArrayList(listadoCargos);
        if(categoryFIlter.getValue() != null){
            for(Charge i : listadoCargos) {
                if(!i.getCategory().equals(categoryFIlter.getValue()))
                    listaFiltrada.remove(i);
            }  
        }
        if(initDateFilter.getValue() != null){
            for(Charge i : listadoCargos) {
                if(i.getDate().isBefore(initDateFilter.getValue()))
                    listaFiltrada.remove(i);
            }  
        }
        if(finDateFilter.getValue() != null){
            for(Charge i : listadoCargos) {
                if(i.getDate().isAfter(finDateFilter.getValue()))
                    listaFiltrada.remove(i);
            }  
        }
        
        tablaGastos.setItems(listaFiltrada);
    }

    @FXML
    private void verGasto(ActionEvent event) {
    }

    @FXML
    private void editarGasto(ActionEvent event) {
    }

    @FXML
    private void eliminarGasto(ActionEvent event) throws AcountDAOException, IOException {
//        tablaGastos.getItems().remove(tablaGastos.getSelectionModel().getSelectedItem());
//        tablaGastos.getSelectionModel().select(-1);
//        Acount.getInstance().removeCharge(tablaGastos.getSelectionModel().getSelectedItem());
//        actualizarModelo();
   }

    @FXML
    private void borrarFiltros(ActionEvent event) throws AcountDAOException, IOException {
        categoryFIlter.setValue(null);
        initDateFilter.setValue(null);
        finDateFilter.setValue(null);
        filtrarTabla();
    }

    private void graficarTablaDosMeses() {
        
    }

    private void graficarTablaMesesAnos() {
        tablaComparacionMeses.getData().clear();
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
            selectYear.setValue(2024);
            Map<Month,Double> gastosPorMes = new HashMap<>();
            for(Charge i : listaGastos){
                if(i.getDate().getYear() == selectYear.getValue()){
                    Double gastoPrevio = gastosPorMes.get(i.getDate().getMonth());
                    if(gastoPrevio != null){
                        gastosPorMes.put(i.getDate().getMonth(), gastoPrevio + i.getCost() * i.getUnits());
                    }else{
                        gastosPorMes.put(i.getDate().getMonth(), i.getCost() * i.getUnits());
                    }
                }
            }
            
            Set<Month> meses = gastosPorMes.keySet();
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            for(Month m : meses){
                series.getData().add(new XYChart.Data<>(m.toString(), gastosPorMes.get(m)));
            }
            FXCollections.reverse(series.getData());
            // Agregar la serie al BarChart
            tablaComparacionMeses.getData().add(series);
            
            
            
            
            
        } catch (AcountDAOException ex) {
            Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    
    private static class ComboCelda extends ComboBoxListCell<Category>{

        @Override
        public void updateItem(Category t, boolean bln) {
            super.updateItem(t, bln); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
            if(bln || t == null){
                setText("");
            }else{
                setText(t.getName());
            }
        }
    }
    
}