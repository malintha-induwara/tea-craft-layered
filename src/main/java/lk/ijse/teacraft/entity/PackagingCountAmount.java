package lk.ijse.teacraft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackagingCountAmount {

    private String packId;

    private int count;

    private double amount;
}

