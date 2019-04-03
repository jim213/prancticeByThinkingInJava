package concurrency;

public class OutThis {
    private String out = "outttttttttttt";
    void f() {
        System.out.println("OutThis f()");
    }
    public class Inner{
        private String s = "sssss";
        public OutThis out(){
            return OutThis.this;
        }
        public String outt(){
            return out;
        }
    }
    public Inner inner(){
        return new Inner();
    }
    public String getInnerS(){
        return new Inner().s;
    }

    public static void main(String[] args) {
        OutThis out = new OutThis();
        OutThis.Inner inner = out.new Inner();
        inner.out().f();
        System.out.println(out.getInnerS());
        System.out.println(inner.outt());
    }
}
