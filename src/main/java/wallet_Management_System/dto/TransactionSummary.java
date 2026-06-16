package wallet_Management_System.dto;

import lombok.Getter;
import lombok.Setter;
import wallet_Management_System.Enum.TransactionStatus;
import wallet_Management_System.Enum.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
public class TransactionSummary {

    private long transactionId;
    private BigDecimal amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private LocalDateTime transactionData;
}
