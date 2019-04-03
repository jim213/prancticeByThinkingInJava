package concurrency;

public class Callbacks {
    public static void main(String[] args) {
        Callee1 callee1 = new Callee1();
        Caller caller1 = new Caller(callee1);
        caller1.go();
        caller1.go();

        /**************************/
        Callee2 callee2 = new Callee2();
        MyIncrement.f(callee2);
        Caller caller2 = new Caller(callee2.getCallbackReference());
        caller2.go();
        caller2.go();
    }
}

interface Incrementable{
    void increment();
}

class Callee1 implements Incrementable {
    private int i =0;
    @Override
    public void increment() {
        i++;
        System.out.println(i);
    }
}

class MyIncrement {
    public void increment(){
        System.out.println("other operation");
    }
    static void f(MyIncrement mi){
        mi.increment();
    }

}

class Callee2 extends MyIncrement {
    private int i = 0;

    @Override
    public void increment() {
        super.increment();
        i++;
        System.out.println(i);
    }

    private class Incoment implements Incrementable {
        @Override
        public void increment() {
            Callee2.this.increment();
        }
    }
    Incrementable getCallbackReference(){
        return new Incoment();
    }
}


class Caller {
    private Incrementable callbackReference;
    Caller(Incrementable cbh){
        callbackReference = cbh;
    }
    void go(){
        callbackReference.increment();
    }
}