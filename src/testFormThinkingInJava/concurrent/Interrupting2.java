package testFormThinkingInJava.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 中断发生阻塞的任务
 */
public class Interrupting2 {

    public static void main(String[] args) throws  Exception {
        Thread t = new Thread(new Blocked2());
        t.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("use t.interrupt..");
        t.interrupt();
    }
}
class Blocked2 implements Runnable{
    BlockedMutex blockedMutex = new BlockedMutex();
    @Override
    public void run() {
        System.out.println("waiting for f() in BlockedMutex");
        blockedMutex.f();
        System.out.println("brocket out of brocked call");
    }
}
class BlockedMutex{
    private Lock lock = new ReentrantLock();
    public BlockedMutex(){
        lock.lock();
    }
    public void f(){
        try {
            lock.lockInterruptibly();
            System.out.println("lock in f()");
        } catch (InterruptedException e) {
            System.out.println("Interrupted from lock");
        }
    }
}