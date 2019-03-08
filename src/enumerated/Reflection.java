package enumerated;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

enum Explore {
    HERE,THERE;
}

public class Reflection {
    public static Set<String> analyze(Class<?> enumClass){
        System.out.println("---------" + enumClass +"-----------");
        System.out.println("Interfaces:");
        for (Type t: enumClass.getGenericInterfaces())
            System.out.println(t);
        System.out.println("base:" + enumClass.getSuperclass());
        System.out.println("Methods:");
        Set<String> methods = new TreeSet<>();
        for (Method method : enumClass.getMethods()){
            methods.add(method.getName());
        }
        System.out.println(methods);
        System.out.println("---------" + enumClass +"-----------");
        return methods;
    }

    public static void main(String[] args) {
        Set<String> exploreMethods = analyze(Explore.class);
        Set<String> enumMethods = analyze(Enum.class);
        System.out.println("exploreMethods containsAll enumMethods:" + exploreMethods.containsAll(enumMethods));
        System.out.println("exploreMethods containsAll enumMethods:");
        exploreMethods.removeAll(enumMethods);
        System.out.println(exploreMethods);
        System.out.println("-----------test getEnumConstants-------------------------");
        Explore[] values = Explore.values();
        Enum e = Explore.HERE;
        for (Enum en : e.getClass().getEnumConstants()){
            System.out.println(en);
        }
        System.out.println("-----------test noEnum getEnumConstants-------------------------");
        Class<Integer> intClass = Integer.class;
        for (Object en : intClass.getEnumConstants()){
            System.out.println(en);
        }
    }
}
