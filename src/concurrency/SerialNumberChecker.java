package concurrency;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SerialNumberChecker {

    private static CircularSet circularSet = new CircularSet(1000);

    static class SerialChecker implements Runnable{
        @Override
        public void run() {
            while (true){
                int i = SerialNumberGenerator.nextSerialNumber();
                if (circularSet.contains(i)){
                    System.out.println("发现了重复的数字：" + i);
                    System.exit(0);
                }
                circularSet.add(i);
            }

        }
    }
    private static final int SIZE=10;
    private static ExecutorService exec = Executors.newCachedThreadPool();
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0;i<SIZE;i++){
            exec.execute(new SerialChecker());
        }
        TimeUnit.SECONDS.sleep(3);
        System.out.println("未发现重复数字,准备退出");
        System.exit(0);
    }


}

class CircularSet {
    private int[]  array;
    private int length;
    private int index = 0;
    public CircularSet(int length){
        for (int i=0;i<length;i++){
            array = new int[length];
            this.length = length;
        }
    }
    public synchronized void add(int i){
        array[index] = i;
        index = ++index % length;
    }
    public synchronized boolean contains(int val){
        for (int i=0;i<length;i++){
            if (array[i] == val) return true;
        }
        return false;
    }
}