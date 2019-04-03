package concurrency.concurrent.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    final static int SIZE = 25;

    public static void main(String[] args) throws InterruptedException {
        final Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i=0;i<SIZE;i++){
            exec.execute(new CheckOutTask<Fat>(pool));
        }
        System.out.println("all CheckOutTask created !");

        List<Fat> list = new ArrayList<>();
        for (int i=0;i<SIZE;i++){
            Fat f = pool.checkOut();
            System.out.println(i +  " : main() thread checked out ");
            f.operation();
            list.add(f);
        }
        Future<?> submit = exec.submit(() -> {
            try {
                pool.checkOut();
            } catch (InterruptedException e) {
                System.out.println("checkedOut() Interrupted");
            }
        });
        TimeUnit.SECONDS.sleep(2);
        submit.cancel(true);
        System.out.println("checking in objects in " + list);
        for (Fat f : list)
            pool.checkIn(f);
        for (Fat f : list)
            pool.checkIn(f);
        exec.shutdown();
    }
}
class CheckOutTask<T> implements Runnable{
    private static int count = 0;
    private final int id = count++;
    private Pool<T> pool;

    public CheckOutTask(Pool<T> pool) {
        this.pool = pool;
    }
    @Override
    public String toString() {
        return "CheckOutTask{" +
                "id=" + id +
                '}';
    }
    @Override
    public void run() {
        try {
            T item = pool.checkOut();
            System.out.println(this + " checked out " + item);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + " checking in " + item);
            pool.checkIn(item);
        } catch (InterruptedException e) {
        }

    }
}