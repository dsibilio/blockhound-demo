package it.dsibilio.blockhounddemo;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlockhoundDemoApplicationTests {
    @Autowired
    private BlockhoundDemoApplication underTest;

    @Test
    void blockingInNonBlockingThreadsShouldNotBeAllowed() {
        StepVerifier
        .create(underTest.blockingIsNotAllowed())
        .expectErrorMatches(e -> {
            e.printStackTrace();
            
            return e instanceof Error &&
                    e.getMessage().contains("Blocking call!");
        })
        .verify();
    }

    @Test
    void blockingInBlockingThreadsShouldBeAllowed() {
        StepVerifier
        .create(underTest.blockingIsAllowed())
        .expectNext(1)
        .verifyComplete();
    }
}
