package enumerated;

import generics.Generator;

import java.util.Random;

enum CartoonCharacter implements Generator<CartoonCharacter> {
    SLAPPY,SPANKY,PUNCHY,SILLY,BOUNCY,NUTTY,BOB;

    private Random rand = new Random();
    @Override
    public CartoonCharacter next() {
        return values()[rand.nextInt(values().length)];
    }
}
public class EnumImplementation {
    public static <T> void printNext(Generator<T> gt){
        System.out.print(gt.next() + " , ");
    }

    public static void main(String[] args) {
        CartoonCharacter bob = CartoonCharacter.BOB;
        for (int i=0;i<10;i++){
            printNext(bob);
        }
    }

}