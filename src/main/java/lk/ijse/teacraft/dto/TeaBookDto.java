package lk.ijse.teacraft.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaBookDto {

    private  String teaBookId;
    private  double dailyAmount;
    private String date;

}
