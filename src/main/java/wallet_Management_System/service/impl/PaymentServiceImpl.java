package wallet_Management_System.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wallet_Management_System.Entity.Transaction;
import wallet_Management_System.Entity.User;
import wallet_Management_System.Entity.Wallet;
import wallet_Management_System.Enum.TransactionStatus;
import wallet_Management_System.Enum.TransactionType;
import wallet_Management_System.dto.request.TransferRequest;
import wallet_Management_System.dto.response.TransferResponse;
import wallet_Management_System.repository.TransactionRepository;
import wallet_Management_System.repository.UserRepository;
import wallet_Management_System.repository.WalletRepository;
import wallet_Management_System.service.PaymentService;

import javax.naming.InsufficientResourcesException;
import javax.sound.midi.Receiver;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransferResponse sendMoney(TransferRequest request, String phoneNumber) {

        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("cannot be less than zero");
        }
        //check the sender balance
        TransferResponse response = new TransferResponse();
        if (userRepository.findByPhoneNumber(phoneNumber).isEmpty()) {
            throw new RuntimeException("Sender not exists");
        }

        User user = userRepository.findByPhoneNumber(phoneNumber).get();
        if (walletRepository.findByUser(user).isEmpty()) {
            throw new RuntimeException("wallet not exists for the sender");
        }
        Wallet senderWallet = walletRepository.findByUser(user).get();
        if (request.getAmount().compareTo(senderWallet.getBalance()) > 0) {
            throw new RuntimeException(
                    "Insufficient Balance");
        }

        //check for the receiver
        if (userRepository.findByPhoneNumber(request.getReceiverNumber()).isEmpty()) {
            throw new RuntimeException("Receiver  not exists");
        }
        User receiver = userRepository.findByPhoneNumber(request.getReceiverNumber()).get();
        if (walletRepository.findByUser(receiver).isEmpty()) {
            throw new RuntimeException("wallet not exists for the receiver");
        }
        Wallet receiverWallet = walletRepository.findByUser(receiver).get();


        //deduct amount from the sender
        BigDecimal balance = senderWallet.getBalance();
        BigDecimal senderNewBalance = balance.subtract(request.getAmount());
        senderWallet.setBalance(senderNewBalance);

        //add amount to receiver
        BigDecimal receiverBalance = receiverWallet.getBalance();
        BigDecimal receiverNewBalance = receiverBalance.add(request.getAmount());
        receiverWallet.setBalance(receiverNewBalance);

        //save the entities
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        //transactionHistory
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(request.getAmount());
        newTransaction.setTransactionType(TransactionType.TRANSFER);
        newTransaction.setStatus(TransactionStatus.SUCCESS);
        newTransaction.setSenderWallet(senderWallet);
        newTransaction.setReceiverWallet(receiverWallet);
        newTransaction.setCreatedAt(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(newTransaction);

        response.setTransactionId(savedTransaction.getTransactionId());
        response.setSenderBalance(senderWallet.getBalance());
        response.setTransferredAmount(request.getAmount());
        response.setMessage("Transfer Done");

        return response;

    }

    ;
}
