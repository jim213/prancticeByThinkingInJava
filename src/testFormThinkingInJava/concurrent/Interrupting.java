package testFormThinkingInJava.concurrent;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 中断发生阻塞的任务
 */
public class Interrupting {
    private static ExecutorService exec = Executors.newCachedThreadPool();
    static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interruping " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());
        TimeUnit.SECONDS.sleep(3);
        System.out.println("System.exit(0)");
        System.exit(0);
    }
}

class SleepBlocked implements Runnable{

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable {
    private InputStream in ;
    public IOBlocked(InputStream is ){
        in = is;
    }
    @Override
    public void run() {

        try {
            System.out.println("Waiting for input!");
            in.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted()){
                System.out.println("Interrupted by IOBlocked");
            }else {
                throw new RuntimeException();
            }

        }
    }
}

class SynchronizedBlocked implements Runnable {

    public synchronized void f(){
        while (true){
            Thread.yield();
        }
    }

    public SynchronizedBlocked(){
        new Thread(() -> f()).start();
    }
    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}