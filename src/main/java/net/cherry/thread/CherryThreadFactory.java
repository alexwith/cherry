package net.cherry.thread;

import java.util.concurrent.ThreadFactory;

public class CherryThreadFactory implements ThreadFactory {
    private static final String THREAD_NAME = "cherry-thread";

    @Override
    public Thread newThread(Runnable runnable) {
        return new Thread(runnable, THREAD_NAME);
    }
}
