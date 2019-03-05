package testFormThinkingInJava.concurrent;

public class MultLock {
    public synchronized void f1(int count){
        if (count-->0){
            System.out.println("f1() call f2() with count " + count);
            f2(count);
        }else {
            System.out.println("-------------111111----------");
        }
    }
    public synchronized void f2(int count){
        if (count -- > 0){
            System.out.println("f2() call f1() with count " + count);
            f1(count);
        }else {
            System.out.println("--------2222---------------");
        }
    }

    public static void main(String[] args) {
        final MultLock multLock = new MultLock();
        new Thread(() -> multLock.f1(10)).start();
        new Thread(() -> multLock.f2(10)).start();
    }
}
