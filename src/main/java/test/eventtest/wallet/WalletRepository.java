package test.eventtest.wallet;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByName(final String name);
}
