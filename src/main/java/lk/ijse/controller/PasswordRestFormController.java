package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.bo.BOFactory;
import lk.ijse.bo.custom.UserBO;

import java.io.IOException;
import java.sql.SQLException;

public class PasswordRestFormController {


    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnReset;

    @FXML
    private AnchorPane passwordRestPane;

    @FXML
    private MFXPasswordField txtFieldReEnterPassword;

    @FXML
    private MFXPasswordField txtFieldPassword;

    @FXML
    private Text txtMassage;


    private final UserBO userBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);

    private String userName;

    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        passwordRestPane.getChildren().clear();
        passwordRestPane.getChildren().add(registerPane);
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws IOException {

        boolean isPasswordsValidated = validatePasswords();

        if (!isPasswordsValidated){
            return;
        }

        updatePassword();

    }

    private void updatePassword() throws IOException {

        String userName = this.userName;
        String password = txtFieldPassword.getText();

        try {
            boolean isUpdated = userBO.updatePassword(userName, password);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Password Updated Successfully").show();
                switchToLogin();
            }
        }
        catch (SQLException | ClassNotFoundException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void switchToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        passwordRestPane.getChildren().clear();
        passwordRestPane.getChildren().add(registerPane);
    }

    private boolean validatePasswords() {

        String password = txtFieldPassword.getText();
        boolean isPasswordValidated = password.matches("[A-Za-z]{3,}");

        if (!isPasswordValidated){
            txtFieldPassword.requestFocus();
            changeTextFieldStyle(txtFieldPassword);
            return false;
        }

        String reEnterPassword = txtFieldReEnterPassword.getText();
        boolean isReEnterPasswordValidated = reEnterPassword.matches("[A-Za-z]{3,}");

        if (!isReEnterPasswordValidated){
            txtFieldReEnterPassword.requestFocus();
            changeTextFieldStyle(txtFieldReEnterPassword);
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

    private void changeTextFieldStyle() {
        txtMassage.setVisible(false);
        txtFieldPassword.getStyleClass().removeAll("mfx-text-field-error");
        txtFieldReEnterPassword.getStyleClass().removeAll("mfx-text-field-error");
    }

    private void changeTextFieldStyle(MFXPasswordField txtField) {

        txtMassage.setVisible(false);

        txtFieldPassword.getStyleClass().removeAll("mfx-text-field-error");
        txtFieldReEnterPassword.getStyleClass().removeAll("mfx-text-field-error");

        txtField.getStyleClass().add("mfx-text-field-error");

        if (txtField==txtFieldPassword){
            txtMassage.setText("Invalid Password");
            txtMassage.setVisible(true);
        }
        else if (txtField==txtFieldReEnterPassword){
            txtMassage.setText("Invalid ReenterPassword");
            txtMassage.setVisible(true);
        }


    }


    public void setUserName(String userName) {
        this.userName= userName;
    }

    @FXML
    void txtFieldPasswordOnAction(ActionEvent event) throws IOException {
        btnResetOnAction(event);

    }

    @FXML
    void txtFieldReEnterPasswordOnAction(ActionEvent event) throws IOException {
        btnResetOnAction(event);
    }


}
