package lk.ijse.teacraft.dto;

import lk.ijse.teacraft.view.tdm.FertilizerSalesCartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceFertilizerOrderDto {

    private String fertilizerOrderId;

    private String customerId;

    LocalDate date;

    private List<FertilizerSalesCartTm> tmList = new ArrayList<>();

}
