package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.entity.Supplier;
import lk.ijse.model.SupplierModel;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddSupplierFormController {


    private final SupplierModel  supplierModel = new SupplierModel();

    @FXML
    private MFXButton btnAddSupplier;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXTextField txtAddress;

    @FXML
    private MFXTextField txtBank;

    @FXML
    private MFXTextField txtBankNo;

    @FXML
    private Text txtSupplierId;

    @FXML
    private MFXTextField txtFirstName;

    @FXML
    private MFXTextField txtLastName;

    @FXML
    private MFXTextField txtMobileNo;

    private SupplierFormController supplierFormController;


    public void  initialize(){

        generateNextSupplierId();
    }


    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {

        boolean isSupplierValidated = validateSupplier();

        if (!isSupplierValidated){
            return;
        }

        String supId= txtSupplierId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        String bank = txtBank.getText();
        String bankNo= txtBankNo.getText();
        String mobileNo= txtMobileNo.getText();

        var dto = new Supplier(supId,firstName,lastName,address,bank,bankNo,mobileNo);

        try{
            boolean isSaved= supplierModel.saveSupplier(dto);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved").show();
                clearFields();
            }

            if (supplierFormController != null){
                supplierFormController.loadAllSuppliers();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

    private boolean validateSupplier() {


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

        String bank = txtBank.getText();

        boolean isBankValidated = Pattern.matches("[A-Za-z0-9/ ]{3,}",bank);

        if (!isBankValidated){
            txtBank.requestFocus();
            txtBank.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtBank.getStyleClass().removeAll("mfx-text-field-error");


        String bankNo = txtBankNo.getText();

        boolean isBankNoValidated = Pattern.matches("[0-9]{3,}",bankNo);

        if (!isBankNoValidated){
            txtBankNo.requestFocus();
            txtBankNo.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtBankNo.getStyleClass().removeAll("mfx-text-field-error");



        String mobileNo = txtMobileNo.getText();
        boolean isMobileNoValid = Pattern.matches("[0-9]{3,}",mobileNo);
        if (!isMobileNoValid){
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

    private void generateNextSupplierId(){

        try{
            String supplierId = supplierModel.generateNextSupplierId();
            txtSupplierId.setText(supplierId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }

    }

    private void clearFields(){
        generateNextSupplierId();
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtBank.clear();
        txtBankNo.clear();
        txtMobileNo.clear();


    }



    public void setCustomerFormController(SupplierFormController supplierFormController) {
        this.supplierFormController= supplierFormController;

    }
}
