package generics;

public class TupleTest {
    static TwoTuple<String,Integer> f(){
        return new TwoTuple<>("jim", 213);
    }
    static ThreeTuple<String,Integer,Boolean> g(){
        return new ThreeTuple<>("king", 107, true);
    }
    public static void main(String[] args) {
        TwoTuple<String,Integer> twoTuple = f();
        System.out.println(twoTuple);
        System.out.println(g());
    }
}

class TwoTuple<A,B>{
    public final A a;
    public final B b;

    public TwoTuple(A a, B b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public String toString() {
        return "("+a+","+b+")";
    }
}
class ThreeTuple<A,B,C> extends TwoTuple<A,B>{
    public final C c;

    public ThreeTuple(A a,B b,C c) {
        super(a,b);
        this.c = c;
    }

    @Override
    public String toString() {
        return "("+a+","+b+","+c+")";
    }
}
class FourTuple<A,B,C,D> extends ThreeTuple<A,B,C>{
    public final D d;
    public FourTuple(A a, B b, C c, D d) {
        super(a, b, c);
        this.d = d;
    }

    @Override
    public String toString() {
        return "("+a+","+b+","+c+","+d+")";
    }
}