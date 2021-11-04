
package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddSeries"
    private Button btnAddSeries; // Value injected by FXMLLoader

    @FXML // fx:id="btnCancel"
    private Button btnCancel; // Value injected by FXMLLoader

    @FXML // fx:id="btnEnter"
    private Button btnEnter; // Value injected by FXMLLoader

    @FXML // fx:id="btnGenerate"
    private Button btnGenerate; // Value injected by FXMLLoader

    @FXML // fx:id="cmbDataset_List"
    private ComboBox<String> cmbDataset_List; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSeries_List"
    private ComboBox<String> cmbSeries_List; // Value injected by FXMLLoader

    ObservableList<String> Series_Names = FXCollections.observableArrayList();
    ObservableList<String> Datalist_Names = FXCollections.observableArrayList();


    @FXML // fx:id="tblDataset"
    private TableView<All_Series> tblDataset; // Value injected by FXMLLoader

//    @FXML
//    public TableColumn<All_Series, Integer> _id;
    @FXML
    public TableColumn<All_Series, Integer> _X;
    @FXML
    public TableColumn<All_Series, Integer> _Y;

    @FXML // fx:id="tfX_Axis"
    private TextField tfX_Axis; // Value injected by FXMLLoader

    @FXML // fx:id="tfY_Axis"
    private TextField tfY_Axis; // Value injected by FXMLLoader

    @FXML
    void CreateDataset(ActionEvent event) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Dataset");
        td.showAndWait();
        String s = td.getEditor().getText();
        Datalist_Names.add(s);
    }

    @FXML
    void AddEntry(ActionEvent event) {
        int x = Integer.parseInt(tfX_Axis.getText());
        int y = Integer.parseInt(tfY_Axis.getText());



        if (!tfX_Axis.getText().isEmpty() && !tfY_Axis.getText().isEmpty()) {
//            insertAll_Series(Double.parseDouble(tfX_Axis.getText()), Double.parseDouble(tfY_Axis.getText()));
            tfX_Axis.setText("0");
            tfY_Axis.setText("0");
            All_Series series = new All_Series(x,y);
            tblDataset.getItems().add(series);
        } else {
            Alert alert=  new Alert(Alert.AlertType.NONE,"Must Enter a number",ButtonType.OK);
            alert.showAndWait();
        }

    }

    @FXML
    void AddSeries(ActionEvent event) {
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("Enter Series Name");
        td.showAndWait();
        String s = td.getEditor().getText();
        Series_Names.add(s);


    }

    @FXML
    void GenerateGraph(ActionEvent event) {
        Alert alert=  new Alert(Alert.AlertType.NONE,"you clicked add series",ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    void OnCancel(ActionEvent event) {
        Alert alert=  new Alert(Alert.AlertType.NONE,"you clicked add series",ButtonType.OK);
        alert.showAndWait();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddSeries != null : "fx:id=\"btnAddSeries\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert btnEnter != null : "fx:id=\"btnEnter\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert btnGenerate != null : "fx:id=\"btnGenerate\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert cmbDataset_List != null : "fx:id=\"cmbDataset_List\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert cmbSeries_List != null : "fx:id=\"cmbSeries_List\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert tblDataset != null : "fx:id=\"tblDataset\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert tfX_Axis != null : "fx:id=\"tfX_Axis\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert tfY_Axis != null : "fx:id=\"tfY_Axis\" was not injected: check your FXML file 'hello-view.fxml'.";
        _X.setCellValueFactory(new PropertyValueFactory<>("_X"));
        _Y.setCellValueFactory(new PropertyValueFactory<>("_Y"));
        tfX_Axis.setText("0");
        tfY_Axis.setText("0");
        cmbDataset_List.setItems(Datalist_Names);
        cmbSeries_List.setItems(Series_Names);
//        tblDataset.setItems(series);
//        _id.setCellValueFactory(new PropertyValueFactory<>("_id"));
        btnGenerate.setDisable(true);


    }


}
