package concurrency;

public class SerialNumberGenerator {
    private static volatile int serialNumber = 1;

    public static int nextSerialNumber(){
        return serialNumber++;
    }
}
