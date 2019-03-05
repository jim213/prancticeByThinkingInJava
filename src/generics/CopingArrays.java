package generics;

import java.util.*;

public class CopingArrays {
    private  String x = "xxx";

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "CopingArrays{" +
                "x='" + x + '\'' +
                '}';
    }

    public static void main(String[] args) {
//        int[] i = new int[7];
//        int[] j = new int[11];
//        Arrays.fill(i,3);
//        Arrays.fill(j,4);
//        System.out.println(Arrays.toString(i));
//        System.out.println(Arrays.toString(j));
//        System.arraycopy(i,0,j,0,7);
//        System.out.println(Arrays.toString(j));
//        f();

        CopingArrays[] copingArrays = new CopingArrays[3];
        CopingArrays copingArrays1 = new CopingArrays();
        Arrays.fill(copingArrays,copingArrays1);
        System.out.println(Arrays.toString(copingArrays));
        copingArrays1.setX("asd");
        System.out.println(Arrays.toString(copingArrays));
        List<String> stringList = Collections.nCopies(3, "xxx");
        System.out.println(stringList);

    }
    static void f(){
        HashSet hashSet = new HashSet();
        hashSet.add(new CopingArrays());
        SortedSet sortedSet =  new TreeSet();

    }
}
