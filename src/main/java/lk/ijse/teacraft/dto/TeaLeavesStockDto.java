package lk.ijse.teacraft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaLeavesStockDto {

    private String teaStockId;

    private String supId;

    private String teaBookId;

    private double amount;

    private boolean isPayed;



}

