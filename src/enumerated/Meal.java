package enumerated;

import util.Enums;

public enum Meal {
    APPETIZER(Food.Appetizer.class),
    MAINCOURSE(Food.MainCourse.class),
    DESSERT(Food.Dessert.class)
    ;

    private Food[] values;

    Meal(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }

    public interface Food{
        enum Appetizer implements Food{
            SALAD,SOUP,SPRING_ROLLS;
        }
        enum MainCourse implements Food{
            LASANG,RICE,MANTOU;
        }
        enum Dessert implements Food{
            CAKE,ICE_CREAME,FRUIT;
        }
    }

    public Food randomSelection(){
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (int i = 0 ;i<5;i++){
            for (Meal meal: Meal.values()){
                Food food = meal.randomSelection();
                System.out.println(food);
            }
            System.out.println("-------------------------");
        }
    }

}
