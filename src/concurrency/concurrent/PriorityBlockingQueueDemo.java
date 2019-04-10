package concurrency.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Random rand = new Random(47);
        PriorityBlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        exec.execute(new PrioritizedTaskProducer(queue,exec));
        exec.execute(new PrioritizedTaskConsumer(queue));
    }
}

class PrioritizedTaskProducer implements Runnable{

    private PriorityBlockingQueue<Runnable> queue;
    private ExecutorService exec;
    private Random rand = new Random(47);

    public PrioritizedTaskProducer(PriorityBlockingQueue<Runnable> queue,ExecutorService exec) {
        this.queue = queue;
        this.exec = exec;
    }

    @Override
    public void run() {
        try {
            for (int i=0;i<20;i++){
                queue.add(new PrioritizedTask(rand.nextInt(10)));
                Thread.yield();
            }
            System.out.println("前20个创建完成");
            for (int i=0;i<10;i++){
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            System.out.println("优先级为10 的创建完成");
            for (int i=0;i<10;i++){
                queue.add(new PrioritizedTask(i));
            }
            System.out.println("顺序创建10个优先级的任务完成");
            queue.add(new PrioritizedTask.EndSentinel(exec));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished PrioritizedTaskProducer!");


    }
}

class PrioritizedTaskConsumer implements Runnable{

    PriorityBlockingQueue<Runnable> queue ;

    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            System.out.println("开始消费");
            while (!Thread.interrupted())
                queue.take().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class PrioritizedTask  implements Runnable,Comparable<PrioritizedTask>{
    private static int count =0;
    private final int priority;
    private final int id = count ++;
    protected static List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    @Override
    public void run() {

        try {
            TimeUnit.MILLISECONDS.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("[%1$-3d]",priority) + "task " + id;
    }

    public String summary(){
        return "(" + id + ":" + priority + ")";
    }

    @Override
    public int compareTo(PrioritizedTask o) {
        return Integer.compare(o.priority, priority);
    }

    public static class EndSentinel extends PrioritizedTask {

        private ExecutorService exec;

        public EndSentinel(ExecutorService e) {
            super(-1);
            exec = e;
        }

        @Override
        public void run() {
            int count = 0;
            for (PrioritizedTask pt : sequence){
                System.out.print(pt.summary());
                if (++count % 5 == 0){
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println(this + " calling shutdown");
            exec.shutdownNow();
        }
    }
}