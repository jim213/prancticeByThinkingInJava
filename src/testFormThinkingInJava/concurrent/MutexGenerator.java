package testFormThinkingInJava.concurrent;

import java.awt.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexGenerator extends IntGenerator {
    private int currentEventVal = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public int next() {
        lock.lock();
        try {
            ++currentEventVal;
            Thread.yield();
            ++currentEventVal;
            System.out.println(currentEventVal);
            return currentEventVal;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        EventChecker.test(new MutexGenerator());
    }
}
