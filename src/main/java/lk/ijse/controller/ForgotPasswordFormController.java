package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;

import java.io.IOException;
import java.sql.SQLException;

public class ForgotPasswordFormController {

    @FXML
    private AnchorPane forgotPasswordPane;
    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnReset;

    @FXML
    private Text txtMassage;

    @FXML
    private MFXTextField txtEmail;

    @FXML
    private MFXTextField txtUserName;

    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);


    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        forgotPasswordPane.getChildren().clear();
        forgotPasswordPane.getChildren().add(registerPane);
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws IOException {

        boolean isDetailsVerified = validateDetails();

        if (!isDetailsVerified){
            return;
        }



        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/passwordResetOtpForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        PasswordResetOtpFormController controller = fxmlLoader.getController();
        controller.setUserNameAndEmail(txtUserName.getText(),txtEmail.getText());
        forgotPasswordPane.getChildren().clear();
        forgotPasswordPane.getChildren().add(registerPane);
        controller.sendOtp();

    }

    private boolean validateDetails() {

        String userName = txtUserName.getText();
        boolean isUserNameValidated = userName.matches("[A-Za-z]{3,}");

        if (!isUserNameValidated){
            txtUserName.requestFocus();
            changeTextFieldStyle(txtUserName);
            return false;
        }

        //Check Username Exist
        boolean isUsernameExist = false;

        try {
            isUsernameExist = userBO.searchUser(userName);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!isUsernameExist){
            txtMassage.setText("Username Doesnt Exist");
            txtMassage.setVisible(true);
            return false;
        }

        String email = txtEmail.getText();

        boolean isEmailValidated = email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        if (!isEmailValidated){
            txtEmail.requestFocus();
            changeTextFieldStyle(txtEmail);
            return false;
        }

        //Check Email
        boolean isEmailExist = false;

        try {
          isEmailExist = userBO.searchEmailAndUsername(userName,email);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!isEmailExist){
            txtMassage.setText("Email Doesnt Match");
            txtMassage.setVisible(true);
            return false;
        }



        changeTextFieldStyle();

        return  true;
    }

    private void changeTextFieldStyle() {
        txtMassage.setVisible(false);

        txtUserName.getStyleClass().removeAll("mfx-text-field-error");
        txtEmail.getStyleClass().removeAll("mfx-text-field-error");
    }

    private void changeTextFieldStyle(MFXTextField txtField) {

        txtMassage.setVisible(false);

        txtUserName.getStyleClass().removeAll("mfx-text-field-error");
        txtEmail.getStyleClass().removeAll("mfx-text-field-error");

        txtField.getStyleClass().add("mfx-text-field-error");




        if (txtField==txtEmail){
            txtMassage.setText("Invalid Email");
            txtMassage.setVisible(true);
        }
        else if (txtField==txtUserName){
            txtMassage.setText("Invalid Username");
            txtMassage.setVisible(true);
        }


    }

    @FXML
    void txtEmailOnAction(ActionEvent event) throws IOException {
        btnResetOnAction(event);

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) throws IOException {
        btnResetOnAction(event);
    }



}
