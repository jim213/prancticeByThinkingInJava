package concurrency;

public class MoreBasicThreads {

    public static void main(String[] args) {
        for (int i=0;i<5;i++){
            Thread t = new Thread(new LiftOff(4));
            t.start();
        }
        System.out.println("waiting for liftOff");
    }
}
