package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.FertilizerBO;
import lk.ijse.dto.FertilizerDto;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class UpdateFertilizerController {


    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnUpdateFertilizer;

    @FXML
    private MFXTextField txtBrand;

    @FXML
    private MFXTextField txtDescription;

    @FXML
    private Text txtFertilizerId;

    @FXML
    private MFXTextField txtPrice;

    @FXML
    private MFXTextField txtQty;

    @FXML
    private MFXTextField txtSize;

    private  FertilizerFormController fertilizerFormController;

    private String fertilizerId;

    private final FertilizerBO fertilizerBO = (FertilizerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.FERTILIZER);



    @FXML
    void btnCancelOnAction(ActionEvent event) {
        Stage updateFertilizerStage = (Stage) btnCancel.getScene().getWindow();
        updateFertilizerStage.close();
    }

    @FXML
    void btnUpdateFertilizerOnAction(ActionEvent event) {

        boolean isValidated = validateFields();

        if (!isValidated){
            return;
        }

        String fertilizerId = txtFertilizerId.getText();
        String brand = txtBrand.getText();
        String description = txtDescription.getText();
        String size = txtSize.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        FertilizerDto dto= new FertilizerDto(fertilizerId, brand, description, size, price,qty );

        try {
            boolean isUpdated = fertilizerBO.updateFertilizer(dto);
            if (isUpdated){

                //Close
                Stage updateFertilizerStage = (Stage) btnCancel.getScene().getWindow();
                updateFertilizerStage.close();

                fertilizerFormController.loadAllFertilizers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private boolean validateFields() {

        String brand = txtBrand.getText();

        boolean isValidateBrand = Pattern.matches("[A-Za-z ]{3,}",brand);
        if (!isValidateBrand){
            txtBrand.requestFocus();
            txtBrand.getStyleClass().add("mfx-text-field-error");
            return false;
        }
        txtBrand.getStyleClass().removeAll("mfx-text-field-error");



        String qty = txtQty.getText();

        boolean isValidateQty = Pattern.matches("[1-9][0-9]*",qty);

        if (!isValidateQty){
            txtQty.getStyleClass().add("mfx-text-field-error");
            txtQty.requestFocus();
            return false;
        }
        txtQty.getStyleClass().removeAll("mfx-text-field-error");

        return true;

    }


    public void loadFertilizerDetails(){
        try{
            FertilizerDto dto = fertilizerBO.getFertilizer(fertilizerId);
            setField(dto);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    private void setField(FertilizerDto dto){
        txtFertilizerId.setText(dto.getFertilizerId());
        txtBrand.setText(dto.getBrand());
        txtDescription.setText(dto.getDescription());
        txtSize.setText(dto.getSize());
        txtQty.setText(String.valueOf(dto.getQty()));
        txtPrice.setText(String.valueOf(dto.getPrice()));
    }


    public void setFertilizerFormController(FertilizerFormController fertilizerFormController) {
        this.fertilizerFormController = fertilizerFormController;
    }

    public void setFertilizerId(String fertilizerId) {
        this.fertilizerId = fertilizerId;
    }
}
