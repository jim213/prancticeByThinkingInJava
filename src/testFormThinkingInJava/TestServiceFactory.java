package testFormThinkingInJava;

import java.util.concurrent.TimeUnit;

public class TestServiceFactory {

    public static void main(String[] args) {
        ServiceFactory factory = new ServiceFactoryimpl();
        factory.create().f1();
    }
}

interface Service {
    void f1();
}
class Serviceimpl implements Service{
    @Override
    public void f1() {
        System.out.println("Serviceimpl service");
    }
}
interface ServiceFactory{
    Service create();
}
class ServiceFactoryimpl implements ServiceFactory{
    @Override
    public Service create() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Serviceimpl();
    }
}