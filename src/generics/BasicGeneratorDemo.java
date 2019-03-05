package generics;

public class BasicGeneratorDemo {
    public static void main(String[] args) {
        BasicGenerator<CountObject> countObjectBasicGenerator = BasicGenerator.create(CountObject.class);
        for (int i =0;i<10;i++)
            System.out.println(countObjectBasicGenerator.next());
    }
}
