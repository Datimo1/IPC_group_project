/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxmlapplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;

/**
 *
 * @author Jiaxiang Jin
 */
public class FXMLVistaModGastoController implements Initializable{

    @FXML
    private TextField tituloField;
    @FXML
    private Text tituloError;
    @FXML
    private DatePicker fechaSelector;
    @FXML
    private Text fechaError;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Text descripcionError;
    @FXML
    private ComboBox<Category> categoriaCombox;
    @FXML
    private Button newCatButton;
    @FXML
    private Text categoriaError;
    @FXML
    private TextField costeField;
    @FXML
    private Text costeError;
    @FXML
    private Button facturaButton;
    @FXML
    private ImageView profileImage;
    @FXML
    private Button cancelarButton;
    @FXML
    private TextField unidadesField;
    @FXML
    private Text unidadesError;
   
    private boolean seModifico;
    private List<Category> listadoCategoria;
    private ObservableList<Category> datosListadoCategoria;
    private Image facturaImagen = null;
    
    @FXML
    private Button editButton;
    private Charge gasto;
    
    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            System.out.println("Funciona Initialize");
            seModifico = false;
            // Crear combobox
            listadoCategoria = Acount.getInstance().getUserCategories();
            datosListadoCategoria = FXCollections.observableArrayList(listadoCategoria);
            if(listadoCategoria != null){
//                for(int i = 0; i < listadoCategoria.size(); i++){
//                    categoriaCombox.getItems().add(listadoCategoria.get(i));
//                }
                  categoriaCombox.setItems(datosListadoCategoria);
            }
            
            
            categoriaCombox.setCellFactory(c->new ComboCelda());
            categoriaCombox.setButtonCell(new ComboCelda());
            
            //----------------------------------------------------------------------------------------------------------------------------------------
            //EventHandler
            editButton.setOnAction((ev) -> {
                
                //registrar el gasto
                if (!tituloField.getText().isEmpty() && fechaSelector.getValue() != null
                        && categoriaCombox.getValue() != null && !costeField.getText().isEmpty()
                        && !descriptionArea.getText().isEmpty() && !unidadesField.getText().isEmpty()) {
                    
                    gasto.setName(tituloField.getText());
                    gasto.setDescription(descriptionArea.getText());
                    gasto.setCost(Double.parseDouble(costeField.getText()));
                    gasto.setUnits(Integer.parseInt(unidadesField.getText()));
                    gasto.setImageScan(facturaImagen);
                    gasto.setDate(fechaSelector.getValue());
                    gasto.setCategory(categoriaCombox.getValue());
                    
                    seModifico = true;
                    editButton.getScene().getWindow().hide();
                }else{
                    //mensajes de error si faltan algunos campos
                    if(tituloField.getText().isEmpty()){
                        tituloError.setText("Introduzca un título");
                    }
                    
                    if (fechaSelector.getValue() == null) {
                        fechaError.setText("Seleccione una fecha");
                    }
                    
                    if (categoriaCombox.getValue() == null) {
                        categoriaError.setText("Seleccione una categoría");
                    }
                    
                    if (costeField.getText().isEmpty()) {
                        costeError.setText("Introduzca un coste");
                    }
                    
                    if (descriptionArea.getText().isEmpty()) {
                        descripcionError.setText("Introduzca una descripción");
                    }
                    
                    if (unidadesField.getText().isEmpty()) {
                        unidadesError.setText("Introduzca las ud.");
                    }
                    
                }
                
            });
            
            cancelarButton.setOnAction((ev)->{
                cancelarButton.getScene().getWindow().hide();
            });
            //-------------------------------------------------------------------------------------------------------------------------------
            //Listener:
            
            //asegurar que los precios y unidades son solo numeros
            costeField.textProperty().addListener((ob, oldV, newV)->{
                if(!newV.matches("\\d*(\\.\\d*)?")){
                    costeField.setText(oldV);
                    costeError.setText("Solo números");
                }else{
                    costeError.setText("");
                }
            });
            
            unidadesField.textProperty().addListener((ob, oldV, newV)->{
                if(!newV.matches("\\d*")){
                    unidadesField.setText(oldV);
                    unidadesError.setText("Solo nº enteros");
                }else{
                    unidadesError.setText("");
                }
            });
            
            //quitar los textos de errores cuando escribe el usuario
            tituloField.focusedProperty().addListener((ob, oldV, newV)->{
                if(newV){
                    tituloError.setText("");
                }
            });
            
            descriptionArea.focusedProperty().addListener((ob, oldV, newV)->{
                if(newV){
                    descripcionError.setText("");
                }
            });
            
            fechaSelector.focusedProperty().addListener((ob, oldV, newV)->{
                if(newV){
                    fechaError.setText("");
                }
            });
            
            categoriaCombox.focusedProperty().addListener((ob, oldV, newV)->{
                if(newV){
                    categoriaError.setText("");
                }
            });
            
            costeField.focusedProperty().addListener((ob, oldV, newV)->{
                if(newV){
                    costeError.setText("");
                }
            });
            
            unidadesField.focusedProperty().addListener((ob, oldV, newV)->{
                if(newV){
                    unidadesError.setText("");
                }
            });
        } catch (AcountDAOException ex) {
            Logger.getLogger(FXMLVistaAddGastoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLVistaAddGastoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void pickImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Mostrar el diálogo de selección de archivos
        Stage stage = (Stage) facturaButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Procesar el archivo seleccionado (puedes hacer lo que necesites con él)
        if (selectedFile != null) {
            System.out.println("Imagen seleccionada: " + selectedFile.getAbsolutePath());
            // Cargar la imagen en el ImageView
            facturaImagen = new Image(selectedFile.toURI().toString());
            profileImage.setImage(facturaImagen);
        }
    }

    @FXML
    private void createCategory(ActionEvent event) throws AcountDAOException {
        try {
                //completar: lanzar registrar gastos fxml
                FXMLLoader loader= new FXMLLoader(getClass().getResource("FXMLVistaNuevaCategoria.fxml"));
                Parent root = loader.load();
                FXMLVistaNuevaCategoriaController controller = loader.getController();
                
                Scene inicioSesionScene = new Scene(root);
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/resources/icono-aplicacion.png"));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(inicioSesionScene);
                stage.setTitle("Añadir categoria");
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.showAndWait();
                
                if(controller.isAnyadido()){
                    listadoCategoria = Acount.getInstance().getUserCategories();
                    datosListadoCategoria = FXCollections.observableArrayList(listadoCategoria);
                    categoriaCombox.setItems(datosListadoCategoria);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(FXMLVistaInicialController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public boolean seModifico(){
        return seModifico;
    }
    
    public void setCharge(Charge c){
        gasto = c;
        tituloField.setText(c.getName());
        fechaSelector.setValue(c.getDate());
        descriptionArea.setText(c.getDescription());
        categoriaCombox.getSelectionModel().select(c.getCategory());
        costeField.setText(String.valueOf(c.getCost()));
        unidadesField.setText(String.valueOf(c.getUnits()));
        facturaImagen = c.getImageScan();
        profileImage.setImage(facturaImagen);
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
