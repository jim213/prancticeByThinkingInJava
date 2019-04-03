package concurrency.concurrent;

public class EventGenerator extends IntGenerator {
    private int currentEventValue = 0;
    @Override
//    public synchronized int next() {
    public  int next() {
        ++currentEventValue;
        Thread.yield();
        ++currentEventValue;
//        System.out.println("ok  " + currentEventValue);
        return currentEventValue;
    }

    public static void main(String[] args) {
        EventChecker.test(new EventGenerator(),2);
    }
}
