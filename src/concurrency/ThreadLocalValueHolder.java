package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalValueHolder {
    private static ThreadLocal<Integer> value = new ThreadLocal<Integer>(){
        private Random rand = new Random(47);
        @Override
        protected Integer initialValue() {
            return rand.nextInt(10000);
        }
    };
    public static void incerment(){
        value.set(value.get() + 1);
    }
    public static int get(){
        return value.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i=0;i<5;i++){
            exec.execute(new Accessor(i));
        }
        TimeUnit.SECONDS.sleep(1);
        exec.shutdownNow();
    }
}

class Accessor implements Runnable{
    private final int id;

    public Accessor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){
            ThreadLocalValueHolder.incerment();
            System.out.println(this);
            Thread.yield();
        }
    }

    @Override
    public String toString() {
        return "# id " + id + ":" + ThreadLocalValueHolder.get();
    }
}