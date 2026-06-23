package wallet_Management_System.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    private String receiverNumber;
    private BigDecimal amount;
}
