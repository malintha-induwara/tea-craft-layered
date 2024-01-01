package lk.ijse.view.tdm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeTm {

    private String empId;
    private String firstName;
    private String lastName;
    private String address;
    private String sex;
    private String dateOfBirth;
    private String mobileNo;
    private MFXButton updateButton;
    private MFXButton deleteButton;

    {


        ImageView update = new ImageView(new Image("/assets/images/edit.png"));
        ImageView delete = new ImageView(new Image("/assets/images/remove.png"));


        update.setFitHeight(30);
        update.setPreserveRatio(true);


        delete.setFitHeight(30);
        delete.setPreserveRatio(true);


        updateButton = new MFXButton("", update);
        deleteButton = new MFXButton("",delete);

        updateButton.setCursor(javafx.scene.Cursor.HAND);
        deleteButton.setCursor(javafx.scene.Cursor.HAND);

        updateButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white");
        deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white");

        updateButton.setPrefHeight(30);
        updateButton.setPrefWidth(80);

        deleteButton.setPrefHeight(30);
        deleteButton.setPrefWidth(80);

    }


    public EmployeeTm(String empId, String firstName, String lastName, String address, String sex, String dateOfBirth, String mobileNo) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.mobileNo = mobileNo;
    }

}
