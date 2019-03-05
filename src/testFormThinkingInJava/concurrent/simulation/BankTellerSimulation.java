package testFormThinkingInJava.concurrent.simulation;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankTellerSimulation {
     static final int MAX_LINE_SIZE = 50;
     static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        exec.execute(new CustomerGenerator(customers));
        exec.execute(new TellerManager(customers,exec,ADJUSTMENT_PERIOD));
        if (args.length>0){
            TimeUnit.SECONDS.sleep(new Integer(args[0]));
        }else {
            System.out.println("press Enter to quit");
            System.in.read();
        }
        exec.shutdown();
    }
}

class Customer {
    private final int serviceTime;

    @Override
    public String toString() {
        return "Customer{" +
                "serviceTime=" + serviceTime +
                '}';
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public Customer(int serviceTime) {

        this.serviceTime = serviceTime;
    }
}

class CustomerLine extends ArrayBlockingQueue<Customer>  {

    public CustomerLine(int maxLineSize) {
        super(maxLineSize);
    }

    @Override
    public String toString() {
        if (this.size() == 0)
            return "[empty]";
        StringBuilder result = new StringBuilder();
        for(Customer customer : this){
            result.append(customer);
        }
        return result.toString();
    }
}
class CustomerGenerator implements Runnable{
    private CustomerLine customers;
    private Random rand = new Random();
    public CustomerGenerator(CustomerLine cq){
        customers = cq;
    }
    @Override
    public void run() {
            try {
                while (!Thread.interrupted()){
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
                    customers.add(new Customer(rand.nextInt(1000)));
                }
            } catch (InterruptedException e) {
                System.out.println("CustomerGenerator interrupted ");
            }
        System.out.println("CustomerGenerator terminating");
    }
}

class Teller implements Runnable,Comparable<Teller> {
    private static int count = 0;
    private final int id = count++;
    private int customersServed = 0;
    private CustomerLine customers;
    private boolean servingCustomerLine = true;

    public Teller(CustomerLine customers) {
        this.customers = customers;
    }

    @Override
    public int compareTo(Teller other) {
        return Integer.compare(this.customersServed, other.customersServed);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized(this){
                    customersServed ++;
                    while (!servingCustomerLine)
                        wait();
                }
            }
        } catch (InterruptedException e){
            System.out.println(this +" interrupted");
        }
        System.out.println(this + " terminating");
    }
    public synchronized void doSomethingElse(){
        customersServed = 0;
        servingCustomerLine = false;
    }
    public synchronized void serveCustomerLine(){
        assert !servingCustomerLine:"already serving : " + this;
        servingCustomerLine = true;
        notifyAll();
    }
    public String shortString() {
        return "T " +id;
    }

    @Override
    public String toString() {
        return "Teller{" + "id=" + id + '}';
    }
}

class TellerManager implements Runnable{
    private CustomerLine customers;
    private ExecutorService exec;
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();
    private Queue<Teller> tellersDoingOtherThings = new LinkedList<>();
    private int adjustmentPeriod;
    private static Random rand = new Random();

    public TellerManager(CustomerLine customers, ExecutorService exec, int adjustmentPeriod) {
        this.customers = customers;
        this.exec = exec;
        this.adjustmentPeriod = adjustmentPeriod;
        Teller teller = new Teller(customers);
        exec.execute(teller);
        workingTellers.add(teller);
    }
    public void adjustTellerNumber(){
        if (customers.size()/workingTellers.size() >2){
            if (tellersDoingOtherThings.size() >0){
                Teller teller = tellersDoingOtherThings.remove();
                teller.serveCustomerLine();
                workingTellers.offer(teller);
                return;
            }
            Teller teller = new Teller(customers);
            exec.execute(teller);
            workingTellers.add(teller);
            return;
        }
        if (workingTellers.size()>1 && customers.size()/workingTellers.size()<2){
            reassignOneTeller();
        }
        if (customers.size() == 0 && workingTellers.size()>1){
            reassignOneTeller();
        }
    }
    public void reassignOneTeller(){
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        tellersDoingOtherThings.add(teller);
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
                adjustTellerNumber();
                System.out.print(customers + " { ");
                for (Teller teller:workingTellers){
                    System.out.print(teller.shortString() + " ");
                }
                System.out.println(" } ");
            }
        }catch (InterruptedException e){
            System.out.println(this + " interrupted ");
        }
        System.out.println(this + " terminating");
    }

    @Override
    public String toString() {
        return "TellerManager ";
    }
}