package lk.ijse.teacraft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalaryDto {

    private String salaryId;

    private String empId;

    private double amount;

    private int dateCount;

    private LocalDate date;


}
