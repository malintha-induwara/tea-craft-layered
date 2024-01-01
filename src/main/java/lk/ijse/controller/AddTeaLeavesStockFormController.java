package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.entity.Supplier;
import lk.ijse.entity.TeaLeavesStock;
import lk.ijse.model.AddTeaLeavesTransactionModel;
import lk.ijse.model.SupplierModel;
import lk.ijse.model.TeaBookModel;
import lk.ijse.model.TeaLeavesStockModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class AddTeaLeavesStockFormController {


    @FXML
    private MFXButton btnAddStock;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXFilterComboBox<String> cmbSupplierId;

    @FXML
    private Text txtDate;

    @FXML
    private MFXTextField txtAmount;

    @FXML
    private Text txtTeaLeavesStockId;

    private   TeaLeavesFormController teaLeavesFormController;


    private final SupplierModel supplierModel = new SupplierModel();

    private  final TeaBookModel teaBookModel = new TeaBookModel();

    private final TeaLeavesStockModel teaLeavesStockModel = new TeaLeavesStockModel();

    private final AddTeaLeavesTransactionModel addTeaLeavesTransactionModel = new AddTeaLeavesTransactionModel();



    public void initialize(){
        loadSupplierIds();
        generateNextTeaLeavesStockId();

    }

    private void generateNextTeaLeavesStockId() {


        try{
            String teaLeavesStockId = teaLeavesStockModel.generateNextTeaLeavesStockId();
            txtTeaLeavesStockId.setText(teaLeavesStockId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private void loadSupplierIds() {

        ObservableList <String> obList = FXCollections.observableArrayList();

        try{
            List<Supplier> supplierList =  supplierModel.getAllSuppliers();

            for (Supplier supplier : supplierList){
                obList.add(supplier.getSupId());
            }

            cmbSupplierId.setItems(obList);

        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }



    }


    @FXML
    void btnAddStockOnAction(ActionEvent event) {

        boolean isValidated = validateFields();

        if (!isValidated){
            return;
        }

        try {
            String teaStockId = txtTeaLeavesStockId.getText();
            String supId = cmbSupplierId.getText();
            String teaBookId = teaBookModel.getTeaBookId(txtDate.getText());
            double amount = Double.parseDouble(txtAmount.getText());

            TeaLeavesStock teaLeavesStock = new TeaLeavesStock(teaStockId,supId,teaBookId,amount,false);
            boolean isSaved = addTeaLeavesTransactionModel.saveTeaLeavesStock(teaLeavesStock,teaBookId);


            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Stock Added").show();

                clearFields();

                if (teaLeavesFormController != null) {
                    teaLeavesFormController.setCurrentDailyAmount(txtDate.getText());
                    teaLeavesFormController.loadAllStockDetails(txtDate.getText());
                }
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private boolean validateFields() {

        String amount = txtAmount.getText();

        boolean isAmountValidated = Pattern.matches("[0-9]{1,}", String.valueOf(amount));


        if (Objects.equals(cmbSupplierId.getText(), "")){
            cmbSupplierId.requestFocus();
            cmbSupplierId.getStyleClass().add("mfx-combo-box-error");
            return false;
        }
        cmbSupplierId.getStyleClass().removeAll("mfx-combo-box-error");



        if (!isAmountValidated){
            txtAmount.requestFocus();
            txtAmount.getStyleClass().add("mfx-text-field-error");
            return false;
        }
        txtAmount.getStyleClass().removeAll("mfx-text-field-error");

        return true;
    }

    private void clearFields() {

        generateNextTeaLeavesStockId();
        txtAmount.clear();
        cmbSupplierId.getSelectionModel().clearSelection();



    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage addCustomereStage= (Stage) btnCancel.getScene().getWindow();
        addCustomereStage.close();

    }


    public void setTeaLeavesFormController(TeaLeavesFormController teaLeavesFormController) {
            this.teaLeavesFormController=teaLeavesFormController;
    }

    public void setDate(String date){
        txtDate.setText(date);

    }

}
