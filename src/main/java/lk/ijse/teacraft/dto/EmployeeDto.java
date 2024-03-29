package lk.ijse.teacraft.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class EmployeeDto {

    private String empId;
    private String firstName;
    private String lastName;
    private String address;
    private String sex;
    private String dateOfBirth;
    private String mobileNo;


}
