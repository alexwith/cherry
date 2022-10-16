package net.cherry.thread;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public interface CherryExecutor {

    ExecutorService CLIENT_EXECUTOR = Executors.newFixedThreadPool(10, new CherryThreadFactory());

    static <T> CompletableFuture<T> supplyFuture(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, CLIENT_EXECUTOR);
    }
}
