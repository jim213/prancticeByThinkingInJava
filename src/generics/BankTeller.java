package generics;

public class BankTeller {

    public static void serve(Teller t,Customer c) {
        System.out.println(t + "serve" + c);
    }

    public static void main(String[] args) {

    }

}

class Customer {
    private static long counter = 1;
    private final long id = counter ++;
    private Customer(){}

    @Override
    public String toString() {
        return "Customer:" + id;
    }

    public static Generator<Customer> generator(){
        return new Generator<Customer>() {
            @Override
            public Customer next() {
                return new Customer();
            }
        };
    }
}

class Teller {
    private static long counter = 1;
    private final long id = counter ++;
    private Teller(){}

    @Override
    public String toString() {
        return "Teller:" + id;
    }

    public static Generator<Teller> generator(){
        return new Generator<Teller>() {
            @Override
            public Teller next() {
                return new Teller();
            }
        };
    }
}