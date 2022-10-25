package net.cherry.util;

public class SneakyThrows {

    public static void run(SneakyRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T supply(SneakySupplier<T> supplier) {
        try {
            return supplier.supply();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public interface SneakyRunnable {

        void run() throws Exception;
    }

    public interface SneakySupplier<T> {

        T supply() throws Exception;
    }
}
