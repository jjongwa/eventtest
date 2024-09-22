package test.eventtest.wallet;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WalletTest {

    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    /*
     |  @Transactional  | @Async | Lock 여부 |  결과 |
     |  --------------  | ------ | -------- | ---- |
     |         X        |    X   |     X    |   X  |
     | REQUIRED(default)|    X   |     X    |   X  |
     |   REQUIRES_NEW   |    X   |     X    |   O  |
     |         X        |    O   |     X    |   O  |
     |         X        |    X   |     O    |   X  |
     |   REQUIRES_NEW   |    X   |     O    |   O  |
     |         X        |    O   |     O    |   O  |
     */
    @Test
    void TransactionalEventListener_주의사항_테스트() throws InterruptedException {
        // when
        List<Wallet> before = walletRepository.findAll();

        walletService.publishSuccessEvent("dino");
        Thread.sleep(1000);
        List<Wallet> after = walletRepository.findAll();

        // then
        System.out.println("before = " + before);
        System.out.println("after = " + after);
    }

    @Test
    void 리스너_호출_순서_테스트() {
        // when
        walletService.orderTest();
    }


    /*
     | @Transactional | @Async |  DB Pool Size |
     | -------------- | ------ | ------------- |
     |  REQUIRES_NEW  |    X   |       2       |
     |  REQUIRES_NEW  |    X   |       3       |
     |  REQUIRES_NEW  |    O   |       2       |
     */
    @Test
    void REQUIERS_NEW_일떄_DB_커넥션_점유_테스트() throws InterruptedException {
        // given

        // when
        walletService.publishAsyncEvent("디노");
        walletService.publishAsyncEvent("벨라");

        Thread.sleep(2000);

        // then
        final List<Wallet> all = walletRepository.findAll();
        System.out.println("all = " + all);
    }
}
