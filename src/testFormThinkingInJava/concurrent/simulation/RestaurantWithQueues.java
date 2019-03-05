package testFormThinkingInJava.concurrent.simulation;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RestaurantWithQueues {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Restaurant restaurant = new Restaurant(exec,5,2);
        exec.execute(restaurant);
        TimeUnit.SECONDS.sleep(10);
        exec.shutdownNow();
    }
}

class Restaurant implements Runnable{
    private static Random rand = new Random();
    private ExecutorService exec ;
    private List<WaitPerson> waitPersons = new ArrayList<>();
    private List<Chef> chefs = new ArrayList<>();
    BlockingDeque<Order> orders = new LinkedBlockingDeque<>();
    public Restaurant(ExecutorService exec,int nWaitPersons,int nChefs) {
        this.exec = exec;
        for (int i= 0;i<nWaitPersons;i++){
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
            exec.execute(waitPerson);
        }
        for (int i= 0;i<nChefs;i++){
            Chef chef = new Chef(this);
            chefs.add(chef);
            exec.execute(chef);
        }
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                WaitPerson wp = waitPersons.get(rand.nextInt(waitPersons.size()));
                Customer1 c = new Customer1(wp);
                exec.execute(c);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }catch (InterruptedException e){
            System.out.println("Restaurant interrupted");
        }
        System.out.println("Restaurant closing");
    }
}
class WaitPerson implements Runnable{
    private Restaurant restaurant;

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {

    }
}
class Chef implements Runnable{
    private static int count = 0;
    private final int id = count++;
    private final Restaurant restaurant;
    private static Random rand = new Random();
    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            if (!Thread.interrupted()){
                Order order = restaurant.orders.take();
                Food requestedItem = order.item();
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                Plate plate = new Plate(order,requestedItem);



            }
        }catch (InterruptedException e){

        }
    }
}
class Order{
    private static int count = 0;
    private final int id =count++;
    private final Customer customer;
    private final WaitPerson waitPerson;
    private final Food food;

    public Order(Customer customer, WaitPerson waitPerson, Food food) {
        this.customer = customer;
        this.waitPerson = waitPerson;
        this.food = food;
    }

    public Customer getCustomer() {
        return customer;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customer=" + customer +
                ", waitPerson=" + waitPerson +
                ", food=" + food +
                '}';
    }

    public Food item(){
        return food;
    }

}
class Customer1 implements Runnable{
    private static int count = 0;
    private final int id = count++;
    private SynchronousQueue<Plate> placeSetting = new SynchronousQueue<>();
    private final WaitPerson waitPerson;
    public Customer1(WaitPerson waitPerson) {
        this.waitPerson = waitPerson;
    }
    public void deliver(Plate p) throws InterruptedException {
        placeSetting.put(p);
    }

    @Override
    public void run() {
    }
}
class Food{

}
class Plate{
    private final Order order;
    private final Food food;

    public Plate(Order order, Food food) {
        this.order = order;
        this.food = food;
    }

    public Order getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    @Override
    public String toString() {
        return "Plate{" +
                "order=" + order +
                ", food=" + food +
                '}';
    }
}