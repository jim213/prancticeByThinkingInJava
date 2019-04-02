package concurrency;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RandomSleep implements Runnable {

    private int id ;
    private static Random rand = new Random();

    public RandomSleep(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            int i = rand.nextInt(10);
            TimeUnit.SECONDS.sleep(i+1);
            System.out.println("#" +id+ "睡眠了" + i + "秒");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i =0;i<5;i++)
            exec.execute(new RandomSleep(i));
        exec.shutdown();
    }
}
