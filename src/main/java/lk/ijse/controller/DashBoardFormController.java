package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DashBoardFormController {



    @FXML
    private MFXButton btnCustomer;

    @FXML
    private MFXButton btnDashboard;

    @FXML
    private MFXButton btnEmployee;

    @FXML
    private MFXButton btnLogout;

    @FXML
    private MFXButton btnFertilizer;

    @FXML
    private MFXButton btnPackaging;

    @FXML
    private MFXButton btnProcessing;

    @FXML
    private MFXButton btnSales;

    @FXML
    private MFXButton btnSupplier;

    @FXML
    private MFXButton btnTeaLeaves;

    @FXML
    private AnchorPane mainPane;


    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashBoardMainForm.fxml"));

        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

        //To Activate dashboard Button
        setButtonActive(btnDashboard);
    }


    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnDashboard);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dashBoardMainForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

    }


    @FXML
    void btnTeaLeavesOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnTeaLeaves);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/teaLeavesForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }


    @FXML
    void btnProcessingOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnProcessing);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/processingForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }


    @FXML
    void btnPackagingOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnPackaging);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/packagingForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

    }

    @FXML
    void btnSalesOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnSales);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/salesForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

    }





    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnSupplier);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/supplierForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnEmployee);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }


    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnCustomer);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/customerForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);
    }


    @FXML
    void btnFertilizerOnAction(ActionEvent event) throws IOException {
        setButtonActive(btnFertilizer);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/fertilizerSalesForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        mainPane.getChildren().clear();
        mainPane.getChildren().add(registerPane);

    }



    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/mainForm.fxml"));
        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Login");
        stage.show();

        //Close the Current Window
        Stage dashboard= (Stage) btnLogout.getScene().getWindow();
        dashboard.close();

    }

    private void setButtonActive(MFXButton activeButton) {

        //Remove Style
        btnDashboard.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnTeaLeaves.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnProcessing.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnPackaging.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnSales.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnSupplier.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnEmployee.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnCustomer.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnLogout.getStyleClass().removeAll("mfx-active-button","mfx-button");
        btnFertilizer.getStyleClass().removeAll("mfx-active-button","mfx-button");

        //Add Style
        activeButton.getStyleClass().add("mfx-active-button");
    }


}
