package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.FertilizerBO;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.FertilizerDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.PlaceFertilizerOrderDto;
import lk.ijse.view.tdm.FertilizerSalesCartTm;
import lk.ijse.model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class FertilizerSalesFormController {

    @FXML
    private MFXButton btnAddCart;

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnFertilizer;

    @FXML
    private MFXButton btnPlaceOrder;

    @FXML
    private MFXButton btnSales;


    @FXML
    private Text txtMassage;


    @FXML
    private MFXFilterComboBox<String> cmbCustomerId;

    @FXML
    private MFXFilterComboBox<String> cmbFertilizer;

    @FXML
    private TableColumn<?, ?> colFertilizerId;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private AnchorPane fertilizerSalesPane;

    @FXML
    private TableView<FertilizerSalesCartTm> tblCart;

    @FXML
    private Text txtDate;

    @FXML
    private Text txtDescription;

    @FXML
    private MFXTextField txtFieldQty;

    @FXML
    private Text txtName;

    @FXML
    private Text txtNetTotal;

    @FXML
    private Text txtOrderId;

    @FXML
    private MFXButton btnReceipt;


    @FXML
    private Text txtQtyOnHand;

    @FXML
    private Text txtUnitPrice;


    ArrayList<ArrayList<String>> fertilizerDetails = new ArrayList<>();


    private final ObservableList<FertilizerSalesCartTm> obList = FXCollections.observableArrayList();

    private final SupplierBO supplierModel = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SUPPLIER);

    private final FertilizerBO fertilizerBO = (FertilizerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.FERTILIZER);

    private final FertilizerOrderModel fertilizerOrderModel = new FertilizerOrderModel();

    private final PlaceFertilizerOrderModel placeFertilizerOrderModel = new PlaceFertilizerOrderModel();



    //For Report Generation
    private  String lastFertilizerOrderId;
    private  String lastSupId;
    private  String lastSupName;
    private  String lastTotal;




    public void initialize(){


        setDate();
        generateNextFertilizerOrderId();
        loadCustomerIds();
        loadFertilizerIds();
        setCellValueFactory();
        loadFertilizerDetails();


        //Disable Until Order Placed
        btnReceipt.setDisable(true);


    }

    private void loadFertilizerDetails() {

        try{

            List<FertilizerDto> dtoList = fertilizerBO.getAllFertilizers();

            for (int i = 0; i < dtoList.size(); i++){
                fertilizerDetails.add(new ArrayList<>());
                fertilizerDetails.get(i).add(dtoList.get(i).getFertilizerId());
                fertilizerDetails.get(i).add(String.valueOf(dtoList.get(i).getQty()));
            }

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }


    }

    private void setDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtDate.setText(formattedDate);
    }

    private void generateNextFertilizerOrderId() {

        try{
            String orderId =fertilizerOrderModel.generateNextFertilizerOrderId();
            txtOrderId.setText(orderId);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    private void setCellValueFactory() {
        colFertilizerId.setCellValueFactory(new PropertyValueFactory<>("fertilizerId"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

    }

    private void loadFertilizerIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            List<FertilizerDto> fertilizerList = fertilizerBO.getAllFertilizers();

            for (FertilizerDto dto : fertilizerList){
                obList.add(dto.getFertilizerId());
            }
            cmbFertilizer.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void loadCustomerIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            List<SupplierDto> supList = supplierModel.getAllSuppliers();

            for (SupplierDto dto : supList){
                obList.add(dto.getSupId());
            }
            cmbCustomerId.setItems(obList);

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }


    @FXML
    void btnAddCartOnAction(ActionEvent event) {
        
        boolean isValidated = validateFields();

        if (!isValidated){
            return;
        }
        

        String fertilizerId = cmbFertilizer.getValue();
        String description = txtDescription.getText();
        int qty = Integer.parseInt(txtFieldQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        double total = qty * unitPrice;




        for (int i = 0; i < tblCart.getItems().size(); i++) {

            if (fertilizerId.equals(colFertilizerId.getCellData(i))){

                qty += (int) colQty.getCellData(i);
                total = qty * unitPrice;

                obList.get(i).setQty(qty);
                obList.get(i).setTotal(total);

                tblCart.refresh();
                calculateNetTotal();

                decreasePackageCount(obList.get(i).getFertilizerId(), Integer.parseInt(txtFieldQty.getText()));
                itemCountRefresh();
                txtFieldQty.clear();
                return;
            }
        }


        obList.add(new FertilizerSalesCartTm(
                fertilizerId,
                description,
                qty,
                total
        ));


        cartRefresh();


        tblCart.setItems(obList);
        calculateNetTotal();
        decreasePackageCount(fertilizerId,qty);
        itemCountRefresh();
        txtFieldQty.clear();

    }

    private void cartRefresh() {

        for (int i = 0; i < obList.size(); i++) {
            final  int index = i;

            obList.get(i).getRemoveButton().setOnAction(event -> {

                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);


                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no)==yes) {
                    increasePackageCount(obList.get(index).getFertilizerId(),obList.get(index).getQty());
                    itemCountRefresh();
                    obList.remove(index);
                    cartRefresh();
                    calculateNetTotal();
                }

            });
        }



    }

    private void decreasePackageCount(String fertilizerId, int qty) {
        for (int i = 0; i < fertilizerDetails.size(); i++) {
            if (fertilizerId.equals(fertilizerDetails.get(i).get(0))){
                int count = Integer.parseInt(fertilizerDetails.get(i).get(1));
                count -= qty;
                fertilizerDetails.get(i).set(1,String.valueOf(count));
                return;
            }
        }
    }

    private void increasePackageCount(String fertilizerId, int qty) {
        for (int i = 0; i < fertilizerDetails.size(); i++) {
            if (fertilizerId.equals(fertilizerDetails.get(i).get(0))){
                int count = Integer.parseInt(fertilizerDetails.get(i).get(1));
                count += qty;
                fertilizerDetails.get(i).set(1,String.valueOf(count));
                return;
            }
        }
    }

    private void itemCountRefresh() {

        String fertilizerId = cmbFertilizer.getValue();

        if (fertilizerId == null){
            return;
        }

        int count = getFertilizerCount(fertilizerId);

        txtQtyOnHand.setText(String.valueOf(count));

    }

    private int getFertilizerCount(String fertilizerId) {

        for (int i = 0; i < fertilizerDetails.size(); i++) {
            if (fertilizerDetails.get(i).get(0).equals(fertilizerId)){
                return Integer.parseInt(fertilizerDetails.get(i).get(1));
            }
        }
        return 0;
    }

    private boolean validateFields() {

        if (Objects.equals(cmbCustomerId.getText(), "")){
            cmbCustomerId.requestFocus();
            cmbCustomerId.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbCustomerId.getStyleClass().removeAll("mfx-combo-box-error");

        if (Objects.equals(cmbFertilizer.getText(), "")){
            cmbFertilizer.requestFocus();
            cmbFertilizer.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbFertilizer.getStyleClass().removeAll("mfx-combo-box-error");

        String qty = txtFieldQty.getText();

        boolean isValidateQty = Pattern.matches("[1-9][0-9]*",qty);

        if (!isValidateQty){
            txtFieldQty.requestFocus();
            txtFieldQty.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFieldQty.getStyleClass().removeAll("mfx-text-field-error");


        int count = getFertilizerCount(cmbFertilizer.getValue());

        if (Integer.parseInt(qty)>count){
            txtMassage.setVisible(true);
            return false;
        }
        txtMassage.setVisible(false);

        return true;

    }

    private void calculateNetTotal() {

        double total = 0;

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }


        txtNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

        for (int i = 0; i < obList.size(); i++) {
            increasePackageCount(obList.get(i).getFertilizerId(),obList.get(i).getQty());
        }
        itemCountRefresh();
        obList.clear();

    }




    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {


        boolean isValidate = validateFieldsPlaceOrder();

        if (!isValidate){
            return;
        }

        String orderId = txtOrderId.getText();
        LocalDate date = LocalDate.now();
        String cusId = cmbCustomerId.getValue();


       List<FertilizerSalesCartTm> tmList = new ArrayList<>();

       for (FertilizerSalesCartTm tm : obList){
           tmList.add(tm);
       }


        PlaceFertilizerOrderDto dto = new PlaceFertilizerOrderDto(
                orderId,
                cusId,
                date,
                tmList
        );

       try{

           boolean isSuccess = placeFertilizerOrderModel.placeFertilizerOrder(dto);

           if (isSuccess){
               new Alert(Alert.AlertType.CONFIRMATION,"Order Placed Successfully").show();
               setFieldsForReport(orderId,cusId,txtName.getText(),txtNetTotal.getText());


               tblCart.getItems().clear();
               txtNetTotal.setText("-");
               txtDescription.setText("-");
               txtFieldQty.setText("0");
               cmbCustomerId.clear();
               txtUnitPrice.setText("-");
               txtName.setText("-");
               txtQtyOnHand.setText("-");
               cmbFertilizer.clear();




               generateNextFertilizerOrderId();

           }
       }catch (SQLException e){
              new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
       }

    }

    private boolean validateFieldsPlaceOrder() {

        if (Objects.equals(cmbCustomerId.getText(), "")){
            cmbCustomerId.requestFocus();
            cmbCustomerId.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbCustomerId.getStyleClass().removeAll("mfx-combo-box-error");
        return true;

    }

    private void setFieldsForReport(String orderId, String cusId, String cusName, String netTotal) {
        this.lastFertilizerOrderId = orderId;
        this.lastSupId = cusId;
        this.lastSupName = cusName;
        this.lastTotal = netTotal;

        btnReceipt.setDisable(false);
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {

        String supId= cmbCustomerId.getValue();

        try {
            SupplierDto dto = supplierModel.searchSupplier(supId);
            txtName.setText(dto.getFirstName());
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbFertilizerOnAction(ActionEvent event) {

        String fertilizerId = cmbFertilizer.getValue();

        try{
            FertilizerDto dto = fertilizerBO.searchFertilizer(fertilizerId);

            int qty = getFertilizerCount(fertilizerId);

            txtDescription.setText(dto.getDescription());
            txtQtyOnHand.setText(String.valueOf(qty));
            txtUnitPrice.setText(String.valueOf(dto.getPrice()));

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }



    @FXML
    void btnFertilizerOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fertilizerForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        fertilizerSalesPane.getChildren().clear();
        fertilizerSalesPane.getChildren().add(registerPane);
    }


    @FXML
    void btnReceiptOnAction(ActionEvent event) {

        HashMap map = new HashMap();


        map.put("supId",lastSupId);
        map.put("supName",lastSupName);
        map.put("total",lastTotal);

        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/Freport.jrxml");

            JRDesignQuery jrDesignQuery = new JRDesignQuery();


            String query = "SELECT f.brand,f.description,f.size,fod.qty FROM fertilizer_order_details fod JOIN fertilizer_orders fo ON fod.fertOid = fo.fertOid JOIN fertilizer f ON fod.fertilizerId = f.fertilizerId WHERE fod.fertOid = '"+lastFertilizerOrderId+"';";

            jrDesignQuery.setText(query);
            jasperDesign.setQuery(jrDesignQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport, //compiled report
                            map,
                            DbConnection.getInstance().getConnection() //database connection
                    );

            JFrame frame = new JFrame("Jasper Report Viewer");
            JRViewer viewer = new JRViewer(jasperPrint);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(viewer);
            frame.setSize(new Dimension(1200, 800));
            frame.setVisible(true);

        }catch (JRException | SQLException e){
            e.printStackTrace();
        }


    }


    @FXML
    void txtFieldQtyOnAction(ActionEvent event) {
        btnAddCartOnAction(event);
    }



}
