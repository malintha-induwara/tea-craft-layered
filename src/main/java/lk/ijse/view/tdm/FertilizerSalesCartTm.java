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
public class FertilizerSalesCartTm {

    private String fertilizerId;

    private String description;

    private int qty;

    private double total;

    private MFXButton removeButton;


    {

        ImageView delete = new ImageView(new Image("/assets/images/remove.png"));
        delete.setFitHeight(30);
        delete.setPreserveRatio(true);

        removeButton = new MFXButton("",delete);

        removeButton.setCursor(javafx.scene.Cursor.HAND);
        removeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white");

        removeButton.setPrefHeight(30);
        removeButton.setPrefWidth(100);
    }


    public FertilizerSalesCartTm(String fertilizerId, String description, int qty, double total) {
        this.fertilizerId = fertilizerId;
        this.description = description;
        this.qty = qty;
        this.total = total;
    }
}
