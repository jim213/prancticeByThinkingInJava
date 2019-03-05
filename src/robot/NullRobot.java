package robot;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

public class NullRobot {
    public static Robot newNullRobot(Class<? extends Robot> type){
        return (Robot) Proxy.newProxyInstance(NullRobot.class.getClassLoader(), new Class[]{Null.class, Robot.class}, new NullRobotProxyHander(type));
    }

    public static void main(String[] args) {
        Robot[] robots = {
                new SnowRemoveRobot("小明"),
                newNullRobot(SnowRemoveRobot.class)
        };
        for (Robot robot : robots){
            Robot.Test.test(robot);
        }
    }
}

class NullRobotProxyHander implements InvocationHandler{

    private String nullName;

    private Robot proxied = new NRobot();
    NullRobotProxyHander(Class<? extends Robot> type){
        nullName = type.getSimpleName() + "NullRobot";
    }

    private class NRobot implements Null,Robot{

        @Override
        public String name() {
            return nullName;
        }

        @Override
        public String mode() {
            return nullName;
        }

        @Override
        public List<Operation> operations() {
            return Collections.emptyList();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(proxied,args);
    }
}