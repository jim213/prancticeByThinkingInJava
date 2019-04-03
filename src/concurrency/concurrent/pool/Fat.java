package concurrency.concurrent.pool;

public class Fat {
    private volatile double d;
    private static int count = 0;
    private final int id = count ++;
    public Fat(){
        for (int i=1;i<10000;i++){
            d +=( Math.PI + Math.E) / (double)i;
        }
    }
    public void operation(){
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Fat id: " + id;
    }

    public static void main(String[] args) {
        for (int i=0;i<5;i++)
            System.out.println(new Fat());
    }
}
