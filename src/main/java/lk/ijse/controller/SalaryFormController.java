package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.AttendanceBO;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.bo.custom.SalaryBO;
import lk.ijse.bo.custom.TeaCraftDetailBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.SalaryDto;
import lk.ijse.view.tdm.SalaryTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class SalaryFormController {


    @FXML
    private MFXButton btnAttendance;

    @FXML
    private MFXButton btnEmployee;

    @FXML
    private MFXButton btnPay;


    @FXML
    private MFXButton btnUpdate;

    @FXML
    private MFXFilterComboBox<String> cmbEmployeeId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDaysCount;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private AnchorPane salaryPane;

    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private MFXTextField txtFieldHourlyRate;

    @FXML
    private MFXTextField txtFieldOt;

    @FXML
    private MFXTextField txtFieldOther;

    @FXML
    private Text txtHourlyPayment;

    @FXML
    private Text txtName;

    @FXML
    private Text txtOtPayment;

    @FXML
    private Text txtSalaryId;

    @FXML
    private Text txtTotal;

    private final EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.EMPLOYEE);

    private final AttendanceBO attendanceBO = (AttendanceBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ATTENDANCE);

    private final TeaCraftDetailBO teaCraftDetailBO = (TeaCraftDetailBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_CRAFT_DETAIL);

    private final SalaryBO salaryBO = (SalaryBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SALARY);

    public void initialize(){

        setCellValueFactory();
        generateNextSalaryId();
        loadEmployeeId();
        loadHourlyRateAndOt();


    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colDaysCount.setCellValueFactory(new PropertyValueFactory<>("daysCount"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }


    private void loadEmployeePaymentDetails(String empId){


        ObservableList<SalaryTm> obList= FXCollections.observableArrayList();

        try{

            List<SalaryDto> salaryList = salaryBO.getPaymentDetails(empId);


            for (SalaryDto dto: salaryList){

                obList.add( new SalaryTm(
                       dto.getSalaryId(),
                        dto.getDateCount(),
                        dto.getAmount(),
                        dto.getDate()
                ));
            }

            tblSalary.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    private void loadHourlyRateAndOt() {


        try{
            double hourlyRate = teaCraftDetailBO.getHourlyRate();
            double otRate = teaCraftDetailBO.getOtRate();

            txtFieldHourlyRate.setText(String.valueOf(hourlyRate));
            txtFieldOt.setText(String.valueOf(otRate));
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    private void generateNextSalaryId() {

        try{
            String salaryId= salaryBO.generateNextSalaryId();
            txtSalaryId.setText(salaryId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void loadEmployeeId() {


        ObservableList<String> employeeIdList = FXCollections.observableArrayList();

        try{

            List<EmployeeDto> employeeList = employeeBO.getAllEmployees();

            for (EmployeeDto dto : employeeList){
                employeeIdList.add(dto.getEmpId());
            }

            cmbEmployeeId.setItems(employeeIdList);


        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }


    @FXML
    void btnAttendanceOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/attendanceForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        salaryPane.getChildren().clear();
        salaryPane.getChildren().add(registerPane);


    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        salaryPane.getChildren().clear();
        salaryPane.getChildren().add(registerPane);


    }

    @FXML
    void btnPayOnAction(ActionEvent event) {

        boolean isValidate = validateFields();

        if (!isValidate){
            return;
        }

        String salaryId = txtSalaryId.getText();
        String empId = cmbEmployeeId.getValue();
        double total = Double.parseDouble(txtTotal.getText());
        LocalDate date = LocalDate.now();


        int workedDaysCount = 0;
        try {
             workedDaysCount = attendanceBO.getWorkedDaysCount(empId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


        SalaryDto dto = new SalaryDto(salaryId, empId, total,workedDaysCount, date);

        try {

            boolean isAdded = salaryBO.saveSalary(dto);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Added Successfully").show();
                loadEmployeePaymentDetails(empId);
                calculateSalary();
            }
            else {
                new Alert(Alert.AlertType.ERROR, "Payment Added Failed").show();
            }
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private boolean validateFields() {


        if (Objects.equals(cmbEmployeeId.getText(), "")){
            cmbEmployeeId.requestFocus();
            cmbEmployeeId.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbEmployeeId.getStyleClass().removeAll("mfx-combo-box-error");


        String hourlyRate = txtFieldHourlyRate.getText();

        boolean isHourlyRateValidated = Pattern.matches("[1-9][0-9]*(\\.[0-9]+)?",hourlyRate);


        if (!isHourlyRateValidated){
            txtFieldHourlyRate.requestFocus();
            txtFieldHourlyRate.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFieldHourlyRate.getStyleClass().removeAll("mfx-text-field-error");


        String otRate = txtFieldOt.getText();

        boolean isOtRateValidated = Pattern.matches("[1-9][0-9]*(\\.[0-9]+)?",otRate);

        if (!isOtRateValidated){
            txtFieldOt.requestFocus();
            txtFieldOt.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFieldOt.getStyleClass().removeAll("mfx-text-field-error");


        String other = txtFieldOther.getText();

        boolean isOtherValidated = Pattern.matches("[0-9]+(\\.[0-9]+)?",other);

        if (!isOtherValidated){
            txtFieldOther.requestFocus();
            txtFieldOther.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFieldOther.getStyleClass().removeAll("mfx-text-field-error");

        return true;

    }

    @FXML
    void cmbEmployeeIdOnAction(ActionEvent event) {

        String empId = cmbEmployeeId.getValue();

        //To remove the red border when employee is selected
        cmbEmployeeId.getStyleClass().removeAll("mfx-combo-box-error");

        try {
            loadEmployeePaymentDetails(empId);
            EmployeeDto employee = employeeBO.searchEmployee(empId);
            txtName.setText(employee.getFirstName());
            calculateSalary();

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void calculateSalary() throws SQLException {

        String empId = cmbEmployeeId.getValue();

        //Getting Date From Test Fields
        int workedHoursCount = attendanceBO.getWorkedHoursCount(empId);
        int workedDaysCount = attendanceBO.getWorkedDaysCount(empId);



        //Calculating Required Hours Count
        int requiredHoursCount= workedDaysCount * 8;


        //Calculating OT Hours Count
        int otHoursCount = workedHoursCount - requiredHoursCount;


        //Calculating Basic Payment
        double basicPayment = workedHoursCount * Double.parseDouble(txtFieldHourlyRate.getText());


        //Calculating OT Payment
        double otPayment =0;
        if (otHoursCount>0){
            otPayment = otHoursCount * Double.parseDouble(txtFieldOt.getText());
        }



        double otherPayment = Double.parseDouble(txtFieldOther.getText());

        double total = basicPayment + otPayment + otherPayment;

        txtHourlyPayment.setText(String.valueOf(basicPayment));
        txtOtPayment.setText(String.valueOf(otPayment));
        txtTotal.setText(String.valueOf(total));

        if (total==0){
            btnPay.setDisable(true);
        }
        else {
            btnPay.setDisable(false);
        }

    }

    @FXML
    void txtFieldOtherOnAction(ActionEvent event) {

        double hourlySalary = Double.parseDouble(txtHourlyPayment.getText());

        double otPayment = Double.parseDouble(txtOtPayment.getText());

        double otherPayment = Double.parseDouble(txtFieldOther.getText());

        double total = hourlySalary + otPayment + otherPayment;

        txtTotal.setText(String.valueOf(total));

    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) {

        //Validate Fields

        String hourlyRate = txtFieldHourlyRate.getText();

        boolean isHourlyRateValidated = Pattern.matches("[1-9][0-9]*(\\.[0-9]+)?",hourlyRate);


        if (!isHourlyRateValidated){
            txtFieldHourlyRate.requestFocus();
            txtFieldHourlyRate.getStyleClass().add("mfx-text-field-error");
            return ;
        }

        txtFieldHourlyRate.getStyleClass().removeAll("mfx-text-field-error");


        String otRate = txtFieldOt.getText();

        boolean isOtRateValidated = Pattern.matches("[1-9][0-9]*(\\.[0-9]+)?",otRate);

        if (!isOtRateValidated){
            txtFieldOt.requestFocus();
            txtFieldOt.getStyleClass().add("mfx-text-field-error");
            return ;
        }

        txtFieldHourlyRate.getStyleClass().removeAll("mfx-text-field-error");


        try{
            boolean isUpdated = teaCraftDetailBO.updateHourlyRateAndOt(Double.parseDouble(hourlyRate), Double.parseDouble(otRate));

            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Hourly Rate and OT Rate Updated").show();
                if (Objects.equals(cmbEmployeeId.getText(), "")){
                    return;
                }
            }

            loadHourlyRateAndOt();

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

}
