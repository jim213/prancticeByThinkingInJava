package concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CriticalSection {
    public static void main(String[] args) {
        PairManager pman1 = new PairManager1(),
        pman2 = new PairManager2();
        testApp(pman1,pman2);
    }

    public static void testApp(PairManager pairManager1,PairManager pairManager2){
        ExecutorService exec = Executors.newCachedThreadPool();
        PairManipulator pm1 = new PairManipulator(pairManager1),
                pm2 = new PairManipulator(pairManager2);
        PairChecker pcheck1 = new PairChecker(pairManager1),
                pcheck2 = new PairChecker(pairManager2);
        exec.execute(pm1);
        exec.execute(pm2);
        exec.execute(pcheck1);
        exec.execute(pcheck2);
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pm1:" + pm1 + "\npm2:"+pm2);
        System.exit(0);

    }
}

class PairManipulator implements Runnable{

    private PairManager pm;

    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while (true){
            pm.increment();
        }
    }

    @Override
    public String toString() {
        return "Pair:" + pm.getPair() + "checkerCount:" + pm.checkCounter.get();
    }
}

class PairChecker implements Runnable{

    private PairManager pm;

    public PairChecker(PairManager pm) {
        this.pm = pm;
    }
    @Override
    public void run() {
        while (true){
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}

abstract class  PairManager {
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());
    public synchronized Pair getPair(){
        return new Pair(p.getX(), p.getY());
    }

    protected void store(Pair p){
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void increment();
}

class PairManager1 extends PairManager {

    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incermentY();
        store(p);
    }
}

class PairManager2 extends PairManager {

    @Override
    public void increment() {
        Pair temp;
        synchronized(this) {
            p.incrementX();
            p.incermentY();
            temp =  getPair();
        }
        store(temp);
    }
}

class Pair{
    private int x,y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this(0, 0);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void incrementX(){
        x++;
    }
    public void incermentY(){
        y++;
    }

    @Override
    public String toString() {
        return "x:" + x + "y:" + y;
    }
    public class PairValuesNotEqualException extends RuntimeException{
        public PairValuesNotEqualException() {
            super("Pair values not equal : " + Pair.this);
        }
    }
    public void checkState(){
        if (x!=y){
            throw new PairValuesNotEqualException();
        }
    }
}