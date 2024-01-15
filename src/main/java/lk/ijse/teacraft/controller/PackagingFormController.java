package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.PackagingBO;
import lk.ijse.bo.custom.PackagingDetailsBO;
import lk.ijse.bo.custom.TeaBookBO;
import lk.ijse.bo.custom.TeaTypeBO;
import lk.ijse.dto.*;
import lk.ijse.teacraft.view.tdm.PackagingTm;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PackagingFormController {


    @FXML
    private MFXButton btnAdd;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnConfirm;

    @FXML
    private MFXButton btnSizeEdit;

    @FXML
    private MFXFilterComboBox<String> cmbDate;

    @FXML
    private MFXComboBox<String> cmbPackSize;

    @FXML
    private MFXComboBox<String> cmbTeaType;

    @FXML
    private Text txtBlack;

    @FXML
    private MFXTextField txtFieldCount;

    @FXML
    private Text txtGreen;

    @FXML
    private Text txtOolong;

    @FXML
    private Text txtSupplierId;

    @FXML
    private TableColumn<?, ?> colCount;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colPackId;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<PackagingTm> tblPackaging;



    private  final TeaBookBO teaBookBO = (TeaBookBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_BOOK);
    private final TeaTypeBO teaTypeBO = (TeaTypeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_TYPE);
    private final PackagingBO packagingBO = (PackagingBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PACKAGING);
    private final PackagingDetailsBO packagingDetailsBO = (PackagingDetailsBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PACKAGING_DETAILS);


    public void initialize() {
        loadDates();
        generateNextPackingId();
        loadTeaTypes();
        setCellValueFactory();
        loadTeaTypeAmounts();
    }

    private void setCellValueFactory() {
        colPackId.setCellValueFactory(new PropertyValueFactory<>("packagingDetailsId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

    }

    private void loadAllPackagingDetails(String date) {


        try{
            List<PackagingDetailsDto> dtoList = packagingDetailsBO.loadAllPackagingDetails(LocalDate.parse(date));

            ObservableList<PackagingTm> obList = FXCollections.observableArrayList();


            for (PackagingDetailsDto dto : dtoList){

                PackagingDto packagingDto = packagingBO.searchPackaging(dto.getPackId());

                String type = teaTypeBO.getTeaType(packagingDto.getTypedId());


                PackagingTm tm = new PackagingTm(dto.getPackagingDetailsId(), type, packagingDto.getDescription(), dto.getCount());

                obList.add(tm);
            }


            for (int i = 0; i < obList.size(); i++) {
                final int index = i;


                if (dtoList.get(index).isConfirmed()){
                    obList.get(i).getDeleteButton().setDisable(true);
                }


                obList.get(i).getDeleteButton().setOnAction(actionEvent -> {
                    String packageDetailsId= dtoList.get(index).getPackagingDetailsId();
                    deletePackageDetails(packageDetailsId);
                });


            }



            tblPackaging.setItems(obList);


        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


        
        
    }

    private void deletePackageDetails(String packageDetailsId) {

            try{

                boolean isDeleted = packagingDetailsBO.deletePackageDetails(packageDetailsId);

                if (isDeleted){
                    loadTeaTypeAmounts();
                    refreshTable();
                    new Alert(Alert.AlertType.CONFIRMATION, "Deleted").show();
                }
                else {
                    new Alert(Alert.AlertType.WARNING, "Try Again").show();
                }

            }
            catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
    }

    private void refreshTable() {
        loadAllPackagingDetails(cmbDate.getText());
    }

    private void loadTeaTypeAmounts() {

        try{


            List<TeaTypesDto> dto = teaTypeBO.getAllTeaTypes();

            double blackAmount= dto.get(0).getAmount()- packagingDetailsBO.getTotalDecreasedAmount(dto.get(0).getTypeId());
            double greenAmount= dto.get(1).getAmount()- packagingDetailsBO.getTotalDecreasedAmount(dto.get(1).getTypeId());
            double oolongAmount= dto.get(2).getAmount()- packagingDetailsBO.getTotalDecreasedAmount(dto.get(2).getTypeId());

            txtBlack.setText(blackAmount +" Kg");
            txtGreen.setText(greenAmount + " Kg");
            txtOolong.setText(oolongAmount +" Kg");
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void loadTeaTypes() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            List<TeaTypesDto> teaTypesList = teaTypeBO.getAllTeaTypes();

            for (TeaTypesDto dto : teaTypesList){
                obList.add(dto.getType());
            }
            cmbTeaType.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void generateNextPackingId() {

        try{
            String packagingId = packagingDetailsBO.generateNextPackingId();
            txtSupplierId.setText(packagingId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void loadDates() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try {

            List<TeaBookDto> teaBookList =  teaBookBO.getAllTeaBookDetails();

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
    void btnAddOnAction(ActionEvent event) {

        boolean isValidated = validateFields();

        if (!isValidated){
            return;
        }



        String packagingDetailsId = txtSupplierId.getText();
        String date = cmbDate.getSelectionModel().getSelectedItem();
        String teaType = cmbTeaType.getSelectionModel().getSelectedItem();
        String packSize = cmbPackSize.getSelectionModel().getSelectedItem();
        int count = Integer.parseInt(txtFieldCount.getText());


        try {

            //To Get Tea Type ID
            String teaTypeId = teaTypeBO.getTeaTypeId(teaType);

            double currentAmount = teaTypeBO.getTeaAmount(teaType);
            double currentPackageDetailsAmount = packagingDetailsBO.getTotalDecreasedAmount(teaTypeId);
            double decreasedAmount = calculateDecreasedAmount(packSize, count);

            //Check whether the amount is enough
            if ((currentAmount - (decreasedAmount+currentPackageDetailsAmount))<0){
                new Alert(Alert.AlertType.WARNING, "Not Enough Amount").show();
                return;
            }

            String typeId = teaTypeBO.getTeaTypeId(teaType);
            String packId = packagingBO.getPackId(typeId,packSize);



            PackagingDetailsDto dto = new PackagingDetailsDto(packagingDetailsId,LocalDate.parse( cmbDate.getText()),packId, count, decreasedAmount, false);

            boolean isAdded = packagingDetailsBO.addPackagingDetails(dto);

            if (isAdded){
                generateNextPackingId();
                loadAllPackagingDetails(cmbDate.getText());
                new Alert(Alert.AlertType.CONFIRMATION, "Added").show();
                loadTeaTypeAmounts();
            }


        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();

        }



    }

    private boolean validateFields() {

       if (Objects.equals(cmbDate.getText(), "")){
           cmbDate.requestFocus();
           cmbDate.getStyleClass().add("mfx-combo-box-error");
              return false;
       }

         cmbDate.getStyleClass().removeAll("mfx-combo-box-error");

       if (Objects.equals(cmbTeaType.getText(), "")){
           cmbTeaType.requestFocus();
           cmbTeaType.getStyleClass().add("mfx-combo-box-error");
           return false;
       }
        cmbTeaType.getStyleClass().removeAll("mfx-combo-box-error");

        if (Objects.equals(cmbPackSize.getText(), "")){
            cmbPackSize.requestFocus();
            cmbPackSize.getStyleClass().add("mfx-combo-box-error");
            return false;
        }
        cmbPackSize.getStyleClass().removeAll("mfx-combo-box-error");

        String count = txtFieldCount.getText();

        boolean isValidateCount = Pattern.matches("[1-9][0-9]*", String.valueOf(count));

        if (!isValidateCount){
            txtFieldCount.requestFocus();
            txtFieldCount.getStyleClass().add("mfx-text-field-error");
            return false;
        }
        txtFieldCount.getStyleClass().removeAll("mfx-text-field-error");

        return true;


    }

    private double calculateDecreasedAmount(String packSize, int count) {

        boolean isKexists = packSize.toUpperCase().contains("K");

        double amount = 0;

        // Create a matcher with the input string
        double convertedPackSize = 0;

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(packSize);

        // Find the first match
        if (matcher.find()) {
            convertedPackSize = Integer.parseInt(matcher.group());
        }

        if (isKexists) {
            amount = convertedPackSize * 1000;
        } else {
            amount = convertedPackSize;
        }

        return (amount * count)/1000;

    }


    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnConfirmOnAction(ActionEvent event) {

        try{

            //To Calculate the total amount and count
            List<PackagingCountAmountDto> dtoList = packagingDetailsBO.getTotalCountAmount(LocalDate.parse(cmbDate.getSelectionModel().getSelectedItem()));

            //To Update the confirmed status
            boolean isConfirmed = packagingDetailsBO.confirmPackaging(LocalDate.parse(cmbDate.getSelectionModel().getSelectedItem()),dtoList);

            if (isConfirmed){
                new Alert(Alert.AlertType.CONFIRMATION,"Confirmed").show();
                refreshTable();
            }


        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbDateOnAction(ActionEvent event) {
        loadAllPackagingDetails(cmbDate.getSelectionModel().getSelectedItem());
    }

    @FXML
    void cmbTeaTypeOnAction(ActionEvent event) {

        //To clear Selection
        cmbPackSize.getSelectionModel().clearSelection();

        String teaType= cmbTeaType.getSelectionModel().getSelectedItem();
        loadPackSizes(teaType);


    }

    private void loadPackSizes(String teaType) {



        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            String teaTypeId= teaTypeBO.getTeaTypeId(teaType);

            List<PackagingDto> packagingList = packagingBO.getAllPackaging(teaTypeId);

            for (PackagingDto dto : packagingList){
                obList.add(dto.getDescription());
            }


            cmbPackSize.setItems(obList);

        }
        catch (SQLException e){

            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }


    @FXML
    void btnSizeEditOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/packagingDetailsForm.fxml"));
        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Packaging Details");
        stage.show();
    }

    @FXML
    void txtFieldCountOnAction(ActionEvent event) {
        btnAddOnAction(event);
    }


}
