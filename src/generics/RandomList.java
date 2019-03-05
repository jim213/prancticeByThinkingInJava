package generics;

import java.util.ArrayList;
import java.util.Random;

public class RandomList<T> {
    private ArrayList<T> storage = new ArrayList<>();
    private Random random = new Random();
    public void add(T item){
        storage.add(item);
    }
    public T select(){
        return storage.get(random.nextInt(storage.size()));
    }

    public static void main(String[] args) {
        RandomList<Integer> ri = new RandomList<>();
        for (int i=0;i<10;i++){
            ri.add(i);
        }
        for (int i=0;i<10;i++){
            System.out.println(ri.select());
        }

    }
}
