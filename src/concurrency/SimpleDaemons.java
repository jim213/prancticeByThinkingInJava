package concurrency;

import java.util.concurrent.TimeUnit;

public class SimpleDaemons implements Runnable {
    @Override
    public void run() {
        while (true){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                System.out.println(Thread.currentThread() + ":" + this);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("---------------------------------------");
            }
        }
    }

    public static void main(String[] args) throws  Exception{
        for (int i=0;i<10;i++){
        Thread deamon = new Thread(new SimpleDaemons());
        deamon.setDaemon(true);
        deamon.start();
        }
        System.out.println("all deamon start!!");
        TimeUnit.MILLISECONDS.sleep(1755);
    }

}
