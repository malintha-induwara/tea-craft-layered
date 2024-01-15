package lk.ijse.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Fertilizer {

    private String fertilizerId;

    private String brand;

    private String description;

    private String size;

    private double price;

    private int qty;


}

