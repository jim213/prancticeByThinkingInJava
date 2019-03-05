package testFormThinkingInJava.concurrent.deadLock;

public class Chopstick {
    private Boolean taken = false;
    public synchronized void take() throws InterruptedException {
        while (taken)
            wait();
        taken = true;
    }
    public synchronized void drop() {
        taken = false;
        notifyAll();
    }
}
