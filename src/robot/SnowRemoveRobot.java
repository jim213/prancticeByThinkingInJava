package robot;

import java.util.Arrays;
import java.util.List;

public class SnowRemoveRobot implements Robot {

    private String name;

    public SnowRemoveRobot(String name) {
        this.name = name;
    }


    @Override
    public String name() {
        return name;
    }

    @Override
    public String mode() {
        return "第一代扫雪机器人";
    }

    @Override
    public List<Operation> operations() {
        return Arrays.asList(
                new Operation() {
                    @Override
                    public String description() {
                        return name + "可以扫雪";
                    }

                    @Override
                    public void command() {
                        System.out.println(name + "正在扫雪......");
                    }
                },
                new Operation() {
                    @Override
                    public String description() {
                        return name + "可以除冰";
                    }

                    @Override
                    public void command() {
                        System.out.println(name + "正在除冰......");
                    }
                },
                new Operation() {
                    @Override
                    public String description() {
                        return name + "可以清理屋顶";
                    }

                    @Override
                    public void command() {
                        System.out.println(name + "正在清理屋顶......");
                    }
                });
    }

    public static void main(String[] args) {
        Robot.Test.test(new SnowRemoveRobot("小明"));
    }
}
