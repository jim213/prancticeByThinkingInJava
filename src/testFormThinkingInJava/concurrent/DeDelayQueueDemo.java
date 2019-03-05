package testFormThinkingInJava.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class DeDelayQueueDemo {
    public static void main(String[] args) {
        Random rand = new Random(17);
        ExecutorService exec = Executors.newCachedThreadPool();
        DelayQueue<DelayTask> queue = new DelayQueue<>();
        for (int i=0;i<20;i++){
            queue.put(new DelayTask(rand.nextInt(5000)));
            queue.add(new DelayTask.EndSentinel(5000, exec));
            exec.execute(new DelayedTaskConsumer(queue));
        }
    }
}

class DelayTask implements Runnable , Delayed {
    private static int count = 0;
    private final int id = count ++;
    private final int delta;
    private final long trigger;
    protected static List<DelayTask> sequence = new ArrayList<>();
    public  DelayTask(int delayInMilliseconds){
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + NANOSECONDS.convert(delta,MILLISECONDS) ;
        sequence.add(this);
    }
    @Override
    public long getDelay (TimeUnit unit){
        return unit.convert(trigger - System.nanoTime(), NANOSECONDS);
    }



    @Override
    public int compareTo(Delayed o) {
        DelayTask that = (DelayTask) o;
        if (trigger < that.trigger) return -1;
        if (trigger > that.trigger) return 1;
        return 0;
    }

    @Override
    public void run() {
        System.out.print(this +" ");
    }

    @Override
    public String toString() {
        return String.format("[%1$-4d]",delta) + "Task " + id;
    }
    public String summary(){
        return "(" + id +":"+ delta + ")";
    }

    public static class EndSentinel extends DelayTask {
        private ExecutorService exec;
        public EndSentinel(int delay,ExecutorService e) {
            super(delay);
            exec = e;
        }

        @Override
        public void run() {
            for (DelayTask dt : sequence){
                System.out.print(dt.summary() + " ");
            }
            System.out.print(this + " Calling shutdownNow() ");
            exec.shutdownNow();
        }
    }
}


class DelayedTaskConsumer implements Runnable{
    private DelayQueue<DelayTask> q;

    public DelayedTaskConsumer(DelayQueue<DelayTask> q) {
        this.q = q;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted())
                q.take().run();
            } catch (InterruptedException e) {

        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}