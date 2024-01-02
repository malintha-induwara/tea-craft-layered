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

public class AddFertilizerFormController {

    @FXML
    private MFXButton btnAddFertilizer;

    @FXML
    private MFXButton btnCancel;

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


    private final FertilizerBO fertilizerBO = (FertilizerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.FERTILIZER);

    public void initialize(){
        generateNextFertilizerId();
    }

    @FXML
    void btnAddFertilizerOnAction(ActionEvent event) {

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

        FertilizerDto dto= new FertilizerDto(fertilizerId, brand, description, size, price, qty);

        try {
            boolean isAdded = fertilizerBO.addFertilizer(dto);
            if (isAdded){
                new Alert(Alert.AlertType.CONFIRMATION,"Added Success").show();
                clearFields();

                if (fertilizerFormController != null){
                    fertilizerFormController.loadAllFertilizers();
                }

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

    private void clearFields() {
        generateNextFertilizerId();
        txtBrand.clear();
        txtDescription.clear();
        txtSize.clear();
        txtQty.clear();
        txtPrice.clear();
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

        Stage addFertilizerStage= (Stage) btnCancel.getScene().getWindow();
        addFertilizerStage.close();

    }

    private void generateNextFertilizerId() {

        try {
            String fertilizerId = fertilizerBO.generateNextFertilizerId();
            txtFertilizerId.setText(fertilizerId);
        } catch (SQLException e) {
             new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }




    public void setCustomerFormController(FertilizerFormController fertilizerFormController) {
        this.fertilizerFormController= fertilizerFormController;

    }
}
