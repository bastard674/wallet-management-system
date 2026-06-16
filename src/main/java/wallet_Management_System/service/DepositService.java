package wallet_Management_System.service;

import wallet_Management_System.dto.response.DepositResponse;

import java.math.BigDecimal;

public interface DepositService {

    DepositResponse deposit(String phoneNumber, BigDecimal amount);
}
