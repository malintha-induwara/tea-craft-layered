package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackagingCountAmountDto {

    private String packId;

    private int count;

    private double amount;

}
