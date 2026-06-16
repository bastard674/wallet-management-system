package wallet_Management_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_Management_System.Entity.Transaction;
import wallet_Management_System.Entity.Wallet;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {


    List<Transaction> findBySenderWallet(Wallet senderWallet);
    List<Transaction> findByReceiverWallet(Wallet receiverwallet);
}
