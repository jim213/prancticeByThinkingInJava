package concurrency.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class AttemptLocking {
    private ReentrantLock lock = new ReentrantLock();
    public void untimed(){
        boolean captured = lock.tryLock();
        try{
            System.out.println("tryLock():"+ captured);
        }finally {
            if (captured){
                lock.unlock();
            }
        }
    }
    public void timed(){
        boolean captured = false;
        try {
            captured = lock.tryLock(2,TimeUnit.SECONDS);
        }catch (InterruptedException e){
            throw new RuntimeException();
        }
        try {
            System.out.println("tryLock(2,TimeUnit.SECONDS):" + captured);
        }finally {
            if (captured){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final AttemptLocking al = new AttemptLocking();
        al.timed();
        al.untimed();
        new Thread(){
            {setDaemon(true);}
            @Override
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        TimeUnit.MILLISECONDS.sleep(1);
        al.untimed();
        al.timed();
    }
}
