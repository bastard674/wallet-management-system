package wallet_Management_System.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class TransferResponse {

    private Long transactionId;
    private BigDecimal transferredAmount;
    private BigDecimal senderBalance;
    private String message;
}
