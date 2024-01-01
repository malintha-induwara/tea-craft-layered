package lk.ijse.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeaBook {

    private  String teaBookId;
    private  double dailyAmount;
    private String date;

}
