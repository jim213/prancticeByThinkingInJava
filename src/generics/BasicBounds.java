package generics;

import java.awt.*;

public class BasicBounds {
    public static void main(String[] args) {
        Solid<Bound> solid = new Solid<>(new Bound());
        System.out.println(solid.color());
        System.out.println(solid.getX());
        System.out.println(solid.weight());
    }
}

interface HasColor {
    Color getColor();
}
class Colored<T extends HasColor>{
    T item;
    Colored(T item){
        this.item = item;
    }
    Color color(){
        return item.getColor();
    }
}
class Dimension{
    public int x,y,z;
}
class ColoredDimension<T extends Dimension & HasColor>{
    T item;
    public ColoredDimension(T item) {
        this.item = item;
    }
    T getItem(){
        return item;}
    Color color(){
        return item.getColor();
    }
    int getX() {
        return item.x;
    }
    int getY(){
        return item.y;
    }
    int getZ(){
        return item.z;
    }
}
interface Weight{
    int weignt();
}
class Solid<T extends Dimension & HasColor & Weight>{
    T item;
    public Solid(T item) {
        this.item = item;
    }
    T getItem(){
        return item;}
    Color color(){
        return item.getColor();
    }
    int getX() {
        return item.x;
    }
    int getY(){
        return item.y;
    }
    int getZ(){
        return item.z;
    }
    int weight(){
        return item.weignt();
    }
}
class Bound extends Dimension implements HasColor , Weight{
    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public int weignt() {
        return 0;
    }
}