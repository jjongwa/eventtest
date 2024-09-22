package test.eventtest.wallet;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletListenerService {

    private final WalletRepository walletRepository;
    private final EntityManagerService entityManagerService;

    public void chargeCash(final String name, final Integer cash) {
        final Thread thread = Thread.currentThread();
        log.info("ListenerService 메서드가 진행중인 스레드: id={}, name={}", thread.threadId(), thread.getName());
        entityManagerService.logConnectionInfo();
        final Optional<Wallet> optional = walletRepository.findByName(name);
        optional.ifPresent(wallet -> walletRepository.save(wallet.plusCash(cash)));
    }

//    public List<Wallet> minusAllWallets(final List<Wallet> wallets, final Integer cash) {
//        return walletRepository.saveAll(wallets.stream()
//                .map(wallet -> wallet.minusCash(cash))
//                .toList());
//    }
}
