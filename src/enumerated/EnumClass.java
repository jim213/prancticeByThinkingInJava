package enumerated;

enum Dog{
    BIG_DOG,YELLOW_DOG,SMALL_DONG
}

public class EnumClass {
    public static void main(String[] args) {
        for (Dog s : Dog.values()){
            System.out.println(s + ":" + s.ordinal());
            System.out.println(s.compareTo(Dog.YELLOW_DOG) );
            System.out.println(s.equals(Dog.YELLOW_DOG ));
            System.out.println(s == Dog.YELLOW_DOG);
            System.out.println(s.name());
            System.out.println("--------------------");
        }
    }
}
