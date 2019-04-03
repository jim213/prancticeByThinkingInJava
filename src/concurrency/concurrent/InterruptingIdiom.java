package concurrency.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 中断发生阻塞的任务
 */
public class InterruptingIdiom{

    public static void main(String[] args) throws  Exception {
        if (args.length != 1){
            System.out.println("请出入一个参数！");
            System.exit(1);
        }
        Thread t = new Thread(new Blocked3());
        t.start();
        TimeUnit.MILLISECONDS.sleep(new Integer(args[0]));
        t.interrupt();
    }
}
class Blocked3 implements Runnable{
    private volatile double d = 0.0;
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                NeedsCleanup n1 = new NeedsCleanup(1);
                try {
                    System.out.println("sleeping");
                    TimeUnit.SECONDS.sleep(1);
                    NeedsCleanup n2 = new NeedsCleanup(2);
                    try {
                        System.out.println("计算");
                        for (int i = 1;i<250000;i++){
                            d = d + (Math.PI + Math.E) /d;
                            System.out.println("完成计算了");
                        }
                    }finally {
                        n2.cleanup();
                    }
                }finally {
                    n1.cleanup();
                }
            }
            System.out.println("Exiting via while() test");
        }catch (InterruptedException e ){
            System.out.println("Exiting via InterruptedException");
        }
    }
}

class NeedsCleanup {
    private final int id;
    public NeedsCleanup(int id){
        this.id = id;
        System.out.println("NeedsCleanup" + id);
    }
    public void cleanup(){
        System.out.println("Clean up "+ id);
    }
}