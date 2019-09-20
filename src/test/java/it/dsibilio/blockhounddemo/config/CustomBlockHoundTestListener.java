package it.dsibilio.blockhounddemo.config;

import org.junit.platform.launcher.TestExecutionListener;
import reactor.blockhound.BlockHound;
import reactor.core.scheduler.ReactorBlockHoundIntegration;

public class CustomBlockHoundTestListener implements TestExecutionListener {
    static {
        BlockHound
        .builder()
        .allowBlockingCallsInside(
                "ch.qos.logback.classic.Logger",
                "buildLoggingEventAndAppend")
        .with(new ReactorBlockHoundIntegration())
        .install();
    }
}
