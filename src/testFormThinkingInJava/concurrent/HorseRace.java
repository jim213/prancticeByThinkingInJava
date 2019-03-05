package testFormThinkingInJava.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class HorseRace {
    static final int FINAISN_LINE = 75;
    private List<Horse> horses = new ArrayList<>();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;
    public HorseRace(int nHorses,int pause){
        barrier = new CyclicBarrier(nHorses, () -> {
            StringBuilder s = new StringBuilder();
            for (int i=0;i<FINAISN_LINE;i++){
                s.append("=");
            }
            System.out.println(s);
            for (Horse horse : horses){
                System.out.println(horse.tracks());
            }
            for (Horse horse:horses){
                if (horse.getStrides() >= FINAISN_LINE){
                    System.out.println(horse + " won!");
                }
                exec.shutdownNow();
                return;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        for (int i=0;i<nHorses;i++){
            Horse horse = new Horse(barrier);
            horses.add(horse);
            exec.execute(horse);
            }
    }
    public static void main(String[] args) {
        int nHorse = 7;
        int pause = 200;
        if (args.length >0){
            int n = new Integer(args[0]);
            nHorse = n >0 ? n : nHorse;
        }
        if (args.length >1){
            int p = new Integer(args[1]);
            pause = p >-1 ? p : pause;
        }
        new HorseRace(nHorse, pause);
    }
}

class Horse implements Runnable{
    private static int count =0;
    private final int id = count++;
    private int strides = 0;
    private static Random rand = new Random(47);
    private static CyclicBarrier barrier;
    public synchronized int getStrides() {
        return strides;
    }
    public Horse(CyclicBarrier barrier){
            this.barrier = barrier;
    }
    public String tracks(){
        StringBuilder s = new StringBuilder();
        for (int i=0;i<getStrides();i++){
            s.append("*");
        }
        s.append(id);
        return s.toString();
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                synchronized (this){
                    strides += rand.nextInt(3);
                }
                barrier.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Horse{" + "id=" + id + '}';
    }
}
