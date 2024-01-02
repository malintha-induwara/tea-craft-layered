package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.entity.TeaLeavesStock;
import lk.ijse.view.tdm.TeaLeavesStockTm;
import lk.ijse.model.TeaBookModel;
import lk.ijse.model.TeaLeavesStockModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TeaLeavesFormController {



    @FXML
    private MFXButton btnAddStock;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDelete;

    @FXML
    private TableColumn<?, ?> colStockId;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;



    @FXML
    private TableColumn<?, ?> colUpdate;


    @FXML
    private TableView<TeaLeavesStockTm> tableTeaLeaveStock;


    @FXML
    private MFXDatePicker dpDate;

    @FXML
    private Text txtAmount;

    private TeaBookModel teaBookModel = new TeaBookModel();

    private final SupplierBO supplierModel = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.SUPPLIER);

    private TeaLeavesStockModel teaLeavesStockModel = new TeaLeavesStockModel();


    public void initialize(){
        setCurrentDate();
        setCurrentDailyAmount(dpDate.getValue().toString());
        setCellValueFactory();
        loadAllStockDetails(dpDate.getValue().toString());

    }

    private void setCellValueFactory() {

        colStockId.setCellValueFactory(new PropertyValueFactory<>("teaStockId"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colUpdate.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));


    }

    public void loadAllStockDetails(String date) {



        try{
            ObservableList < TeaLeavesStockTm> obList = FXCollections.observableArrayList();

            String dateBookId = teaBookModel.getTeaBookId(date);



            List<TeaLeavesStock> dtoList = teaLeavesStockModel.getAllStockDetails(dateBookId);

            for (TeaLeavesStock dto : dtoList) {

                String supName = supplierModel.getSupplierName(dto.getSupId());

                obList.add( new TeaLeavesStockTm(
                        dto.getTeaStockId(),
                        dto.getSupId(),
                        supName,
                        dto.getAmount()
                ));

            }
            for (int i = 0; i < obList.size(); i++) {
                final  int index = i;


                //To Disable Delete and update button if already paid

                if (dtoList.get(index).isPayed()){
                    obList.get(i).getDeleteButton().setDisable(true);
                    obList.get(i).getUpdateButton().setDisable(true);
                }


                obList.get(i).getUpdateButton().setOnAction(event -> {
                    try {
                        updateTeaLeavesStock(dtoList.get(index).getTeaStockId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


                obList.get(i).getDeleteButton().setOnAction(event -> {

                    String teaStockId = dtoList.get(index).getTeaStockId();
                    deleteTeaLeavesStock(teaStockId);


                        try {
                            String teaBookId = teaBookModel.getTeaBookId(String.valueOf(dpDate.getValue()));
                            double dailyAmount = teaLeavesStockModel.getTotalAmount(teaBookId);
                            teaBookModel.updateTeaBookAmount(teaBookId,dailyAmount);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    setCurrentDailyAmount(dpDate.getValue().toString());
                    loadAllStockDetails(dpDate.getValue().toString());

                });


            }


            tableTeaLeaveStock.setItems(obList);



        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }




    }

    private void deleteTeaLeavesStock(String teaStockId) {

           try {

               boolean isDeleted = teaLeavesStockModel.deleteTeaLeavesStock(teaStockId);
               if (isDeleted){
                   new Alert(Alert.AlertType.CONFIRMATION, "Stock Deleted").show();
               }

           }catch (SQLException e){
               new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
           }


    }




    @FXML
    void  btnAddStockOnAction(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/addTeaLeavesStockForm.fxml"));
        Parent rootNode = loader.load();

        AddTeaLeavesStockFormController addTeaLeavesStockFormController = loader.getController();
        addTeaLeavesStockFormController.setTeaLeavesFormController(this);
        addTeaLeavesStockFormController.setDate(dpDate.getValue().toString());


        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Add Tea Stock");
        stage.show();

    }

    private void setCurrentDate() {
        dpDate.setValue(java.time.LocalDate.now());
    }







    public void setCurrentDailyAmount(String date) {


        try{
            boolean isExists=teaBookModel.searchDate(date);

            if (isExists){
                txtAmount.setText(String.valueOf(teaBookModel.getAmount(date))+" Kg");
            }
            else{
                txtAmount.setText("No Date");
            }

        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();

        }



    }


    @FXML
    void dpDateOnAction(ActionEvent event) {
        String date = dpDate.getValue().toString();

        //This method is to check is there a record for that date in database
        //If not it create a one
        dateCheck(date);
        setCurrentDailyAmount(date);
        loadAllStockDetails(date);

    }

    private void dateCheck(String date) {

        try{
            if (!teaBookModel.searchDate(date)){
                teaBookModel.createTeaBookRecord(date);
                setCurrentDailyAmount(date);
            }
        }
        catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }


    public void updateTeaLeavesStock(String teaStockId) throws IOException {


        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/updateTeaStockForm.fxml"));
        Parent rootNode = loader.load();

        // Get a reference to the AddCustomerFormController
        UpdateTeaStockFormController updateTeaStockFormController = loader.getController();

        // Pass a reference to this CustomerFormController
        updateTeaStockFormController.setTeaLeavesFormController(this);
        updateTeaStockFormController.setTeaLeavesStockId(teaStockId);
        updateTeaStockFormController.loadTeaLeavesStockDetails();


        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Update Supplier");
        stage.show();


    }


}
