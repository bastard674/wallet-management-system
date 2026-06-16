package wallet_Management_System.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wallet_Management_System.Entity.Transaction;
import wallet_Management_System.Entity.User;
import wallet_Management_System.Entity.Wallet;
import wallet_Management_System.dto.TransactionSummary;
import wallet_Management_System.dto.response.DashboardResponse;
import wallet_Management_System.repository.TransactionRepository;
import wallet_Management_System.repository.UserRepository;
import wallet_Management_System.repository.WalletRepository;
import wallet_Management_System.service.DashboardService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;


    @Override
    public DashboardResponse dashboard(String phoneNumber) {
        DashboardResponse response = new DashboardResponse();
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
                new RuntimeException("phoneNumber not exits"));

        Wallet wallet = walletRepository.findByUser(user).orElseThrow(
                () -> new RuntimeException("userid not found"));

        List<TransactionSummary> txnSummary = new ArrayList<>();
        List<Transaction> sendTransactions = transactionRepository.findBySenderWallet(wallet);
        List<Transaction> receiveTransactions = transactionRepository.findByReceiverWallet(wallet);

        sendTransactions.addAll(receiveTransactions);
        sendTransactions.sort(Comparator.comparing(Transaction::getCreatedAt).reversed());

        for (Transaction txn : sendTransactions) {
            TransactionSummary summary = new TransactionSummary();
            summary.setTransactionId(txn.getTransactionId());
            summary.setAmount(txn.getAmount());
            summary.setTransactionType(txn.getTransactionType());
            summary.setTransactionStatus(txn.getStatus());
            summary.setTransactionData(txn.getCreatedAt());
            txnSummary.add(summary);
        }


        response.setPhoneNumber(user.getPhoneNumber());
        response.setName(user.getName());
        response.setWalletBalance(wallet.getBalance());
        response.setRecentTransactions(txnSummary);

        return response;
    }

}
