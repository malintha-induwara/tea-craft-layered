package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.entity.Fertilizer;
import lk.ijse.view.tdm.FertilizerTm;
import lk.ijse.model.FertilizerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FertilizerFormController {

    @FXML
    private MFXButton btnAddFertilizer;

    @FXML
    private MFXButton btnFertilizer;

    @FXML
    private MFXButton btnSales;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colFertilizerId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private TableView<FertilizerTm> tblFertilizer;


    @FXML
    private AnchorPane fertilizerPane;

    private final FertilizerModel fertilizerModel = new FertilizerModel();

    public void initialize(){
        loadAllFertilizers();
        setCellValueFactory();
    }

    private void setCellValueFactory() {
        colFertilizerId.setCellValueFactory(new PropertyValueFactory<>("fertilizerId"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

    public void loadAllFertilizers() {

        ObservableList<FertilizerTm> obList = FXCollections.observableArrayList();
        try {
            List<Fertilizer> dtoList = fertilizerModel.getAllFertilizers();


            for (Fertilizer dto : dtoList) {

                FertilizerTm tm = new FertilizerTm(
                        dto.getFertilizerId(),
                        dto.getBrand(),
                        dto.getDescription(),
                        dto.getSize(),
                        dto.getQty(),
                        dto.getPrice());
                obList.add(tm);
            }


            for (int i = 0; i < obList.size(); i++) {
                final int index = i;

                obList.get(i).getUpdateButton().setOnAction(actionEvent -> {
                    try {
                        updateFertilizer(dtoList.get(index).getFertilizerId());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                obList.get(i).getDeleteButton().setOnAction(actionEvent -> {

                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);


                    Optional<ButtonType> types = new Alert(Alert.AlertType.INFORMATION,"Are You Sure to Remove?", yes, no).showAndWait();

                    if (types.orElse(no)==yes){


                        String fertilizerId = obList.get(index).getFertilizerId();
                        deleteFertilizer(fertilizerId);
                        loadAllFertilizers();
                    }
                });

            }

            tblFertilizer.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

    private void deleteFertilizer(String fertilizerId) {

        try{
            boolean isDeleted = fertilizerModel.deleteFertilizer(fertilizerId);

            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted Success").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void updateFertilizer(String fertilizerId) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/updateFertilizer.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController

        UpdateFertilizerController updateFertilizerController = loader.getController();


        // Pass a reference to this CustomerFormController
        updateFertilizerController.setFertilizerFormController(this);
        updateFertilizerController.setFertilizerId(fertilizerId);
        updateFertilizerController.loadFertilizerDetails();

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Fertilizer");
        stage.show();


    }

    @FXML
    void btnAddFertilizerOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/addFertilizerForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
        AddFertilizerFormController addFertilizerFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        addFertilizerFormController.setCustomerFormController(this);

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Fertilizer");
        stage.show();

    }

    @FXML
    void btnSalesOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fertilizerSalesForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        fertilizerPane.getChildren().clear();
        fertilizerPane.getChildren().add(registerPane);

    }


}
