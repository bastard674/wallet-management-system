package wallet_Management_System.dto.response;

import lombok.Getter;
import lombok.Setter;
import wallet_Management_System.dto.TransactionSummary;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class DashboardResponse {

    private String name ;
    private String phoneNumber;
    private BigDecimal walletBalance;
    private List<TransactionSummary> recentTransactions;
}
