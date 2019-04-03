package concurrency.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 模拟汽车打蜡/抛光过程
 */
public class WaxMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.submit(new WaxOff(car));
        exec.submit(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}
class Car{
    private Boolean waxOn = false;
    public synchronized void waxed(){
        waxOn = true;
//        notifyAll();
        notify(); //the same with notifyAll()
    }
    public synchronized void buffed(){
        waxOn = false;
//        notifyAll();
        notify(); //the same with notifyAll()
    }
    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn == false)
            wait();
    }
    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn == true)
            wait();
    }

}

class WaxOff implements  Runnable{
    private Car car;
    public WaxOff(Car car){
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println("Wax Off !!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
                car.waitForWaxing();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting via interrupt ");
        }
        System.out.println("Ending Wax Off task!!!");
    }
}

class WaxOn implements Runnable{
    private Car car;
    public WaxOn(Car car){
        this.car = car;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println("Wax On !!");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffing();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting via interrupt ");
        }
        System.out.println("Ending Wax On task!!!");
    }
}