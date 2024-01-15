package lk.ijse.teacraft.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String cusId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String mobileNo;
}

