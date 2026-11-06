package com.mns.cda.suivimns.security;

import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory rate limit per key, with a fixed window: a key gets maxPerWindow actions, then has to
 * wait until windowSeconds have passed since the first action of its window.
 */
public class FixedWindowCounter {

    private final int maxPerWindow;
    private final long windowSeconds;
    private final ConcurrentHashMap<String, Window> windowsByKey = new ConcurrentHashMap<>();

    public FixedWindowCounter(int maxPerWindow, long windowSeconds) {
        this.maxPerWindow = maxPerWindow;
        this.windowSeconds = windowSeconds;
    }

    /**
     * Counts one action for this key if it still has some left in its window.
     *
     * @return false if the key has reached its limit (nothing is counted then)
     */
    public boolean tryAcquire(String key) {
        long now = System.currentTimeMillis();
        Window window = windowsByKey.compute(key, (k, existing) ->
                existing == null || isExpired(existing, now) ? new Window(now) : existing);

        synchronized (window) {
            if (window.count >= maxPerWindow) {
                return false;
            }
            window.count++;
            return true;
        }
    }

    public long secondsUntilReset(String key) {
        Window window = windowsByKey.get(key);
        if (window == null) {
            return 0;
        }
        long elapsedSeconds = (System.currentTimeMillis() - window.start) / 1000;
        return Math.max(0, windowSeconds - elapsedSeconds);
    }

    private boolean isExpired(Window window, long now) {
        return now - window.start >= windowSeconds * 1000;
    }

    private static final class Window {
        private final long start;
        private int count;

        private Window(long start) {
            this.start = start;
        }
    }
}
