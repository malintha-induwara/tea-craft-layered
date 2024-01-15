package lk.ijse.teacraft.view.tdm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FertilizerTm {

    private String fertilizerId;
    private String brand;
    private String description;
    private String size;
    private int qty;
    private double price;
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
        updateButton.setPrefWidth(100);

        deleteButton.setPrefHeight(30);
        deleteButton.setPrefWidth(100);

    }


    public FertilizerTm(String fertilizerId, String brand, String description, String size, int qty, double price) {
        this.fertilizerId = fertilizerId;
        this.brand = brand;
        this.description = description;
        this.size = size;
        this.qty = qty;
        this.price = price;
    }



}

