package concurrency.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReaderWriterList<T> {
    private ArrayList<T> lockedList;
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public ReaderWriterList(int size,T initValue){
        lockedList = new ArrayList<>(Collections.nCopies(size, initValue));
    }
    public T set(int index,T element){
        Lock wLock = lock.writeLock();
        wLock.lock();
        try{
            return lockedList.set(index, element);
        }finally {
            wLock.unlock();
        }
    }

    public T get(int index){
        Lock rLock = lock.readLock();
        rLock.lock();
        try {
            return lockedList.get(index);
        }finally {
            rLock.unlock();
        }
    }

    public static void main(String[] args) {
        new ReaderWriterListTest(1, 30);
    }
}

class ReaderWriterListTest {
    ExecutorService exec = Executors.newCachedThreadPool();
    private final static int SIZE = 100;
    private static Random rand = new Random(47);
    private ReaderWriterList<Integer> list = new ReaderWriterList<>(SIZE, 0);
    private class Writer implements Runnable{
        @Override
        public void run() {

            try {
                for (int i =0;i<20;i++){
                    list.set(i, rand.nextInt());
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("writer is finished , shutdown!");
            exec.shutdownNow();
        }
    }
    private class Reader implements Runnable{
        @Override
        public void run() {
            try{
                while (!Thread.interrupted()){
                    for (int i=0;i<SIZE;i++){
                        System.out.println(i+ ":"+list.get(i));
                        TimeUnit.MILLISECONDS.sleep(1);
                    }
                }
            }catch (InterruptedException e){
                System.out.println("reader:" + Thread.currentThread().getName() + " is interrupted ");
            }
        }
    }

    public ReaderWriterListTest(int writers,int readers) {
        for (int i=0;i<readers;i++)
            exec.execute(new Reader());
        for (int i=0;i<writers;i++)
            exec.execute(new Writer());
    }
}