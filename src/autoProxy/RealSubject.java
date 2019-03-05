package autoProxy;

public class RealSubject implements Subject {
    @Override
    public void rent() {
        System.out.println("I want rent my house!");
    }

    @Override
    public void hello(String s) {
        System.out.println("Hello: " + s);
    }
}
