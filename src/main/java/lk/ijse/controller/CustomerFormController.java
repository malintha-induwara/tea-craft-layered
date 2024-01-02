package lk.ijse.controller;

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
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.view.tdm.CustomerTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CustomerFormController {


    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colFirstName;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private MFXButton btnAddCustomer;


    @FXML
    private MFXTextField txtSearch;



    private  final CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize(){
        setCellValueFactory();
        loadAllCustomers();
    }


    @FXML
    void btnAddCustomerOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/addCustomerForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
        AddCustomerFormController addCustomerFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        addCustomerFormController.setCustomerFormController(this);

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Customer");
        stage.show();
    }

    private void setCellValueFactory() {

        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));


    }


    public void loadAllCustomers() {


        try {
            ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

            List<CustomerDto> dtoList = customerBO.getAllCustomer();

            for (CustomerDto dto:dtoList){

                obList.add( new CustomerTm(
                        dto.getCusId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getAddress(),
                        dto.getEmail(),
                        dto.getMobileNo()
                ));

            }

            for (int i = 0; i < obList.size(); i++) {
                final int index= i;

                obList.get(i).getUpdateButton().setOnAction(event -> {
                    try {
                        updateCustomer(dtoList.get(index).getCusId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                obList.get(i).getDeleteButton().setOnAction(event -> {

                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no)==yes) {
                        String cusId = dtoList.get(index).getCusId();
                        deleteCustomer(cusId);
                    }
                });
            }


            tblCustomer.setItems(obList);

        }
        catch (SQLException  e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void updateCustomer(String cusId) throws IOException {


        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/updateCustomerForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
        UpdateCustomerFormController updateCustomerFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        updateCustomerFormController.setCustomerFormController(this);
        updateCustomerFormController.setCustomerId(cusId);
        updateCustomerFormController.loadCustomerDetails();

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Customer");
        stage.show();



    }

    private void deleteCustomer(String id) {

        try {
            boolean isDeleted = customerBO.deleteCustomer(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted!").show();
                loadAllCustomers();
            }
        } catch (SQLException  e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

        FilteredList<CustomerTm> filteredData = new FilteredList<>(tblCustomer.getItems(), b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customerTm -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return customerTm.getCusId().toLowerCase().contains(searchKeyword) ||
                        customerTm.getFirstName().toLowerCase().contains(searchKeyword) ||
                        customerTm.getLastName().toLowerCase().contains(searchKeyword) ||
                        customerTm.getAddress().toLowerCase().contains(searchKeyword) ||
                        customerTm.getEmail().toLowerCase().contains(searchKeyword) ||
                        customerTm.getMobileNo().toLowerCase().contains(searchKeyword);
            });
        });

        tblCustomer.setItems(filteredData);
    }

}


