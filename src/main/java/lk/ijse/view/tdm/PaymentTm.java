package lk.ijse.view.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentTm {

    private String paymentId;
    private double amount;
    private double payment;
    private LocalDate date;


}
