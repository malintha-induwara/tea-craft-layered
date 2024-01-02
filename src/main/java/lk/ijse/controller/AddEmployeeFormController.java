package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Employee;
import lk.ijse.model.EmployeeModel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

public class AddEmployeeFormController {


    @FXML
    private MFXButton btnAddEmployee;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXDatePicker dpDateOfBirth;

    @FXML
    private MFXTextField txtAddress;

    @FXML
    private Text txtEmployeeId;

    @FXML
    private MFXTextField txtFirstName;

    @FXML
    private MFXTextField txtLastName;

    @FXML
    private MFXTextField txtMobileNo;

    @FXML
    private MFXComboBox<String> cmbSex;


    private EmployeeFormController employeeFormController;


    private final EmployeeBO employeeModel= (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize(){
        generateNextEmployeeId();
        setGender();
    }

    private void setGender() {
        cmbSex.getItems().addAll("Male", "Female");
    }


    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {

        boolean isEmployeeValidated = validateEmployee();

        if (!isEmployeeValidated){
            return;
        }

        String empId= txtEmployeeId.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String address = txtAddress.getText();
        String sex = cmbSex.getText();
        String dateOfBirth = dpDateOfBirth.getValue().toString();
        String mobileNo = txtMobileNo.getText();


        var dto = new EmployeeDto(empId,firstName,lastName,address,sex,dateOfBirth,mobileNo);

        try {

            boolean isSaved = employeeModel.saveEmployee(dto);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Saved").show();
                clearFields();

                if (employeeFormController != null){
                    employeeFormController.loadAllEmployees();
                }


            }



        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }


    }

    private boolean validateEmployee() {

        String firstName = txtFirstName.getText();
        boolean isFirstNameValid = Pattern.matches("[A-Za-z]{3,}",firstName);
        if (!isFirstNameValid){
            txtFirstName.requestFocus();
            txtFirstName.getStyleClass().removeAll("mfx-text-field-error");
            return false;
        }

        txtFirstName.getStyleClass().removeAll("mfx-text-field-error");


        String lastName = txtLastName.getText();
        boolean isLastNameValid = Pattern.matches("[A-Za-z]{3,}",lastName);
        if (!isLastNameValid){
            txtLastName.requestFocus();
            txtLastName.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtLastName.getStyleClass().removeAll("mfx-text-field-error");


        String address = txtAddress.getText();
        boolean isAddressValid = Pattern.matches("[A-Za-z0-9/ ]{3,}",address);
        if (!isAddressValid){
            txtAddress.requestFocus();
            txtAddress.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtAddress.getStyleClass().removeAll("mfx-text-field-error");

        String dob = dpDateOfBirth.getText();


        if (Objects.equals(cmbSex.getText(), "")){
            cmbSex.requestFocus();
            cmbSex.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbSex.getStyleClass().removeAll("mfx-combo-box-error");



        boolean isDobValid = Pattern.matches("(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s\\d{1,2},\\s\\d{4}",dob);


        if (!isDobValid){
            dpDateOfBirth.requestFocus();
            dpDateOfBirth.getStyleClass().add("mfx-text-field-error");
            return false;
        }


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







    private void clearFields() {

        generateNextEmployeeId();
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        cmbSex.getSelectionModel().clearSelection();
        cmbSex.clear();
        dpDateOfBirth.setValue(LocalDate.now());
        txtMobileNo.clear();

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage addCustomereStage= (Stage) btnCancel.getScene().getWindow();
        addCustomereStage.close();

    }

    private void generateNextEmployeeId() {

        try {
            String employeeId = employeeModel.generateNextEmployeeId();
            txtEmployeeId.setText(employeeId);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }
    }


    public void setCustomerFormController(EmployeeFormController employeeFormController) {
        this.employeeFormController=employeeFormController;
    }
}
