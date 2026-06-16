package wallet_Management_System.service;

import wallet_Management_System.dto.request.WithdrawRequest;
import wallet_Management_System.dto.response.WithdrawResponse;

import java.math.BigDecimal;

public interface WithdrawService {
    WithdrawResponse withdraw(String phoneNumber, BigDecimal amount);
}
