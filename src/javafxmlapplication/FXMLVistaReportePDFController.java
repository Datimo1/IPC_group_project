package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Acount;
import model.AcountDAOException;
import model.Charge;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jiaxiang Jin
 */
public class FXMLVistaReportePDFController implements Initializable{

    @FXML
    private BarChart<String, Double> graficaBarra;
    @FXML
    private Label fechaCreacionLabel;
    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Limpiamos los datos predeterminados
            graficaBarra.getData().clear();
            List<Charge> listaGastos;
            
            //Creamos el diccionario con los gastos anuales
            listaGastos = Acount.getInstance().getUserCharges();
            Map<Integer, Double> gastosPorAnyo = new HashMap<>();
            Double gastoPrevio;
            for(Charge i : listaGastos){
                if(gastosPorAnyo.get(i.getDate().getYear()) != null){
                    gastoPrevio = gastosPorAnyo.get(i.getDate().getYear());
                    gastosPorAnyo.put(i.getDate().getYear(), gastoPrevio + i.getCost() * i.getUnits());
                }else{
                    gastosPorAnyo.put(i.getDate().getYear(), i.getCost() * i.getUnits());
                }
            }
            
            Set<Integer> anyos = gastosPorAnyo.keySet();
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            for(Integer a :anyos){
                series.getData().add(new XYChart.Data<>(a.toString(), gastosPorAnyo.get(a)));
            }
            //FXCollections.reverse(series.getData());
            // Agregar la serie al BarChart
            graficaBarra.getData().add(series);
        } catch (AcountDAOException ex) {
            Logger.getLogger(FXMLVistaReportePDFController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLVistaReportePDFController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        fechaCreacionLabel.setText("Fecha creación: " + LocalDate.now());
        
    }
    
    
    
}
