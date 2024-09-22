package test.eventtest.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import test.eventtest.event.WalletAsyncEvent;
import test.eventtest.event.WalletSuccessEvent;
import test.eventtest.wallet.EntityManagerService;
import test.eventtest.wallet.WalletListenerService;

@Slf4j
@RequiredArgsConstructor
@Component
public class WalletEventListener {

    private final WalletListenerService walletListenerService;
    private final EntityManagerService entityManagerService;

    //    @Async
//    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleSuccessEvent(final WalletSuccessEvent event) throws IllegalAccessException {
        try {
            log.info("리스너 호출 - handleSuccessEvent");
//            entityManagerService.findWithEntityLock(event.name());
            walletListenerService.chargeCash(event.name(), 1000);
        } catch (Exception e) {
            log.error("리스너 메서드에서 에러 발생\nmessage={}", e.getMessage(), e);
            throw new IllegalAccessException("리스너 메서드에서 에러 발생");
        }
    }

    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener
    public void handleEventByAsyncMethod(final WalletAsyncEvent event) {
        try {
            log.info("리스너 호출 - handleEventByAsyncMethod");
            entityManagerService.logConnectionInfo();
            walletListenerService.chargeCash(event.name(), 1000);
        } catch (Exception e) {
            log.error("리스너 메서드에서 예외 발생\nmessage={}", e.getMessage(), e);
        }
    }
}
