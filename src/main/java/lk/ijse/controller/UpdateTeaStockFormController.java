package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.bo.custom.TeaBookBO;
import lk.ijse.bo.custom.TeaLeavesStockBO;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.TeaLeavesStockDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class UpdateTeaStockFormController {



    @FXML
    private MFXButton btnUpdateStock;

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


    private final TeaLeavesStockBO teaLeavesStockBO = (TeaLeavesStockBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_LEAVES_STOCK);

    private  final TeaBookBO teaBookBO = (TeaBookBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_BOOK);

    private final SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SUPPLIER);

    private TeaLeavesFormController teaLeavesFormController;

    public void initialize(){
        loadSupplierIds();

    }

    private void loadSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            List<SupplierDto> supplierList =  supplierBO.getAllSuppliers();

            for (SupplierDto supplier : supplierList){
                obList.add(supplier.getSupId());
            }

            cmbSupplierId.setItems(obList);

        }
        catch (SQLException e){
            throw  new RuntimeException(e);
        }





    }


    @FXML
    void btnUpdateStockOnAction(ActionEvent event) {



        boolean isValidated = validateFields();

        if (!isValidated){
            return;
        }


        String teaStockId = txtTeaLeavesStockId.getText();
        String supId = cmbSupplierId.getText();
        String date = txtDate.getText();
        double amount = Double.parseDouble(txtAmount.getText());

        try {

            String teaBookId = teaBookBO.getTeaBookId(date);

            boolean isUpdated = teaLeavesStockBO.updateTeaLeavesStock(new TeaLeavesStockDto(teaStockId,supId,teaBookId,amount,false));
            //Update Daily Amount On UI
            double dailyAmount= teaLeavesStockBO.getTotalAmount(teaBookId);

            //Update Database
            teaBookBO.updateTeaBookAmount(teaBookId,dailyAmount);

            if (teaLeavesFormController != null) {
                teaLeavesFormController.setCurrentDailyAmount(date);
                teaLeavesFormController.loadAllStockDetails(date);
            }
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Updated Successfully").show();



            }

        }
        catch (SQLException e){
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

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage updateSupplierStage = (Stage) btnCancel.getScene().getWindow();
        updateSupplierStage.close();

    }

    public void loadTeaLeavesStockDetails(){

        try{
            TeaLeavesStockDto dto= teaLeavesStockBO.searchTeaLeavesStock(txtTeaLeavesStockId.getText());
            setFields(dto);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }




    }

    private void setFields(TeaLeavesStockDto teaLeavesStock) {

        String date = null;

        try{
            date = teaBookBO.getTeaBookDate(teaLeavesStock.getTeaBookId());
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        txtTeaLeavesStockId.setText(teaLeavesStock.getTeaStockId());
        cmbSupplierId.setText(teaLeavesStock.getSupId());
        txtAmount.setText(String.valueOf(teaLeavesStock.getAmount()));
        txtDate.setText(date);



    }

    public void setTeaLeavesFormController(TeaLeavesFormController teaLeavesFormController){
        this.teaLeavesFormController= teaLeavesFormController;
    }

    public void setTeaLeavesStockId(String teaLeavesStockId){
        this.txtTeaLeavesStockId.setText(teaLeavesStockId);
    }



}
