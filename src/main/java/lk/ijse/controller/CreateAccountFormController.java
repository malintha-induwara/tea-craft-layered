package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;
import lk.ijse.dto.UserDto;
import lk.ijse.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public class CreateAccountFormController {


    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnCreateAccount;

    @FXML
    private AnchorPane createAccountPane;

    @FXML
    private MFXTextField txtEmail;

    @FXML
    private MFXPasswordField txtPassword;

    @FXML
    private MFXPasswordField txtReEnterPassword;

    @FXML
    private MFXTextField txtUsername;


    @FXML
    private Text txtMassage;


    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        createAccountPane.getChildren().clear();
        createAccountPane.getChildren().add(registerPane);

    }

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) throws IOException {


        boolean isAccountValidated = validateAccount();

        if (!isAccountValidated){
            return;
        }



        String email = txtEmail.getText();
        String userName = txtUsername.getText();
        String password = txtPassword.getText();
        String reEnterPassword = txtReEnterPassword.getText();


        UserDto dto = new UserDto(userName,password,email);


        //TO switch the UI

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/view/createAccountOtpForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        CreateAccountOtpFormController controller = fxmlLoader.getController();
        controller.setUserDto(dto);
        createAccountPane.getChildren().clear();
        createAccountPane.getChildren().add(registerPane);
        controller.sendOtp();



    }

    private boolean validateAccount() {

        String email = txtEmail.getText();
        boolean isEmailValidated = email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

        if (!isEmailValidated){
            txtEmail.requestFocus();
            changeTextFieldStyle(txtEmail);
            return false;
        }

        boolean isEmailExist = false;

        try {
            isEmailExist = userBO.searchEmail(email);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (isEmailExist){
            txtMassage.setText("Email Already Exist");
            txtEmail.getStyleClass().add("mfx-text-field-error");
            txtMassage.setVisible(true);
            return false;
        }

        txtEmail.getStyleClass().removeAll("mfx-text-field-error");
        txtMassage.setVisible(false);




        String userName = txtUsername.getText();
        boolean isUserNameValidated = userName.matches("[A-Za-z]{3,}");

        if (!isUserNameValidated){
            txtUsername.requestFocus();
            changeTextFieldStyle(txtUsername);
            return false;
        }

        //Check Username Exist
        boolean isUsernameExist = false;

        try {
            isUsernameExist = userBO.searchUser(userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (isUsernameExist){
            txtMassage.requestFocus();
            txtMassage.setText("Username Already Exist");
            txtMassage.setVisible(true);
            return false;
        }



        String password = txtPassword.getText();
        boolean isPasswordValidated = password.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}");

        if (!isPasswordValidated){
            txtPassword.requestFocus();
            changeTextFieldStyle(txtPassword);
            return false;
        }

        String reEnterPassword = txtReEnterPassword.getText();
        boolean isReEnterPasswordValidated = reEnterPassword.matches("(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}");

        if (!isReEnterPasswordValidated){
            txtReEnterPassword.requestFocus();
            changeTextFieldStyle(txtReEnterPassword);
            return false;
        }




        //Check Passwords

        if (!password.equals(reEnterPassword)){
            txtMassage.setText("Password doesnt Match");
            return false;
        }


        changeTextFieldStyle();


        return true;
    }

    private void changeTextFieldStyle(MFXTextField txtField) {

        //Disable the visibility of the error message
        txtMassage.setVisible(false);

        txtEmail.getStyleClass().removeAll("mfx-text-field-error");
        txtUsername.getStyleClass().removeAll("mfx-text-field-error");
        txtPassword.getStyleClass().removeAll("mfx-text-field-error");
        txtReEnterPassword.getStyleClass().removeAll("mfx-text-field-error");

        txtField.getStyleClass().add("mfx-text-field-error");

        //Enable the visibility of the error message
        if (txtField==txtEmail){
            txtMassage.setText("Invalid Email");
            txtMassage.setVisible(true);
        }
        else if (txtField==txtUsername){
            txtMassage.setText("Invalid Username");
            txtMassage.setVisible(true);
        }
        else if (txtField==txtPassword){
            txtMassage.setText("Enter a valid Password with at least 4 characters and a number");
            txtMassage.setVisible(true);
        }
        else if (txtField==txtReEnterPassword){
            txtMassage.setText("Invalid ReEnterPassword");
            txtMassage.setVisible(true);
        }



    }

    private void changeTextFieldStyle() {

        txtMassage.setVisible(false);

        txtEmail.getStyleClass().removeAll("mfx-text-field-error");
        txtUsername.getStyleClass().removeAll("mfx-text-field-error");
        txtPassword.getStyleClass().removeAll("mfx-text-field-error");
        txtReEnterPassword.getStyleClass().removeAll("mfx-text-field-error");


    }


    private void clearFields() {
        txtEmail.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtReEnterPassword.clear();
    }

    @FXML
    void txtEmailOnAction(ActionEvent event) throws IOException {
        btnCreateAccountOnAction(event);

    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) throws IOException {
        btnCreateAccountOnAction(event);

    }

    @FXML
    void txtReEnterPasswordOnAction(ActionEvent event) throws IOException {
        btnCreateAccountOnAction(event);
    }

    @FXML
    void txtUsernameOnAction(ActionEvent event) throws IOException {
        btnCreateAccountOnAction(event);
    }




}
