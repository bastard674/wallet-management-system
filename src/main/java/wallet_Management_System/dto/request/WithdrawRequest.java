package wallet_Management_System.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class WithdrawRequest {

    private BigDecimal amount;
}
