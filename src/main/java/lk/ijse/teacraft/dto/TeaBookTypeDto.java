package lk.ijse.teacraft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data


public class TeaBookTypeDto {

    private String teaBookTypeId;
    private LocalDate date;
    private String typeId;
    private double amount;
    private boolean isConfirmed;

}
