package lk.ijse.controller;


import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dto.SupplierDto;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateSupplierFormController {

    @FXML
    private MFXButton btnUpdateSupplier;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXTextField txtAddress;

    @FXML
    private MFXTextField txtBank;

    @FXML
    private MFXTextField txtBankNo;

    @FXML
    private MFXTextField txtFirstName;

    @FXML
    private MFXTextField txtLastName;

    @FXML
    private MFXTextField txtMobileNo;

    @FXML
    private Text txtSupplierId;

    private SupplierFormController supplierFormController;

    private String supId;

    private final SupplierBO supplierModel = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SUPPLIER);





    @FXML
    void btnCancelOnAction(ActionEvent event) {

        Stage updateSupplierStage = (Stage) btnCancel.getScene().getWindow();
        updateSupplierStage.close();

    }



    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {

        boolean isSupplierValidated = validateSupplier();

        if (!isSupplierValidated){
            return;
        }

        String supId = txtSupplierId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        String bank = txtBank.getText();
        String bankNo = txtBankNo.getText();
        String mobileNo = txtMobileNo.getText();

        try {
            boolean isUpdated = supplierModel.updateSupplier(new SupplierDto(supId,firstName,lastName,address,bank,bankNo,mobileNo));

            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Supplier Updated").show();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


        supplierFormController.loadAllSuppliers();

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

    public void loadSupplierDetails(){
        try {
            SupplierDto dto = supplierModel.searchSupplier(supId);
            setFields(dto);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    private void setFields(SupplierDto dto){
            txtSupplierId.setText(dto.getSupId());
            txtFirstName.setText(dto.getFirstName());
            txtLastName.setText(dto.getLastName());
            txtAddress.setText(dto.getAddress());
            txtBank.setText(dto.getBank());
            txtBankNo.setText(dto.getBankNo());
            txtMobileNo.setText(dto.getMobileNo());

    }

    public void setSupplierFormController(SupplierFormController supplierFormController) {
        this.supplierFormController= supplierFormController;
    }

    public void setSupplierId(String supId) {
        this.supId =supId;
    }


}
