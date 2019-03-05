package generics;

import java.util.ArrayList;
import java.util.Collection;

public class Generators {
    public static <T> Collection<T> fill(Collection<T> collection,Generator<T> generator ,int n){
        for (int i=0;i<n;i++){
            collection.add(generator.next());
        }
        return collection;
    }

    public static void main(String[] args) {
        Collection<Integer> fill = Generators.fill(new ArrayList<>(), new Fibonacci(), 14);
        System.out.println(fill);
    }
}
