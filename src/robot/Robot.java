package robot;

import java.util.List;

public interface Robot {
    String name();
    String mode();
    List<Operation> operations();
    class Test {
        public static void test(Robot r){
            if (r instanceof Null)
                System.out.println("NullRobot!");
            System.out.println("robot'name : " + r.name());
            System.out.println("robot'mode : " + r.mode());
            for (Operation operation : r.operations()){
                System.out.println(operation.description());
                operation.command();
            }
        }
    }
}
