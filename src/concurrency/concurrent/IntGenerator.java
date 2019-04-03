package concurrency.concurrent;

public abstract class IntGenerator {
    private volatile boolean canceld = false;
    public abstract int next();
    public void cancle(){
        canceld = true;
    }

    public boolean isCanceld() {
        return canceld;
    }
}
