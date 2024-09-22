package test.eventtest.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import test.eventtest.event.OrderTestEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderEventListener {

//    @Order(5)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleEvnet5(final OrderTestEvent event) {
        log.info("handleEvnet5 호출");
    }

//    @Order(2)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleEvnet1(final OrderTestEvent event) {
        log.info("handleEvnet1 호출");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvnet2(final OrderTestEvent event) {
        log.info("handleEvnet2 호출");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleEvnet3(final OrderTestEvent event) {
        log.info("handleEvnet3 호출");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvnet4(final OrderTestEvent event) {
        log.info("handleEvnet4 호출");
    }

}
