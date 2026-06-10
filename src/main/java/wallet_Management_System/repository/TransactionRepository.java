package wallet_Management_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_Management_System.Entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
