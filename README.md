# BlockHound Demo

This project serves as a demo for BlockHound, which detects execution of blocking code (eg. `Thread#sleep`) inside non-blocking threads.

It is particularly useful when enabled during tests execution to discover these issues before bringing them in production.

The `AllowLoggingIntegration.java` shows a way to change BlockHound settings during tests execution by using custom integrations.

The `BlockHoundDemoApplicationTests.java` class shows a very straightforward example of wrongly/correctly invoking blocking operations relying on different Reactor schedulers _(parallel vs elastic)_.

The `StateMachineTransitionServiceTests.java` is just a way more realistic and probable example of the same.
  
## Getting started

Just run `mvn clean test`, sit back and relax.

## Reference Documentation
For further reference, please consider the following resources:

* [BlockHound](https://github.com/reactor/BlockHound)
* [BlockHound Documentation](https://github.com/reactor/BlockHound/blob/master/docs/README.md)
* [Spring Statemachine Documentation](https://docs.spring.io/spring-statemachine/docs/2.1.3.RELEASE/reference/#developing-your-first-spring-statemachine-application)
