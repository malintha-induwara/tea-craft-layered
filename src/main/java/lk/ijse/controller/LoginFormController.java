package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.bo.custom.impl.UserBOImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Objects;

public class LoginFormController {



    @FXML
    private MFXButton btnCreateAccount;

    @FXML
    private AnchorPane loginUiPane;

    @FXML
    private MFXButton btnLogin;

    @FXML
    private Text txtForgotPassword;

    @FXML
    private Text txtGreetings;

    @FXML
    private MFXTextField txtUsername;

    @FXML
    private MFXPasswordField txtPassword;


    @FXML
    private Text txtMassage;


    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);



    public void initialize()  {
        setGreetings();
    }

    private void setGreetings() {
        LocalTime currentTime = LocalTime.now();
        String greeting = (currentTime.getHour() < 12) ? "Good Morning" : "Good Evening";
        txtGreetings.setText(greeting);
    }


    @FXML
    void btnCreateAccountOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/createAccountForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        loginUiPane.getChildren().clear();
        loginUiPane.getChildren().add(registerPane);
    }

    @FXML
    void txtForgotPasswordOnAction(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/forgotPasswordForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        loginUiPane.getChildren().clear();
        loginUiPane.getChildren().add(registerPane);
    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        boolean isLoginValidated = validateLogin();

        if (!isLoginValidated){
            return;
        }





        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashBoardForm.fxml"));
        Scene scene = new Scene(rootNode);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
        stage.show();


        //Close the Current Window
        Stage loginStage = (Stage) btnLogin.getScene().getWindow();
        loginStage.close();

    }

    private boolean validateLogin() {


        String userName = txtUsername.getText();

        if(Objects.equals(userName, "")){
            txtUsername.requestFocus();
            txtUsername.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtUsername.getStyleClass().removeAll("mfx-text-field-error");


        String password = txtPassword.getText();

        if(Objects.equals(password, "")){
            txtPassword.requestFocus();
            txtPassword.getStyleClass().add("mfx-text-field-error");
            return false;
        }

        txtPassword.getStyleClass().removeAll("mfx-text-field-error");


        //Validate Username
        boolean isUsernameExist = false;
        try {
            isUsernameExist = userBO.searchUser(userName);
        }
        catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        if (!isUsernameExist){
            txtMassage.setVisible(true);
            txtUsername.requestFocus();
            txtMassage.setText("User doesnt exist.");
            txtUsername.getStyleClass().add("mfx-text-field-error");
            return false;
        }


        txtUsername.getStyleClass().removeAll("mfx-text-field-error");
        txtMassage.setVisible(false);


        //Validate Username and password
        boolean isUserExist = false;
        try {
            isUserExist = userBO.searchUsernameAndPassword(userName,password);
        }
        catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

        if (!isUserExist){
            txtMassage.setVisible(true);
            txtPassword.requestFocus();
            txtMassage.setText("Invalid Password");
            txtPassword.getStyleClass().add("mfx-text-field-error");
            return false;
        }


        txtPassword.getStyleClass().removeAll("mfx-text-field-error");
        txtMassage.setVisible(false);


        //Set Current User
        UserBOImpl.userName = userName;


        return true;

    }


    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }



}
