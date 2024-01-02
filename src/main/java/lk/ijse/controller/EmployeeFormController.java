
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.view.tdm.EmployeeTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeFormController {



    @FXML
    private MFXButton btnAddEmployee;


    @FXML
    private MFXButton btnAttendance;

    @FXML
    private MFXButton btnSalary;


    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDelete;


    @FXML
    private TableColumn<?, ?> colSex;


    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colFirstName;

    @FXML
    private TableColumn<?, ?> colLastName;

    @FXML
    private TableColumn<?, ?> colMobileNo;

    @FXML
    private TableColumn<?, ?> colDateOfBirth;

    @FXML
    private TableColumn<?, ?> colUpdate;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private AnchorPane employeePane;

    @FXML
    private MFXTextField txtSearch;


    private final EmployeeBO employeeModel= (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.EMPLOYEE);


    public void  initialize(){
        setCellValuesFactory();
        loadAllEmployees();
    }


    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/addEmployeeForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
        AddEmployeeFormController addEmployeeFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        addEmployeeFormController.setCustomerFormController(this);

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Employee");
        stage.show();



    }


    @FXML
    void btnAttendanceOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/attendanceForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        employeePane.getChildren().clear();
        employeePane.getChildren().add(registerPane);


    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/salaryForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        employeePane.getChildren().clear();
        employeePane.getChildren().add(registerPane);

    }



    private void setCellValuesFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colMobileNo.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

    }


    public void loadAllEmployees() {

        ObservableList <EmployeeTm> obList = FXCollections.observableArrayList();

        try {

            List<EmployeeDto>  dtoList = employeeModel.getAllEmployees();


            for (EmployeeDto dto : dtoList) {

                obList.add(new EmployeeTm(
                        dto.getEmpId(),
                        dto.getFirstName(),
                        dto.getLastName(),
                        dto.getAddress(),
                        dto.getSex(),
                        dto.getDateOfBirth(),
                        dto.getMobileNo()
                ));
            }


            for (int i = 0; i < obList.size(); i++) {
                final int index = i;

                obList.get(i).getUpdateButton().setOnAction(event -> {
                    try {
                        updateEmployee(dtoList.get(index).getEmpId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


                obList.get(i).getDeleteButton().setOnAction(event ->{

                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to delete this employee?", yes, no).showAndWait();

                    if(type.orElse(no) == yes){

                        String empId = dtoList.get(index).getEmpId();
                        deleteEmployee(empId);
                        loadAllEmployees();
                    }
                });

            }


            tblEmployee.setItems(obList);


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private void deleteEmployee(String empId) {

        try{

            boolean isDeleted = employeeModel.deleteEmployee(empId);

            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted").show();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void updateEmployee(String empId) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/updateEmployeeForm.fxml"));
        Parent rootNode = loader.load();
        UpdateEmployeeFormController updateEmployeeFormController = loader.getController();

        updateEmployeeFormController.setEmployeeFormController(this);
        updateEmployeeFormController.setEmployeeId(empId);
        updateEmployeeFormController.loadEmployeeDetails();

        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Employee");
        stage.show();


    }




    @FXML
    void txtSearchOnAction(ActionEvent event) {

        FilteredList<EmployeeTm> filteredData = new FilteredList<>(tblEmployee.getItems(), b -> true);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employeeTm -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                return employeeTm.getEmpId().toLowerCase().contains(searchKeyword) ||
                        employeeTm.getFirstName().toLowerCase().contains(searchKeyword) ||
                        employeeTm.getLastName().toLowerCase().contains(searchKeyword) ||
                        employeeTm.getAddress().toLowerCase().contains(searchKeyword) ||
                        employeeTm.getSex().toLowerCase().contains(searchKeyword) ||
                        employeeTm.getDateOfBirth().toLowerCase().contains(searchKeyword) ||
                        employeeTm.getMobileNo().toLowerCase().contains(searchKeyword);
            });
        });

        tblEmployee.setItems(filteredData);



    }



    }

