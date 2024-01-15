package lk.ijse.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Packaging {

    private String packId;

    private String typedId;

    private String description;

    private int packageCount;

    private double price;


}
