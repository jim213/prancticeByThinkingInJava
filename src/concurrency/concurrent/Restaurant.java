package concurrency.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName   餐厅（生产者消费者模型）
 * @author jinjm
 * @date 2018-11-14 10:53:14
 */
public class Restaurant {
    Meal meal;
    Chef chef = new Chef(this);
    WaitPerson waitPerson =  new WaitPerson(this);
    ExecutorService exec = Executors.newCachedThreadPool();
    public Restaurant(){
        exec.execute(chef);
        exec.execute(waitPerson);
    }
    public static void main(String[] args) {
        new Restaurant();
    }
}

class Meal{
    private final int orderNum;
    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "Meal : " +orderNum;
    }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;
    public WaitPerson(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    if (restaurant.meal == null){
                        wait();
                    }
                }
                System.out.println("waitPerson got " + restaurant.meal);
                synchronized (restaurant.chef){
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        } catch (InterruptedException e){
            System.out.println("WaitPerson interrupt!");
        }
    }
}

class Chef implements Runnable {
    private Restaurant restaurant;
    private int count;
    public Chef(Restaurant restaurant){
        this.restaurant = restaurant;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this) {
                    if (restaurant.meal != null){
                        wait();
                    }
                }
                if (++count == 10){
                    System.out.println("Out of food ,closing");
                    restaurant.exec.shutdownNow();
                }
                System.out.println("Order Up !!!");
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
//                TimeUnit.MILLISECONDS.sleep(100);     //观察分析两种情况
            }
        }catch (InterruptedException e){
            System.out.println("Chef interrupted!");
        }

    }
}