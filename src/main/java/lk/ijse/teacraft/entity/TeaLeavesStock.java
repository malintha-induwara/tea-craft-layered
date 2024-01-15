package lk.ijse.teacraft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaLeavesStock {
    private String teaStockId;
    private String supId;
    private String teaBookId;
    private double amount;
    private boolean isPayed;
}

