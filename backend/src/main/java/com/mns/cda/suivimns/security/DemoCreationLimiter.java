package com.mns.cda.suivimns.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Limits how many trial accounts one IP can create, in memory.
 * Fixed window: an IP can create maxPerIp accounts, then has to wait until windowSeconds
 * have passed since the first creation of the window.
 */
@Component
public class DemoCreationLimiter {

    @Value("${app.demo.max-accounts-per-ip:3}")
    private int maxPerIp;

    @Value("${app.demo.ip-window-seconds:3600}")
    private long windowSeconds;

    private final ConcurrentHashMap<String, Window> windowsByIp = new ConcurrentHashMap<>();

    /**
     * Counts one creation for this IP if it still has some left in its window.
     *
     * @return false if the IP has reached its limit (nothing is counted then)
     */
    public boolean tryAcquire(String ip) {
        long now = System.currentTimeMillis();
        Window window = windowsByIp.compute(ip, (k, existing) ->
                existing == null || isExpired(existing, now) ? new Window(now, 0) : existing);

        synchronized (window) {
            if (window.count >= maxPerIp) {
                return false;
            }
            window.count++;
            return true;
        }
    }

    public long secondsUntilReset(String ip) {
        Window window = windowsByIp.get(ip);
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

        private Window(long start, int count) {
            this.start = start;
            this.count = count;
        }
    }
}
