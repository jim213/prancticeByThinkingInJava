package enumerated;

import java.util.EnumMap;
import java.util.Map;

import static enumerated.AlarmPoints.*;

interface Command{
    void action();
}

public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<AlarmPoints, Command> enumMap = new EnumMap<>(AlarmPoints.class);
        enumMap.put(STAIR1, new Command() {
            @Override
            public void action() {
                System.out.println("Stair1 is actioned!");
            }
        });
        enumMap.put(STAIR2, new Command() {
            @Override
            public void action() {
                System.out.println("Stair2 is actioned!");
            }
        });
        for (Map.Entry<AlarmPoints,Command> map :enumMap.entrySet()){
            System.out.println(map.getKey() + " : " );
            map.getValue().action();
        }
        try {
            enumMap.get(LOBBY).action();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
