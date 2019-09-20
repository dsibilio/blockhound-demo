package it.dsibilio.blockhounddemo.service;

import it.dsibilio.blockhounddemo.config.StateMachineConfig.Events;
import it.dsibilio.blockhounddemo.config.StateMachineConfig.States;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

@SpringBootTest
class StateMachineTransitionServiceTests {
    @Autowired
    private StateMachineTransitionService underTest;

    @Test
    void blockingInNonBlockingThreadsShouldNotBeAllowed() {
        Mono<StateMachine<States, Events>> blockingStateMachine = 
                underTest
                .blockingStateMachine()
                .subscribeOn(Schedulers.parallel());

        StepVerifier
        .create(blockingStateMachine)
        .expectErrorMatches(e -> {
            e.printStackTrace();

            return e instanceof Error &&
                    e.getMessage().contains("Blocking call!");
        })
        .verify();
    }

    @Test
    void blockingInBlockingThreadsShouldBeAllowed() {
        Mono<StateMachine<States, Events>> blockingStateMachine = 
                underTest
                .blockingStateMachine()
                .subscribeOn(Schedulers.elastic());

        StepVerifier
        .create(blockingStateMachine)
        .expectNextCount(1)
        .verifyComplete();
    }

    @Test
    void nonBlockingShouldNotBeFlagged() {
        Mono<StateMachine<States, Events>> nonBlockingStateMachine = 
                underTest
                .nonBlockingStateMachine()
                .subscribeOn(Schedulers.parallel());

        StepVerifier
        .create(nonBlockingStateMachine)
        .expectNextCount(1)
        .verifyComplete();
    }
}
