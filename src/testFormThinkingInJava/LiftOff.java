package testFormThinkingInJava;

public class LiftOff implements Runnable {
    @Override
    public void run() {
        while (countDown -- > 0){
            System.out.println(status());
            Thread.yield();
        }
    }

    protected int countDown = 5;
    private static int taskCount = 0;
    private final int id = taskCount ++;

    public LiftOff(int countDown) {
        countDown = countDown;
    }

    public LiftOff() {
    }

    public String status() {
        return "#" + id + "("+
                (countDown >0 ? countDown : "liftOff")+ "),";
    }
}
