package enumerated;

import java.util.Random;

public class RoShamBo1  {
    static final int SIZE = 20;
    static Random rand = new Random();
    public static Item newItem(){
        switch (rand.nextInt(3)){
            default:
            case (0) :
                return new Scissors();
            case (1):
                return new Paper();
            case (2):
                return new Rock();
        }
    }
    public static void match(Item i1,Item i2){
        System.out.println(i1 +" vs " +i2 + " : " +i1.compete(i2));
    }

    public static void main(String[] args) {
        for (int i=0;i<SIZE;i++){
            match(newItem(),newItem());
        }
    }
}
