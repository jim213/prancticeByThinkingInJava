package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CashedThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i=0;i<5;i++)
            executorService.execute(new LiftOff(4));
        executorService.shutdown();
        System.out.println("-------------------");
    }
}
