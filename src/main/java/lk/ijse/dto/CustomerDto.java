package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {

    private String cusId;
    private String firstName;
    private  String lastName;
    private  String address;
    private String email;
    private  String mobileNo;

}
