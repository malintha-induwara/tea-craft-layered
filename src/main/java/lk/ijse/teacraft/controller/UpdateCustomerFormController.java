package lk.ijse.teacraft.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.teacraft.bo.BOFactory;
import lk.ijse.teacraft.bo.custom.CustomerBO;
import lk.ijse.teacraft.dto.CustomerDto;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateCustomerFormController {

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnUpdateCustomer;

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

    private CustomerFormController customerFormController;

    private String cusId;


    private  final CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);


    @FXML
    void btnCancelOnAction(ActionEvent event) {

        Stage addCustomereStage= (Stage) btnCancel.getScene().getWindow();
        addCustomereStage.close();

    }

    @FXML
    void btnUpdateCustomerOnAction(ActionEvent event) {


        boolean isCustomerValidated = validateCustomer();


        if (!isCustomerValidated){
            return;
        }

        String cusId= txtCustomerId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        String email =  txtEmail.getText();
        String mobileNo = txtMobileNo.getText();

        try{
            boolean isUpdated = customerBO.updateCustomer(new CustomerDto(cusId,firstName,lastName,address,email,mobileNo));

            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer updated").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        //To Reload the table
        customerFormController.loadAllCustomers();


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


    public void loadCustomerDetails() {

       try {
           CustomerDto dto = customerBO.searchCustomer(cusId);
           setFields(dto);
       } catch (SQLException  e) {
           new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
       }

    }

    private void setFields(CustomerDto dto) {
        txtCustomerId.setText(dto.getCusId());
        txtFirstName.setText(dto.getFirstName());
        txtLastName.setText(dto.getLastName());
        txtAddress.setText(dto.getAddress());
        txtEmail.setText(dto.getEmail());
        txtMobileNo.setText(dto.getMobileNo());
    }


    public void setCustomerFormController(CustomerFormController customerFormController) {
         this.customerFormController=customerFormController;
    }

    public void setCustomerId(String cusId){
        this.cusId=cusId;
    }

}
