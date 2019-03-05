package testFormThinkingInJava.concurrent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class ToastTest {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue dryQueue = new ToastQueue();
        ToastQueue burreredQueue = new ToastQueue();
        ToastQueue finishedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Butterer(dryQueue,burreredQueue));
        exec.execute(new Jammer(finishedQueue,burreredQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();
    }
}

class Toast{
    public enum Status { DRY,BUTTERED,JAMMED}
    private  Status status = Status.DRY;
    private final int id;
    public Toast(int id ){
        this.id = id;
    }
    public Status getStatus() {
        return status;
    }
    public int getId() {
        return id;
    }
    public void butter() {
        status = Status.BUTTERED;
    }
    public void jam() {
        status = Status.JAMMED;
    }

    @Override
    public String toString() {
        return "Toast{" +"status=" + status +", id=" + id +'}';
    }
}

class ToastQueue extends LinkedBlockingDeque<Toast>{}

class Toaster implements Runnable{
   private ToastQueue toastQueue;
   private int count = 0;
    private Random rand = new Random(47);
   Toaster(ToastQueue tq){this.toastQueue = tq;}
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                Toast toast = new Toast(count++);
                System.out.println(toast);
                toastQueue.put(toast);
            }
        } catch (InterruptedException e){
            System.out.println("Toaster interrupted!");
        }
        System.out.println("Toaster off");
    }
}

class Butterer implements Runnable {
    private ToastQueue dryQueue ;
    private ToastQueue burreredQueue;
    public Butterer(ToastQueue dryQueue,ToastQueue burreredQueue){
        this.dryQueue = dryQueue;
        this.burreredQueue = burreredQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = dryQueue.take();
                toast.butter();
                System.out.println(toast);
                burreredQueue.put(toast);
            }
        } catch (InterruptedException e){
            System.out.println("Butterer interrupted!");
        }
        System.out.println("Butterer off");
    }
}

class Jammer implements Runnable{
    private ToastQueue burreredQueue;
    private ToastQueue finishedQueue ;
    public Jammer(ToastQueue finishedQueue,ToastQueue burreredQueue){
        this.finishedQueue = finishedQueue;
        this.burreredQueue = burreredQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = burreredQueue.take();
                toast.jam();
                System.out.println(toast);
                finishedQueue.put(toast);
            }
        } catch (InterruptedException e){
            System.out.println("Jammer interrupted!");
        }
        System.out.println("Jammer off");
    }
}

class Eater implements Runnable {
    private ToastQueue finishedQueue ;
    private int count = 0;
    public Eater(ToastQueue finishedQueue){
        this.finishedQueue = finishedQueue;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                Toast toast = finishedQueue.take();
                if (toast.getId() != count ++ || toast.getStatus() != Toast.Status.JAMMED){
                    System.out.println(">>>>>>>>>>ERROR : " + toast);
                    System.exit(1);
                } else {
                    System.out.println("Chomp !! " + toast);
                }
            }
        } catch (InterruptedException e){
            System.out.println("Eater interrupted!");
        }
        System.out.println("Eater off");
    }
}

