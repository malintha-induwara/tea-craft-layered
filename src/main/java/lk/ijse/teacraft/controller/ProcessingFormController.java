package lk.ijse.teacraft.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lk.ijse.teacraft.bo.BOFactory;
import lk.ijse.teacraft.bo.custom.ProcessingBO;
import lk.ijse.teacraft.bo.custom.TeaBookBO;
import lk.ijse.teacraft.bo.custom.TeaBookTypeBO;
import lk.ijse.teacraft.bo.custom.TeaTypeBO;
import lk.ijse.teacraft.dto.TeaBookDto;
import lk.ijse.teacraft.dto.TeaBookTypeDetailDto;
import lk.ijse.teacraft.dto.TeaBookTypeDto;
import lk.ijse.teacraft.dto.TeaTypesDto;
import lk.ijse.teacraft.view.tdm.TeaBookTypeTm;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ProcessingFormController {



    @FXML
    private MFXButton btnCancel;


    @FXML
    private MFXButton btnConfirm;

    @FXML
    private MFXFilterComboBox<String> cmbDate;

    @FXML
    private MFXComboBox<String> cmbType;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView <TeaBookTypeTm> tblProcessing;

    @FXML
    private MFXTextField txtAmount;


    @FXML
    private Text txtTeaBookTypeId;



    private final TeaBookTypeBO teaBookTypeBO = (TeaBookTypeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_BOOK_TYPE);

    private  final TeaBookBO teaBookBO = (TeaBookBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_BOOK);

    private final TeaTypeBO teaTypeBO = (TeaTypeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_TYPE);

    private final ProcessingBO processingBO = (ProcessingBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PROCESSING);



    public void initialize(){
        loadDates();
        loadTypes();
        setCellValueFactory();
        generateNextTeaBookTypeId();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("teaBookTypeId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));
    }

    private void loadAllProcessingDetails(String date) {


        try {

            List<TeaBookTypeDto> dtoList = teaBookTypeBO.getAllTeaBookTypeDetails(date);

            ObservableList <TeaBookTypeTm> obList = FXCollections.observableArrayList();


            for (TeaBookTypeDto dto :dtoList){

                //Getting The Name from databace
                String teaType= teaTypeBO.getTeaType(dto.getTypeId());

                TeaBookTypeTm tm = new TeaBookTypeTm(dto.getTeaBookTypeId(),teaType,dto.getAmount());
                obList.add(tm);
            }


            for (int i = 0; i < obList.size(); i++) {
                final int index =i;


                //To Disable the delete button if already confirmed

                if (dtoList.get(index).isConfirmed()){
                    obList.get(i).getDeleteButton().setDisable(true);
                }



                obList.get(i).getDeleteButton().setOnAction(actionEvent -> {
                    String teaBookTypeId= dtoList.get(index).getTeaBookTypeId();
                    deleteTeaBookType(teaBookTypeId);
                });
            }
            tblProcessing.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }



    }

    private void refreshTable() {
        loadAllProcessingDetails(cmbDate.getText());
    }

    private void deleteTeaBookType(String teaBookTypeId) {


        try{
            boolean isDeleted = teaBookTypeBO.deleteTeaBookType(teaBookTypeId);

            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                refreshTable();
            }
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void generateNextTeaBookTypeId() {

        try{
            String teaBookTypeId = teaBookTypeBO.generateNextTeaBookTypeId();
            txtTeaBookTypeId.setText(teaBookTypeId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private void loadTypes() {
        ObservableList <String> obList = FXCollections.observableArrayList();

        try {
            List<TeaTypesDto> teaTypesList = teaTypeBO.getAllTeaTypes();

            for (TeaTypesDto teaTypes : teaTypesList) {
                obList.add(teaTypes.getType());
            }

            cmbType.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

    private void loadDates() {

        ObservableList <String> obList = FXCollections.observableArrayList();

        try {

            List <TeaBookDto> teaBookList =  teaBookBO.getAllTeaBookDetails();

            for (TeaBookDto teaBookDto : teaBookList) {
                obList.add(teaBookDto.getDate());
            }
            cmbDate.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }



    }


    @FXML
    void btnCancelOnAction(ActionEvent event) {
            txtAmount.clear();
            cmbType.getSelectionModel().clearSelection();
            cmbType.clear();
            cmbDate.getSelectionModel().clearSelection();
            cmbDate.clear();

    }

    @FXML
    void btnAddDetailsOnAction(ActionEvent event) {


        boolean isValidated = validateFields();

        if (!isValidated){
            return;
        }



        String teaBookTypeId = txtTeaBookTypeId.getText();
        LocalDate date = LocalDate.parse(cmbDate.getText());
        String type = cmbType.getText();
        double amount = Double.parseDouble(txtAmount.getText());


        try {

            String teaTypeId= teaTypeBO.getTeaTypeId(type);
            TeaBookTypeDto dto = new TeaBookTypeDto(teaBookTypeId,date,teaTypeId,amount,false);

            boolean isSaved = teaBookTypeBO.saveTeaBookType(dto);

            if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"Details Saved").show();
                    refreshTable();
                    clearFields();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }



    }

    private boolean validateFields() {

            if (Objects.equals(cmbDate.getText(), "")){
                cmbDate.requestFocus();
                cmbDate.getStyleClass().add("mfx-combo-box-error");
                return false;
            }
            cmbDate.getStyleClass().removeAll("mfx-combo-box-error");


            if (Objects.equals(cmbType.getText(), "")){
                cmbType.requestFocus();
                cmbType.getStyleClass().add("mfx-combo-box-error");
                return false;
            }
            cmbType.getStyleClass().removeAll("mfx-combo-box-error");

            String amount = txtAmount.getText();

            boolean isValidateAmount = Pattern.matches("[0-9]{1,}", String.valueOf(amount));

            if (!isValidateAmount){
                txtAmount.requestFocus();
                txtAmount.getStyleClass().add("mfx-text-field-error");
                return false;
            }

            txtAmount.getStyleClass().removeAll("mfx-text-field-error");

            return true;
    }

    private void clearFields() {
        generateNextTeaBookTypeId();
        cmbType.getSelectionModel().clearSelection();
        txtAmount.clear();

    }


    @FXML
    void cmbDateOnAction(ActionEvent event) {
        loadAllProcessingDetails(cmbDate.getSelectionModel().getSelectedItem());
    }


    @FXML
    void btnConfirmOnAction(ActionEvent event) {


        try{

            //To Calculate the total amount
            List<TeaBookTypeDetailDto> dtoList = teaBookTypeBO.getTotalAmount(LocalDate.parse(cmbDate.getValue()));

            //To Update the confirmed status
            boolean isConfirmed = processingBO.updateDetails(LocalDate.parse(cmbDate.getSelectionModel().getSelectedItem()),dtoList);


            if (isConfirmed){
                new Alert(Alert.AlertType.CONFIRMATION,"Confirmed").show();
                refreshTable();

            }

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }




    }


    @FXML
    void txtAmountOnAction(ActionEvent event) {
        btnAddDetailsOnAction(event);

    }


}
