package generics;

import java.util.Iterator;

public class IterableFibonacci extends Fibonacci implements Iterable<Integer> {
    private int n;
    public IterableFibonacci(int  n){
        this.n = n;
    }
    @Override
    public Iterator<Integer> iterator() {

        return new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                return n>0;
            }

            @Override
            public Integer next() {
                n--;
                return IterableFibonacci.super.next();
            }
        };
    }

    public static void main(String[] args) {
        IterableFibonacci gen = new IterableFibonacci(18);
        for (int i:gen){
            System.out.println(i);
        }
    }
}
