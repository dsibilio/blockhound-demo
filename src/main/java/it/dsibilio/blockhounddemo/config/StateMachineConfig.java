package it.dsibilio.blockhounddemo.config;

import it.dsibilio.blockhounddemo.config.StateMachineConfig.Events;
import it.dsibilio.blockhounddemo.config.StateMachineConfig.States;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.time.Duration;
import java.util.EnumSet;
import java.util.concurrent.TimeUnit;

@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {
    @Autowired
    private StringRedisTemplate blockingRedisTemplate;
    @Autowired
    private ReactiveStringRedisTemplate reactiveRedisTemplate;

    @Override
    public void configure(StateMachineConfigurationConfigurer<States, Events> config)
            throws Exception {
        config
        .withConfiguration()
        .autoStartup(true);
    }

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states)
            throws Exception {
        states
        .withStates()
        .initial(States.SI)
        .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions)
            throws Exception {
        transitions
        .withExternal()
        .source(States.SI).target(States.S1).event(Events.E1)
        .action(stateContext -> blockingRedisTemplate.expire("someKey", 5, TimeUnit.SECONDS))
        .and()
        .withExternal()
        .source(States.SI).target(States.S2).event(Events.E2)
        .action(stateContext -> reactiveRedisTemplate.expire("someKey", Duration.ofSeconds(5)).subscribe());
    }

    public enum States {
        SI, S1, S2
    }

    public enum Events {
        E1, E2
    }
}
