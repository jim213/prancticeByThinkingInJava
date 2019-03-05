package testFormThinkingInJava.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventChecker implements Runnable {
    private IntGenerator generator;
    private final int id;
    public EventChecker(IntGenerator g,int i){
        generator = g;
        id = i;
    }

    @Override
    public void run() {
        while (!generator.isCanceld()){
            int val = generator.next();
            if (val % 2 !=0) {
                System.out.println(val+" not event!");
                generator.cancle();
            }
        }
    }

    public static void test(IntGenerator gp,int count){
        System.out.println("press ctrl + c to exit!");
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i =0;i<count;i++){
            es.execute(new EventChecker(gp,i));
        }
        es.shutdown();
    }

    public static void test(IntGenerator gp){
        test(gp, 10);
    }
}
