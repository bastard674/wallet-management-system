package wallet_Management_System.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class WithdrawResponse {
    private String message;
    private BigDecimal balance;


}
