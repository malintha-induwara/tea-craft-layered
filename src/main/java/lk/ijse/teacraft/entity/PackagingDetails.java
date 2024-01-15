package lk.ijse.teacraft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackagingDetails {

    private String packagingDetailsId;

    private LocalDate date;

    private String packId;

    private int count;

    private double amount;

    private boolean isConfirmed;


}
