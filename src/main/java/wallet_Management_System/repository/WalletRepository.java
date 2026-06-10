package wallet_Management_System.repository;


import lombok.Lombok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wallet_Management_System.Entity.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
