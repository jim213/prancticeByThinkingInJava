package annotations;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UseCaseTracker {
    public static void trackeUseCases(List<Integer> useCases, Class<?> cl){

        Method[] methods = cl.getMethods();

        for (Method method : methods){
            UseCase annotation = method.getAnnotation(UseCase.class);
            if (annotation != null){
                System.out.println(annotation.id() + annotation.description());
                useCases.remove(new Integer(annotation.id()));
            }
        }
        for (Integer i : useCases){
            System.out.println("Warring miss use Case-" + i);
        }
    }

    public static void main(String[] args) {
        List<Integer> useCases = new ArrayList<>();
        Collections.addAll(useCases, 47, 48, 49, 50);
        trackeUseCases(useCases, PasswordUtils.class);
    }

}
