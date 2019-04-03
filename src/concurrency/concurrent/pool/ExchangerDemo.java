package concurrency.concurrent.pool;

public class ExchangerDemo {

    public static void main(String[] args) {
//        String filePath = "10.4.5.129:22122/group1/M00/00/01/CgQFgVv8sXaAOSVrAABUAL3ntR0760.doc?token=716d0347c12de6875d5cc7b4b61d8563&ts=1543287153";
//        System.out.println(filePath.substring(filePath.indexOf("/") + 1, filePath.indexOf("?")));
        String s = "是非得失.jpg";
        s = s.substring(s.lastIndexOf("\\")+1);
        System.out.println(s);

    }
}
