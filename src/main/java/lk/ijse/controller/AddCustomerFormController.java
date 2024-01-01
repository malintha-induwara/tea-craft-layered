package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.dto.CustomerDto;
import lk.ijse.model.CustomerModel;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddCustomerFormController {

    private  final CustomerModel customerModel = new CustomerModel();


    @FXML
    private MFXButton btnAddCustomer;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXTextField txtAddress;

    @FXML
    private Text txtCustomerId;

    @FXML
    private MFXTextField txtEmail;

    @FXML
    private MFXTextField txtFirstName;

    @FXML
    private MFXTextField txtLastName;

    @FXML
    private MFXTextField txtMobileNo;

    //Hold The Reference
    private CustomerFormController customerFormController;

    public void initialize(){

        generateNextCustomerId();

    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {

        boolean isCustomerValidated = validateCustomer();


        if (!isCustomerValidated){
            return;
        }

        String cusId= txtCustomerId.getText();
        String firstName= txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String mobileNo = txtMobileNo.getText();

        var dto = new CustomerDto(cusId,firstName,lastName,address,email,mobileNo);


        try {
            boolean isSaved = customerModel.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer saved!").show();
                clearFields();

                if (customerFormController != null) {
                    customerFormController.loadAllCustomers();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private boolean validateCustomer() {

        String firstName= txtFirstName.getText();
        boolean isFirstNameValidated = Pattern.matches("[A-Za-z]{3,}",firstName);
        if (!isFirstNameValidated){
            txtFirstName.requestFocus();
            txtFirstName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFirstName.getStyleClass().removeAll("mfx-text-field-error");



        String lastName = txtLastName.getText();
        boolean isLastNameValidated = Pattern.matches("[A-Za-z]{3,}",lastName);
        if (!isLastNameValidated){
            txtLastName.requestFocus();
            txtLastName.getStyleClass().add("mfx-text-field-error");

            return false;
        }

        txtLastName.getStyleClass().removeAll("mfx-text-field-error");



        String address = txtAddress.getText();
        boolean isAddressValidated = Pattern.matches("[A-Za-z0-9/ ]{3,}",address);
        if (!isAddressValidated){
            txtAddress.requestFocus();
            txtAddress.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtAddress.getStyleClass().removeAll("mfx-text-field-error");


        String email = txtEmail.getText();
        boolean isEmailValidated = Pattern.matches("[A-Za-z0-9@.]{3,}",email);
        if (!isEmailValidated){
            txtEmail.requestFocus();
            txtEmail.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtEmail.getStyleClass().removeAll("mfx-text-field-error");

        String mobileNo = txtMobileNo.getText();
        boolean isMobileNoValidated = Pattern.matches("[0-9]{3,}",mobileNo);
        if (!isMobileNoValidated){
            txtMobileNo.requestFocus();
            txtMobileNo.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtMobileNo.getStyleClass().removeAll("mfx-text-field-error");

        return true;
    }





    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage addCustomereStage= (Stage) btnCancel.getScene().getWindow();
        addCustomereStage.close();

    }


    private void generateNextCustomerId(){
        try{
            String customerId=customerModel.generateNextCustomerId();
            txtCustomerId.setText(customerId);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void clearFields() {
        generateNextCustomerId();
        txtAddress.clear();
        txtEmail.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtMobileNo.clear();
    }


    public void setCustomerFormController(CustomerFormController customerFormController) {
        this.customerFormController = customerFormController;
    }
}
