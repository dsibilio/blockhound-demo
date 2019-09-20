package it.dsibilio.blockhounddemo.service;

import it.dsibilio.blockhounddemo.config.StateMachineConfig.Events;
import it.dsibilio.blockhounddemo.config.StateMachineConfig.States;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StateMachineTransitionService {
    @Autowired
    private StateMachineFactory<States, Events> stateMachineFactory;

    public Mono<StateMachine<States, Events>> blockingStateMachine() {
        return getStateMachine(Events.E1);
    }

    public Mono<StateMachine<States, Events>> nonBlockingStateMachine() {
        return getStateMachine(Events.E2);
    }

    private Mono<StateMachine<States, Events>> getStateMachine(Events event) {
        return Mono.just(stateMachineFactory.getStateMachine(UUID.randomUUID()))
                .doOnNext(stateMachine -> stateMachine.sendEvent(event));
    }
}
