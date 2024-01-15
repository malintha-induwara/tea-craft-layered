package lk.ijse.teacraft.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import lk.ijse.teacraft.bo.BOFactory;
import lk.ijse.teacraft.bo.custom.SupplierBO;
import lk.ijse.teacraft.dto.SupplierDto;
import lk.ijse.teacraft.view.tdm.SupplierTm;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SupplierFormController {


    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colBank;

    @FXML
    private TableColumn<?, ?> colBankNo;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colFirstName;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUpdate;



    @FXML
    private TableView<SupplierTm> tblSupplier;


    @FXML
    private MFXButton btnAddSupplier;

    @FXML
    private MFXButton btnPayments;

    @FXML
    private AnchorPane supplierPane;



    @FXML
    private MFXTextField txtSearch;


    private final SupplierBO supplierModel = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SUPPLIER);



    public void initialize(){
        setCellValueFactory();
        loadAllSuppliers();

    }

    private void setCellValueFactory() {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colBank.setCellValueFactory(new PropertyValueFactory<>("bank"));
        colBankNo.setCellValueFactory(new PropertyValueFactory<>("bankNo"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));


    }


    @FXML
    void btnAddSupplierOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/addSupplierForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
       AddSupplierFormController addSupplierFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        addSupplierFormController.setCustomerFormController(this);

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Supplier");
        stage.show();



    }



    @FXML
    void btnPaymentsOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/paymentForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        supplierPane.getChildren().clear();
        supplierPane.getChildren().add(registerPane);

    }

    private void  deleteSupplier(String id){

        try{
            boolean isDeleted = supplierModel.deleteSupplier(id);

            if (isDeleted){
              new Alert(Alert.AlertType.CONFIRMATION,"Supplier Deleted").show();
              loadAllSuppliers();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    public void loadAllSuppliers() {

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();


        try {

            List<SupplierDto> dtoList = supplierModel.getAllSuppliers();


            for (SupplierDto dto: dtoList){

                obList.add(new SupplierTm(
                        dto.getSupId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getAddress(),
                        dto.getBank(),
                        dto.getBankNo(),
                        dto.getMobileNo()
                       )

                );
            }


            for (int i = 0; i < obList.size(); i++) {
                final int index = i ;

                obList.get(i).getUpdateButton().setOnAction(event -> {
                    try {
                        updateSupplier(dtoList.get(index).getSupId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                obList.get(i).getDeleteButton().setOnAction(event -> {


                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no) == yes){
                        String supId = dtoList.get(index).getSupId();
                        deleteSupplier(supId);
                    }

                });




            }




            tblSupplier.setItems(obList);


        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

    private void updateSupplier(String supId) throws IOException {


            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/updateSupplierForm.fxml"));
            Parent rootNode = loader.load();

            // Get a reference to the AddCustomerFormController
            UpdateSupplierFormController updateSupplierFormController = loader.getController();

            // Pass a reference to this CustomerFormController
            updateSupplierFormController.setSupplierFormController(this);
            updateSupplierFormController.setSupplierId(supId);
            updateSupplierFormController.loadSupplierDetails();

            Scene scene = new Scene(rootNode);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle("Update Supplier");
            stage.show();


    }


    @FXML
    void txtSearchOnAction(ActionEvent event) {
        FilteredList<SupplierTm> filteredData = new FilteredList<>(tblSupplier.getItems(), b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(supplierTm -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return supplierTm.getSupId().toLowerCase().contains(searchKeyword) ||
                        supplierTm.getFirstName().toLowerCase().contains(searchKeyword) ||
                        supplierTm.getLastName().toLowerCase().contains(searchKeyword) ||
                        supplierTm.getAddress().toLowerCase().contains(searchKeyword) ||
                        supplierTm.getBank().toLowerCase().contains(searchKeyword) ||
                        supplierTm.getBankNo().toLowerCase().contains(searchKeyword) ||
                        supplierTm.getMobileNo().toLowerCase().contains(searchKeyword);
            });
        });

        tblSupplier.setItems(filteredData);

    }


}
