package wallet_Management_System.service.impl;


import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wallet_Management_System.Entity.Transaction;
import wallet_Management_System.Entity.User;
import wallet_Management_System.Entity.Wallet;
import wallet_Management_System.Enum.TransactionStatus;
import wallet_Management_System.Enum.TransactionType;
import wallet_Management_System.dto.response.DepositResponse;
import wallet_Management_System.repository.TransactionRepository;
import wallet_Management_System.repository.UserRepository;
import wallet_Management_System.repository.WalletRepository;
import wallet_Management_System.service.DepositService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DepositServiceImpl implements DepositService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public DepositResponse deposit(String phoneNumber, BigDecimal amount) {
        if(userRepository.findByPhoneNumber(phoneNumber).isEmpty()){
            throw new RuntimeException("user not exists");

        }
        DepositResponse response = new DepositResponse();
        if(amount.compareTo(BigDecimal.ZERO)<0){
            response.setMessage("amount cannot be less than 0");
            return response;
        }

        User user = userRepository.findByPhoneNumber(phoneNumber).get();
        Wallet wallet = walletRepository.findByUser(user).get();

        //updating the balance
        BigDecimal balance = wallet.getBalance();
        BigDecimal updatedBalance = balance.add(amount);

        //saving the balance
        wallet.setBalance(updatedBalance);
        walletRepository.save(wallet);

        //add the transaction records
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setSenderWallet(null);
        transaction.setReceiverWallet(wallet);
        transactionRepository.save(transaction);

        response.setBalance(wallet.getBalance());
        response.setMessage("Deposit success");
        return response;
    }
}
