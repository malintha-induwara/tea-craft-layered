package lk.ijse.entity;

import lk.ijse.view.tdm.FertilizerSalesCartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceFertilizerOrder {
    private String fertilizerOrderId;
    private String customerId;
    LocalDate date;
}

