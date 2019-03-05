package generics;

public class Fibonacci implements Generator<Integer> {
    private int count = 0;
    @Override
    public Integer next() {
        return fibo(count++);
    }
    private Integer fibo(Integer count){
        return count < 2 ? 1 : fibo(count - 2) + fibo(count - 1);
    }

    public static void main(String[] args) {
        Fibonacci gen = new Fibonacci();
        for (int i=0;i<18;i++){
            System.out.println(gen.next() + " ");
        }
    }
}
