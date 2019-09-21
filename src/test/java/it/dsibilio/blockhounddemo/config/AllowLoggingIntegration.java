package it.dsibilio.blockhounddemo.config;

import com.google.auto.service.AutoService;
import reactor.blockhound.BlockHound.Builder;
import reactor.blockhound.integration.BlockHoundIntegration;

@AutoService(BlockHoundIntegration.class)
public class AllowLoggingIntegration  implements BlockHoundIntegration {

    @Override
    public void applyTo(Builder builder) {
        builder.allowBlockingCallsInside(
                "ch.qos.logback.classic.Logger",
                "buildLoggingEventAndAppend");
    }

}