package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import lk.ijse.entity.Packaging;
import lk.ijse.entity.TeaTypes;
import lk.ijse.view.tdm.PackagingDetailsTm;
import lk.ijse.model.PackagingModel;
import lk.ijse.model.TeaTypeModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class PackagingDetailsFormController {


    @FXML
    private MFXButton btnAdd;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXComboBox<String> cmbTeaType;

    @FXML
    private TableColumn<?, ?> coSize;

    @FXML
    private TableColumn<?, ?> colPackId;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<PackagingDetailsTm> tblPackDetails;

    @FXML
    private MFXTextField txtFieldPrice;

    @FXML
    private MFXTextField txtFieldSize;

    @FXML
    private Text txtPackId;;

    @FXML
    private Text txtMassage;


    @FXML
    private MFXButton btnUpdate;




    private final PackagingModel packagingModel=new PackagingModel();

    private final TeaTypeModel teaTypeModel=new TeaTypeModel();





    public void initialize() {

        generateNextPackId();
        setCellValueFactory();
        loadTeaTypes();
        loadAllPackages();
        setListener();

    }

    private void setListener() {
        tblPackDetails.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    //This Listener works every time something happened to the table
                    //So To if nothing selected and if its worked it give null point error
                    //to stop if just simply check if newValue is null
                    if (newValue == null) {
                        return;
                    }
                    PackagingDetailsTm selectedItem = new PackagingDetailsTm(
                            newValue.getPackId(),
                            newValue.getType(),
                            newValue.getSize(),
                            newValue.getPrice(),
                            newValue.getRemoveButton()
                    );
                    setFields(selectedItem);
                    setButtons(false);
                });


    }

    private void setButtons(boolean value) {
        btnAdd.setVisible(value);
        btnUpdate.setVisible(!value);
    }

    private void setFields(PackagingDetailsTm selectedItem) {
        txtPackId.setText(selectedItem.getPackId());
        cmbTeaType.selectItem(selectedItem.getType());
        txtFieldSize.setText(selectedItem.getSize());
        txtFieldPrice.setText(String.valueOf(selectedItem.getPrice()));



    }

    private void setCellValueFactory() {

        colPackId.setCellValueFactory(new PropertyValueFactory<>("packId"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        coSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

    }

    private void loadAllPackages() {

        ObservableList<PackagingDetailsTm> oblist = FXCollections.observableArrayList();

        try {

            List<Packaging> dtoList = packagingModel.getAllPackaging();

            for (Packaging dto : dtoList) {

                String type = teaTypeModel.getTeaType(dto.getTypedId());

               oblist.add(new PackagingDetailsTm(
                          dto.getPackId(),
                          type,
                          dto.getDescription(),
                          dto.getPrice()
                         )
               );
            }


            for (int i = 0; i < oblist.size(); i++) {
                final int index = i;

                oblist.get(i).getRemoveButton().setOnAction(event -> {

                    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if (type.orElse(no)==yes) {
                        String packId = dtoList.get(index).getPackId();
                        deletePackage(packId);
                    }

                });

            }
            tblPackDetails.setItems(oblist);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void deletePackage(String packId) {

        try {

            boolean isDeleted = packagingModel.deletePackage(packId);

            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Package Deleted").show();
                loadAllPackages();
                clearFields();
                generateNextPackId();
            }
        }catch (SQLException e){
            new  Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }




    }

    private void loadTeaTypes() {

        ObservableList<String> oblist = FXCollections.observableArrayList();

        try{

            List<TeaTypes> teaTypesList =  teaTypeModel.getAllTeaTypes();

            for (TeaTypes teaTypes : teaTypesList) {
                oblist.add(teaTypes.getType());
            }

            cmbTeaType.setItems(oblist);

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void generateNextPackId() {

        try{
            String lastPackId = packagingModel.generateNextPackId();
            txtPackId.setText(lastPackId);

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {

        boolean isValidate = validateFields();

        if(!isValidate){
            return;
        }


        try{

            String packId = txtPackId.getText();
            String type = cmbTeaType.getText();

            String typeId = teaTypeModel.getTeaTypeId(type);

            String size = txtFieldSize.getText();
            String price = txtFieldPrice.getText();

            Packaging dto = new Packaging(packId,typeId,size,0,Double.parseDouble(price));


            boolean isAdded = packagingModel.addPackage(dto);

            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Package Added").show();
                loadAllPackages();
                generateNextPackId();
                clearFields();
            }


        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    private void clearFields() {

        txtFieldPrice.clear();
        txtFieldSize.clear();
        cmbTeaType.clear();
        cmbTeaType.getSelectionModel().clearSelection();

    }

    private boolean validateFields() {

        if (Objects.equals(cmbTeaType.getText(), "")){
            cmbTeaType.requestFocus();
            cmbTeaType.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbTeaType.getStyleClass().removeAll("mfx-combo-box-error");


        String size = txtFieldSize.getText();

        boolean isValidateSize = Pattern.matches("([1-9]\\d*)(?:Kg|g)",size);

        if (!isValidateSize){
            txtFieldSize.requestFocus();
            txtFieldSize.getStyleClass().add("mfx-text-field-error");
            txtMassage.setVisible(true);
            return false;
        }

        txtMassage.setVisible(false);
        txtFieldSize.getStyleClass().removeAll("mfx-text-field-error");


        String price = txtFieldPrice.getText();

        boolean isValidatePrice = Pattern.matches("[0-9]{1,}",price);

        if (!isValidatePrice){
            txtFieldPrice.requestFocus();
            txtFieldPrice.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFieldPrice.getStyleClass().removeAll("mfx-text-field-error");

        return true;
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        setButtons(true);
        clearFields();
        generateNextPackId();

    }

    @FXML
    void cmbTeaTypeOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {



        boolean isValidate = validateFields();

        if(!isValidate){
            return;
        }


        try{

            String packId = txtPackId.getText();
            String type = cmbTeaType.getText();
            String typeId = teaTypeModel.getTeaTypeId(type);
            String size = txtFieldSize.getText();
            String price = txtFieldPrice.getText();


            boolean isAdded = packagingModel.updatePack(packId,typeId,size,Double.parseDouble(price));

            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Package Updated").show();
                loadAllPackages();
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }







}
