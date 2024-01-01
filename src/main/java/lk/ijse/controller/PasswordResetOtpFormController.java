package lk.ijse.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import lk.ijse.util.EmailService;

import java.io.IOException;

public class PasswordResetOtpFormController {

    @FXML
    private MFXButton btnCancel;

    @FXML
    private MFXButton btnResend;

    @FXML
    private MFXButton btnVerify;

    @FXML
    private AnchorPane otpPane;

    @FXML
    private MFXTextField txtFieldOtp;

    @FXML
    private Text txtMassage;


    private String otp;

    private String userName;

    private String email;


    @FXML
    void btnCancelOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/loginForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        otpPane.getChildren().clear();
        otpPane.getChildren().add(registerPane);
    }

    @FXML
    void btnVerifyOnAction(ActionEvent event) throws IOException {

        String otp = txtFieldOtp.getText();

        if(!otp.equals(this.otp)){
            txtMassage.setVisible(true);
            txtFieldOtp.getStyleClass().add("mfx-text-field-error");
            return;
        }




        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/passwordRestForm.fxml"));
        Pane registerPane = (Pane) fxmlLoader.load();
        PasswordRestFormController controller = fxmlLoader.getController();
        controller.setUserName(userName);
        otpPane.getChildren().clear();
        otpPane.getChildren().add(registerPane);

    }

    public void setUserNameAndEmail(String userName,String email){
        this.userName=userName;
        this.email=email;
    }

    public void sendOtp(){
        this.otp= EmailService.sendMail(email);
    }


    @FXML
    void btnResendOnAction(ActionEvent event) {

        txtFieldOtp.getStyleClass().removeAll("mfx-text-field-error");
        txtMassage.setVisible(false);
        sendOtp();

    }


    @FXML
    void txtFieldOtpOnAction(ActionEvent event) throws IOException {
        btnVerifyOnAction(event);

    }

}
