package lk.ijse.teacraft.view.tdm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeaLeavesStockTm {


    private String teaStockId;

    private String supId;

    private String SupName;

    private double amount;

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


    public TeaLeavesStockTm(String teaStockId, String supId, String SupName, double amount) {
        this.teaStockId = teaStockId;
        this.supId = supId;
        this.SupName = SupName;
        this.amount = amount;
    }


}
