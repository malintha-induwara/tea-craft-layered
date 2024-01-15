package lk.ijse.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Supplier {

    private String supId;
    private String firstName;
    private  String lastName;
    private  String address;
    private String bank;
    private String bankNo;
    private String mobileNo;
}
