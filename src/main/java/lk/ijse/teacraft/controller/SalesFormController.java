package lk.ijse.controller;

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
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.PackagingBO;
import lk.ijse.bo.custom.TeaOrderBO;
import lk.ijse.bo.custom.TeaTypeBO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.PackagingDto;
import lk.ijse.dto.TeaTypesDto;
import lk.ijse.dto.PlaceTeaOrderDto;
import lk.ijse.teacraft.view.tdm.SalesCartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class SalesFormController {

    @FXML
    private MFXButton btnAddCart;

    @FXML
    private MFXButton btnCancel;


    @FXML
    private MFXButton btnPlaceOrder;

    @FXML
    private MFXFilterComboBox<String> cmbCustomerNum;

    @FXML
    private MFXComboBox<String> cmbPackSize;

    @FXML
    private MFXComboBox<String> cmbTeaType;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colRemove;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private TableView<SalesCartTm> tblCart;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private MFXButton btnReceipt;



    @FXML
    private Text txtCount;

    @FXML
    private Text txtDate;

    @FXML
    private MFXTextField txtFieldQty;

    @FXML
    private Text txtName;

    @FXML
    private Text txtNetTotal;

    @FXML
    private Text txtOrderId;

    @FXML
    private Text txtPrice;


    @FXML
    private Text txtMassage;


    ArrayList<ArrayList<String> >packageDetails = new ArrayList<>();

    private final ObservableList<SalesCartTm> obList = FXCollections.observableArrayList();

    private  final CustomerBO customerBO = (CustomerBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.CUSTOMER);
    private final TeaTypeBO teaTypeBO = (TeaTypeBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_TYPE);
    private final PackagingBO packagingBO = (PackagingBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PACKAGING);
    private final TeaOrderBO teaOrderBO = (TeaOrderBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.TEA_ORDER);


    //For Report Generation
    private String lastOrderId;
    private String lastCusId;
    private String lastCusName;
    private String lastTotal;



    public void initialize() {

        setCurrentDate();
        generateNextOrderId();
        loadCustomerIds();
        loadTeaTypes();
        setCellValueFactory();
        loadPackageDetails();


        //Disable Until Order Placed
        btnReceipt.setDisable(true);

    }

    private void loadPackageDetails() {

        try{

            List<PackagingDto> dtoList = packagingBO.getAllPackaging();

            for (int i = 0; i < dtoList.size(); i++) {
                packageDetails.add(new ArrayList<>());
                packageDetails.get(i).add(dtoList.get(i).getPackId());
                packageDetails.get(i).add(String.valueOf(dtoList.get(i).getPackageCount()));
            }



        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void setCellValueFactory() {

        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colRemove.setCellValueFactory(new PropertyValueFactory<>("removeButton"));

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

    private void loadCustomerIds() {

        ObservableList<String> obList = FXCollections.observableArrayList();

        try{
            List<CustomerDto> cusList = customerBO.getAllCustomer();
            for (CustomerDto dto : cusList){
                obList.add(dto.getMobileNo());
            }
            cmbCustomerNum.setItems(obList);

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }



    }

    private void generateNextOrderId() {

        try{
            String orderId = teaOrderBO.generateNextOrderId();
            txtOrderId.setText(orderId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage()).show();
        }

    }

    private void setCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(formatter);
        txtDate.setText(formattedDate);
    }


    @FXML
    void btnAddCartOnAction(ActionEvent event) {

        boolean isValidate = validateFields();

        if (!isValidate){
            return;
        }


        String teaType = cmbTeaType.getSelectionModel().getSelectedItem();
        String packSize = cmbPackSize.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(txtFieldQty.getText());

        double unitPrice = Double.parseDouble(txtPrice.getText());
        double total = qty * unitPrice;




        for (int i = 0; i < tblCart.getItems().size(); i++) {

            if (teaType.equals(colType.getCellData(i)) && packSize.equals(colSize.getCellData(i))) {

                qty += (int) colQty.getCellData(i);
                total = qty * unitPrice;

                obList.get(i).setQty(qty);
                obList.get(i).setTotal(total);

                tblCart.refresh();
                calculateNetTotal();

                decreasePackageCount(obList.get(i).getPackId(),Integer.parseInt(txtFieldQty.getText()));
                itemCountRefresh();
                txtFieldQty.clear();
                return;
            }

        }

        //To find the pack id
        String packId = null;
        try {
            String teaTypeId = teaTypeBO.getTeaTypeId(teaType);
             packId= packagingBO.getPackId(teaTypeId,packSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        obList.add(new SalesCartTm(
                packId,
                teaType,
                packSize,
                qty,
                total
        ));

        cartRefresh();



        tblCart.setItems(obList);
        calculateNetTotal();
        decreasePackageCount(packId,qty);
        itemCountRefresh();
        txtFieldQty.clear();


    }

    private void itemCountRefresh(){

        String teaType = cmbTeaType.getValue();
        String packSize = cmbPackSize.getValue();

        if (teaType==null || packSize==null){
            return;
        }

        try {
            //Getting Tea Type id
            String teaTypeId = teaTypeBO.getTeaTypeId(teaType);


            //Finding Pack id According to Tea Type id and Pack Size
            String packId = packagingBO.getPackId(teaTypeId,packSize);

            PackagingDto dto = packagingBO.searchPackaging(packId);

            //Get Details From Packaging Details Array List

            int count = getPackageCount(packId);
            //Set Values
            txtCount.setText(String.valueOf(count));

        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    private void increasePackageCount(String packId, int qty) {
        for (int i = 0; i < packageDetails.size(); i++) {
            if (packageDetails.get(i).get(0).equals(packId)){
                int count = Integer.parseInt(packageDetails.get(i).get(1));
                count += qty;
                packageDetails.get(i).set(1,String.valueOf(count));
                return;
            }
        }


    }

    private void decreasePackageCount(String packId, int qty) {
        for (int i = 0; i < packageDetails.size(); i++) {
            if (packageDetails.get(i).get(0).equals(packId)){
                int count = Integer.parseInt(packageDetails.get(i).get(1));
                count -= qty;
                packageDetails.get(i).set(1,String.valueOf(count));
                return;
            }
        }
    }

    private void cartRefresh() {
        for (int i = 0; i < obList.size(); i++) {
            final int index = i ;

            obList.get(i).getRemoveButton().setOnAction(e -> {


                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);


                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no)==yes) {
                    increasePackageCount(obList.get(index).getPackId(),obList.get(index).getQty());
                    itemCountRefresh();
                    obList.remove(index);
                    cartRefresh();
                    calculateNetTotal();
                }
            });
        }

    }


    private boolean validateFields() {

        if(Objects.equals(cmbCustomerNum.getText(), "")){
            cmbCustomerNum.requestFocus();
            cmbCustomerNum.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbCustomerNum.getStyleClass().removeAll("mfx-combo-box-error");

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



        String qty = txtFieldQty.getText();

        boolean isValidateQty = Pattern.matches("[1-9][0-9]*", String.valueOf(qty));

        if (!isValidateQty){
            txtFieldQty.requestFocus();
            txtFieldQty.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtFieldQty.getStyleClass().removeAll("mfx-text-field-error");


        //To Validate Qty

        String teaType = cmbTeaType.getSelectionModel().getSelectedItem();
        String packSize = cmbPackSize.getSelectionModel().getSelectedItem();


        int count = 0;

        try{
            //Getting Tea Type id
            String teaTypeId = teaTypeBO.getTeaTypeId(teaType);

            //Finding Pack id According to Tea Type id and Pack Size
            String packId = packagingBO.getPackId(teaTypeId,packSize);

            //Get Details From Packaging Details Array List
             count = getPackageCount(packId);
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        if (Integer.parseInt(qty)>count){
            txtMassage.setVisible(true);
            return false;
        }

        txtMassage.setVisible(false);

        return true;

    }

    private void calculateNetTotal() {

        double total =0;

        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        txtNetTotal.setText(String.valueOf(total));

    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        for (int i = 0; i < obList.size(); i++) {
            increasePackageCount(obList.get(i).getPackId(),obList.get(i).getQty());
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
        String cusNum = cmbCustomerNum.getValue();

        String cusId = null;
        try {
            cusId = customerBO.searchCustomerID(cusNum);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        LocalDate date = LocalDate.now();


        List<SalesCartTm> tmList = new ArrayList<>();

        for (SalesCartTm cartTm : obList) {
            tmList.add(cartTm);
        }

        PlaceTeaOrderDto dto = new PlaceTeaOrderDto(
                orderId,
                cusId,
                date,
                tmList
        );


        try{

            boolean isSuccess = teaOrderBO.placeOrder(dto);

            if (isSuccess){
                new Alert(Alert.AlertType.CONFIRMATION, "Order Completed").show();
                setFieldsForReport(orderId,cusId,txtName.getText(),txtNetTotal.getText());
                tblCart.getItems().clear();

                txtNetTotal.setText("-");
                txtCount.setText("-");
                txtPrice.setText("-");
                txtFieldQty.setText("0");
                cmbTeaType.clear();
                cmbPackSize.clear();
                cmbCustomerNum.clear();

                generateNextOrderId();


            }
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

    private boolean validateFieldsPlaceOrder() {

        if(Objects.equals(cmbCustomerNum.getText(), "")){
            cmbCustomerNum.requestFocus();
            cmbCustomerNum.getStyleClass().add("mfx-combo-box-error");
            return false;
        }

        cmbCustomerNum.getStyleClass().removeAll("mfx-combo-box-error");


        return true;


    }

    private void setFieldsForReport(String orderId, String cusId, String name, String total) {

        //Active the receipt Button
        btnReceipt.setDisable(false);

        this.lastCusId = cusId;
        this.lastCusName = name;
        this.lastOrderId = orderId;
        this.lastTotal = total;
    }


    @FXML
    void cmbCustomerOnAction(ActionEvent event) {

        String cusNum= cmbCustomerNum.getValue();

        try {
            String cusId = customerBO.searchCustomerID(cusNum);
            CustomerDto dto = customerBO.searchCustomer(cusId);
            txtName.setText(dto.getFirstName());
        }
        catch (SQLException  e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    @FXML
    void cmbTeaTypeOnAction(ActionEvent event) {

        //To clear Selection
        cmbPackSize.getSelectionModel().clearSelection();
        txtCount.setText("-");
        txtPrice.setText("-");

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
    void cmbPackSizeOnAction(ActionEvent event) {

        String teaType = cmbTeaType.getSelectionModel().getSelectedItem();
        String packSize = cmbPackSize.getSelectionModel().getSelectedItem();

        //Had to use this because when the user selects a tea type and pack size and then selects another tea type it automatically called
        //This Action method so the packSize is going null
        if (packSize== null){
            return;
        }

        try{

            //Getting Tea Type id
            String teaTypeId = teaTypeBO.getTeaTypeId(teaType);


            //Finding Pack id According to Tea Type id and Pack Size
            String packId = packagingBO.getPackId(teaTypeId,packSize);

            PackagingDto dto = packagingBO.searchPackaging(packId);


            //Get Details From Packaging Details Array List
            int count = getPackageCount(packId);



            //Set Values
            txtCount.setText(String.valueOf(count));
            txtPrice.setText(String.valueOf(dto.getPrice()));


        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private int getPackageCount(String packId) {

        for (int i = 0; i < packageDetails.size(); i++) {
            if (packageDetails.get(i).get(0).equals(packId)){
                return Integer.parseInt(packageDetails.get(i).get(1));
            }

        }
        return 0;
    }

    @FXML
    void btnReceiptOnAction(ActionEvent event)  {

        HashMap map = new HashMap();

        map.put("cusId",lastCusId);
        map.put("cusName",lastCusName);
        map.put("total",lastTotal);


        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/Jreport.jrxml");

            JRDesignQuery jrDesignQuery = new JRDesignQuery();


            String query = "SELECT tea_types.type AS Tea_Type, packaging.description AS Description, tea_order_details.qty AS Qty\n" +
                    "FROM tea_order_details\n" +
                    "         JOIN packaging ON tea_order_details.packId = packaging.packId\n" +
                    "         JOIN tea_types ON packaging.typeId = tea_types.typeId\n" +
                    "WHERE tea_order_details.tea_order_id = \""+lastOrderId+"\" ";

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
