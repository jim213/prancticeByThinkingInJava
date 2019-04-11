import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 扫地机器人不重复走法
 */
public class SweepingRobot {
    //机器人只会前后左右移动，假定初始位置为{0，0} 移动只能从objects中的一种
    private static int[][] objects = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    private static int count = 0;
    private static final int N = 12;

    public static void main(String[] args) {
        List<int[]> steps = new ArrayList<>(N);
        //设置初始位置
        steps.add(new int[]{0, 0});
        count = moveNext(1, steps);
        System.out.println(count);
    }

    /**
     * 移动
     *
     * @param nextStep
     * @param steps
     * @return
     */
    private static int moveNext(int nextStep, List<int[]> steps) {
        //走完 N 步，返回 1，增加一次走法
        if (steps.size() == N + 1) {
//            System.out.println(JSONObject.toJSONString(steps));
//            show(steps);
            return 1;
        }
        int cnt = 0;
        int i;
        for (int j = 0; j < objects.length; j++) {
            i = nextStep;
            //深度优先 走完一次 清空 nextStep 的后几位
            for (int k = steps.size(); k > nextStep; k--) {
                steps.remove(k - 1);
            }
            //下一步移动的坐标
            int[] next = {steps.get(i - 1)[0] + objects[j][0], steps.get(i - 1)[1] + objects[j][1]};
            //如果未移动到以前坐标 则继续走下去
            if (!contains(steps, next)) {
                steps.add(next);
                cnt += moveNext(++i, steps);
            }
        }
        return cnt;
    }

    /**
     * 判断是否包含
     *
     * @param steps
     * @param next
     * @return
     */
    private static boolean contains(List<int[]> steps, int[] next) {
        boolean result = false;
        if (steps == null)
            return result;
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i)[0] == next[0] && steps.get(i)[1] == next[1])
                return true;
        }
        return result;
    }

    /**
     * 路径打印
     *
     * @param steps
     */
    private static void show(List<int[]> steps) {
        int min_y = 0;//最小纵坐标
        int max_y = 0;//最大纵坐标
        int min_x = 0;//最小横坐标
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i)[1] < min_y)
                min_y = steps.get(i)[1];
            if (steps.get(i)[1] > max_y)
                max_y = steps.get(i)[1];
            if (steps.get(i)[0] < min_x)
                min_x = steps.get(i)[0];
        }
        int y = max_y - min_y + 1;//纵坐标（路径多少行）
        ArrayList<Node>[] arrayLists = new ArrayList[y];
        for (int i = 0; i < steps.size(); i++) {
            int y_v = steps.get(i)[1];//纵坐标
            int x_v = steps.get(i)[0] - min_x + 1;//横坐标
            Node node = new Node(i, x_v);
            ArrayList<Node> arrayNode = arrayLists[y_v - min_y];
            if (arrayNode == null)
                arrayNode = new ArrayList<>();
            arrayNode.add(node);
            arrayNode.sort(new SortByX());
            arrayLists[y_v - min_y] = arrayNode;
        }
        printToConsole(arrayLists);
    }

    /**
     * 打印到console
     *
     * @param arrayLists
     */
    private static void printToConsole(ArrayList<Node>[] arrayLists) {
        int length = String.valueOf(N).length() + 1;//取N的长度+1 方便观察和对齐
        String blank = "";
        for (int i = 0; i < length; i++) {
            blank += " ";
        }
        for (ArrayList<Node> nodes : arrayLists) {
            for (int i = 0; i < nodes.size(); i++) {
                int count = i == 0 ? nodes.get(i).getX()
                        : nodes.get(i).getX() - nodes.get(i - 1).getX();
                for (int j = 1; j < count; j++) {
                    System.out.print(blank);
                }
                String v = String.valueOf(nodes.get(i).getV());
                int v_l = v.length();
                if (v_l < length)
                    for (int j = 0; j < length - v_l; j++) {
                        v = " " + v;//位数不够，前补空格
                    }
                System.out.print(v);
            }
            System.out.println("");
        }
        String cut_of = "";
        for (int i = 0; i <= N; i++) {
            cut_of += "***";
        }
        System.out.println(cut_of);//分割线
    }
}

class Node {
    private int v;// 值
    private int x;// 横坐标

    public Node(int v, int x) {
        this.v = v;
        this.x = x;
    }

    public Node(int v) {
        this.v = v;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}

class SortByX implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        return ((Node) o1).getX() - ((Node) o2).getX();
    }
}

