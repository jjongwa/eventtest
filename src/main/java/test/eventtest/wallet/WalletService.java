package test.eventtest.wallet;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import test.eventtest.event.OrderTestEvent;
import test.eventtest.event.WalletAsyncEvent;
import test.eventtest.event.WalletFailEvent;
import test.eventtest.event.WalletSuccessEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final ApplicationEventPublisher publisher;
    private final EntityManagerService entityManagerService;

    @Transactional
    public void publishSuccessEvent(final String name) {
        walletRepository.save(new Wallet(null, name, 0));
        printLog();
        entityManagerService.logConnectionInfo();
        publisher.publishEvent(new WalletSuccessEvent(name));
    }

    @Transactional
    public void publishFailEvent(final String name) {
        walletRepository.save(new Wallet(null, name, 0));
        printLog();
        entityManagerService.logConnectionInfo();
        publisher.publishEvent(new WalletFailEvent(name));
    }

    @Async
    @Transactional
    public void publishAsyncEvent(final String name) throws InterruptedException {
        printLog();
        entityManagerService.logConnectionInfo();
        Thread.sleep(500);
        walletRepository.save(new Wallet(null, name, 0));
        publisher.publishEvent(new WalletAsyncEvent(name));
    }

    @Transactional
    public void orderTest() {
        publisher.publishEvent(new OrderTestEvent());
    }

    private void printLog() {
        final Thread thread = Thread.currentThread();
        log.info("service 메서드가 진행중인 스레드: id={}, name={}", thread.threadId(), thread.getName());
    }
}
