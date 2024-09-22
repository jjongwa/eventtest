package test.eventtest.wallet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EntityManagerService {

    @PersistenceContext
    private EntityManager entityManager;
    private final WalletRepository walletRepository;

    public void findWithEntityLock(final String name) {
        walletRepository.findByName(name)
                .ifPresent(wallet -> {
                    final Wallet lockWallet = entityManager.find(Wallet.class, wallet.getId());
                    entityManager.refresh(lockWallet, LockModeType.PESSIMISTIC_WRITE);
                });
    }

    public void logConnectionInfo() {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                DatabaseMetaData metaData = connection.getMetaData();
                log.info("연결된 DB connection: {}", metaData.getConnection().toString());
            }
        });
    }
}
