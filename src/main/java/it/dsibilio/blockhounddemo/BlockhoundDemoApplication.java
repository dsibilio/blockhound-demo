package it.dsibilio.blockhounddemo;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockhoundDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlockhoundDemoApplication.class, args);
    }

    public Mono<Integer> blockingIsAllowed() {
        return getBlockingMono().subscribeOn(Schedulers.elastic());
    }

    public Mono<Integer> blockingIsNotAllowed() {
        return getBlockingMono().subscribeOn(Schedulers.parallel());
    }

    private Mono<Integer> getBlockingMono() {
        return Mono.just(1).doOnNext(i -> block());
    }

    private void block() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
