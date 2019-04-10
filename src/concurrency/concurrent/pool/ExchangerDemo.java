package concurrency.concurrent.pool;

import generics.BasicGenerator;
import generics.Generator;

import java.util.List;
import java.util.concurrent.*;

public class ExchangerDemo {

    static int size = 10;
    static int delay = 5;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> xc = new Exchanger<>();
        List<Fat> producerList = new CopyOnWriteArrayList<>();
        List<Fat> consumerList = new CopyOnWriteArrayList<>();
        exec.execute(new ExchangerProducer<>(BasicGenerator.create(Fat.class),xc,producerList));
        exec.execute(new ExchangerConsumer<>(xc,consumerList));
        TimeUnit.SECONDS.sleep(delay);
        exec.shutdownNow();
    }
}

class ExchangerProducer<T> implements Runnable{

    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;

    public ExchangerProducer(Generator<T> generator, Exchanger<List<T>> exchanger, List<T> holder) {
        this.generator = generator;
        this.exchanger = exchanger;
        this.holder = holder;
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()){
                for (int i=0;i<ExchangerDemo.size;i++){
                    holder.add(generator.next());
                }
                holder = exchanger.exchange(holder);
            }
        } catch (InterruptedException e) {
            System.err.println("ExchangerProducer isInterrupted");
        }


    }
}

class ExchangerConsumer<T> implements Runnable{

    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;

    public ExchangerConsumer(Exchanger<List<T>> exchanger, List<T> holder) {
        this.exchanger = exchanger;
        this.holder = holder;
    }

    @Override
    public void run() {

        try {
            while (!Thread.interrupted()){
                holder = exchanger.exchange(holder);
                for (T x : holder){
                    value = x;
                    holder.remove(x); //is OK for CopyOnWriteArrayList
                }
            }
        } catch (InterruptedException e) {
            System.err.println("ExchangerConsumer isInterrupted");
        }
        System.out.println("final value = " + value);
    }
}