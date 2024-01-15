package lk.ijse.teacraft.dto;

import lk.ijse.teacraft.view.tdm.SalesCartTm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceTeaOrderDto {

    private String orderId;

    private String cusId;

    private LocalDate date;

    private List<SalesCartTm> tmList = new ArrayList<>();


}
