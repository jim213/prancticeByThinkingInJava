package enumerated;

import java.util.List;

enum Light {
    RED,YELLOW,GREEN;
}

public class TrafficLight {
    private Light sig = Light.RED;
    public void change(){
        switch (sig){
            case RED:
                sig = Light.GREEN;
                break;
            case GREEN:
                sig = Light.YELLOW;
                break;
            case YELLOW:
                sig = Light.RED;
        }
    }

    @Override
    public String toString() {
        return "TrafficLight{" +
                "sig=" + sig +
                '}';
    }

    public static void main(String[] args) {
        TrafficLight light = new TrafficLight();
        for (int i = 0;i<7;i++){
            System.out.println(light);
            light.change();
        }
    }

}
