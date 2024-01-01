package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
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
import lk.ijse.entity.Payments;
import lk.ijse.entity.Supplier;
import lk.ijse.view.tdm.PaymentTm;
import lk.ijse.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class PaymentFormController {

    @FXML
    private MFXButton btnPay;

    @FXML
    private MFXButton btnSupplier;


    @FXML
    private MFXButton btnUpdate;

    @FXML
    private MFXComboBox<String> cmbSupId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private AnchorPane paymentsPane;

    @FXML
    private TableView<PaymentTm> tblPayments;

    @FXML
    private Text txtAmount;

    @FXML
    private MFXTextField txtFieldTeaLeavesPrice;

    @FXML
    private Text txtName;

    @FXML
    private Text txtPayment;

    @FXML
    private Text txtPaymentId;

    private TeaLeavesStockModel teaLeavesStockModel = new TeaLeavesStockModel();
    private SupplierModel supplierModel = new SupplierModel();

    private PaymentsModel paymentsModel = new PaymentsModel();

    private final TeaCraftDetailModel teaCraftDetailModel = new TeaCraftDetailModel();

    private PaymentTransactionModel paymentTransactionModel = new PaymentTransactionModel();


    public void initialize() {

        setCellValueFactory();
        generateNextPaymentId();
        loadSupplierIds();
        loadTeaLeavesPrice();


    }

    private void loadTeaLeavesPrice() {


        try{
            double teaLeavesPrice = teaCraftDetailModel.getTeaLeavesPrice();
            txtFieldTeaLeavesPrice.setText(String.valueOf(teaLeavesPrice));
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }


    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }



    private void loadSupplierIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try{

            List<Supplier> supplierList = supplierModel.getAllSuppliers();

            for (Supplier supplierDto : supplierList) {
                obList.add(supplierDto.getSupId());
            }

            cmbSupId.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void generateNextPaymentId() {

        try{
            String paymentId= paymentsModel.generateNextPaymentId();
            txtPaymentId.setText(paymentId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    @FXML
    void btnPayOnAction(ActionEvent event) {

        boolean isValidate = validatePayment();

        if (!isValidate){
            return;
        }

        String paymentId = txtPaymentId.getText();
        String supId = cmbSupId.getValue();
        double amount = Double.parseDouble(txtAmount.getText());
        double payment = Double.parseDouble(txtPayment.getText());
        LocalDate date = LocalDate.now();

        Payments dto = new Payments(paymentId,supId,amount,payment,date);

        try {
            boolean isSaved = paymentTransactionModel.savePayment(dto);

            if (isSaved){
                generateNextPaymentId();
                calculatePaymentAmount();
                loadAllPaymentsDetails();
                new Alert(Alert.AlertType.CONFIRMATION,"Payment Saved").show();
            }

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }




    }

    private boolean validatePayment() {


        String teaLeavesPrice = txtFieldTeaLeavesPrice.getText();

        boolean isValidateTeaLeavesPrice = Pattern.matches("[1-9][0-9]*(\\.[0-9]+)?",teaLeavesPrice);


        if (!isValidateTeaLeavesPrice){
            txtFieldTeaLeavesPrice.requestFocus();
            txtFieldTeaLeavesPrice.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFieldTeaLeavesPrice.getStyleClass().removeAll("mfx-text-field-error");


        if (Objects.equals(cmbSupId.getText(), "")){
            cmbSupId.requestFocus();
            cmbSupId.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbSupId.getStyleClass().removeAll("mfx-combo-box-error");



        return true;

    }

    private void calculatePaymentAmount() throws SQLException {

        String supId = cmbSupId.getValue();
        Supplier dto = supplierModel.searchSupplier(supId);
        txtName.setText(dto.getFirstName());

        //Calculate the amount
        double amount = teaLeavesStockModel.getTotalTeaLeavesSuppliedAmount(supId);

        txtAmount.setText(String.valueOf(amount));

        //Set Payment Amount
        double payment = amount * Double.parseDouble(txtFieldTeaLeavesPrice.getText());
        txtPayment.setText(String.valueOf(payment));

        if (amount == 0){
            btnPay.setDisable(true);
        }
        else {
            btnPay.setDisable(false);
        }


    }


    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplierForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        paymentsPane.getChildren().clear();
        paymentsPane.getChildren().add(registerPane);


    }


    @FXML
    void cmbSupIdOnAction(ActionEvent event) {

        try {
            calculatePaymentAmount();
            loadAllPaymentsDetails();
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }

    }

    private void loadAllPaymentsDetails() {

        String supId = cmbSupId.getValue();

        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try {
            List<Payments> dtoList = paymentsModel.getAllPaymentsDetails(supId);

            for (Payments dto : dtoList) {
                obList.add(new PaymentTm(
                        dto.getPaymentId(),
                        dto.getAmount(),
                        dto.getPayment(),
                        dto.getDate()));

            }

            tblPayments.setItems(obList);


        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {


        //To Validate the field
        String teaLeavesPrice = txtFieldTeaLeavesPrice.getText();

        boolean isValidateTeaLeavesPrice = Pattern.matches("[1-9][0-9]*(\\.[0-9]+)?",teaLeavesPrice);


        if (!isValidateTeaLeavesPrice){
            txtFieldTeaLeavesPrice.requestFocus();
            txtFieldTeaLeavesPrice.getStyleClass().add("mfx-text-field-error");
            return;
        }

        txtFieldTeaLeavesPrice.getStyleClass().removeAll("mfx-text-field-error");

        try {
            boolean isUpdated=   teaCraftDetailModel.updateTeaLeavesPrice(Double.parseDouble(teaLeavesPrice));

            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Tea Leaves Price Updated").show();

                if (Objects.equals(cmbSupId.getText(), "")){
                    return;
                }
                calculatePaymentAmount();

            }

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }


}
