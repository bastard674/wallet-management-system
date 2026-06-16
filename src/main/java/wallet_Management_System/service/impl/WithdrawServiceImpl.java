package wallet_Management_System.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wallet_Management_System.Entity.Transaction;
import wallet_Management_System.Entity.User;
import wallet_Management_System.Entity.Wallet;
import wallet_Management_System.Enum.TransactionStatus;
import wallet_Management_System.Enum.TransactionType;
import wallet_Management_System.dto.request.WithdrawRequest;
import wallet_Management_System.dto.response.DepositResponse;
import wallet_Management_System.dto.response.WithdrawResponse;
import wallet_Management_System.repository.TransactionRepository;
import wallet_Management_System.repository.UserRepository;
import wallet_Management_System.repository.WalletRepository;
import wallet_Management_System.service.WithdrawService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WithdrawServiceImpl implements WithdrawService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    public WithdrawResponse withdraw(String phoneNumber, BigDecimal amount) {
        if(userRepository.findByPhoneNumber(phoneNumber).isEmpty()){
            throw new RuntimeException("user not exists");

        }
        WithdrawResponse response = new WithdrawResponse();
        if(amount.compareTo(BigDecimal.ZERO)<0){
            response.setMessage("amount cannot be less than 0");
            return response;
        }

        User user = userRepository.findByPhoneNumber(phoneNumber).get();
        Wallet wallet = walletRepository.findByUser(user).get();

        if(amount.compareTo(wallet.getBalance())>0){
          response.setMessage("insufficeint funds");
          return response;
        }

        //updating the balance
        BigDecimal balance = wallet.getBalance();
        BigDecimal updatedBalance = balance.subtract(amount);
        System.out.println("updatedBalance :" + updatedBalance);
        //saving the balance
        wallet.setBalance(updatedBalance);
        walletRepository.save(wallet);

        //add the transaction records
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setAmount(amount);
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setSenderWallet(wallet);
        transaction.setReceiverWallet(null);
        transactionRepository.save(transaction);

        //responseDTO
        response.setBalance(wallet.getBalance());
        response.setMessage("withdraw success");
        return response;

    }
}
