package concurrency;

import testFormThinkingInJava.LiftOff;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread {
    public static void main(String[] args) {

//
//        for (int i = 0 ; i<10 ;i++){
//            new Thread(new LiftOff()).start();
//        }
//        System.out.println("wait fot over launch!");

        ExecutorService exec = Executors.newFixedThreadPool(3);
//        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i =0 ; i< 10 ; i++){
            exec.execute(new LiftOff());
        }
        exec.shutdown();
    }
}
