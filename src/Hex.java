import java.io.BufferedReader;
import java.io.FileReader;

public class Hex {
    public static String format(byte[] data){
        int n = 0;
        StringBuilder sb = new StringBuilder();
        for (byte b : data){
            if (n % 16 == 0){
                sb.append(String.format("%05X:",n));
            }
            sb.append(String.format("%02X:", n));
            n++;
            if (n % 16 == 0){
                sb.append("\n");
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\IdeaProjects\\prancticeByThinkingInJava\\out\\production\\prancticeByThinkingInJava\\Hex.class"));
        StringBuilder sb = new StringBuilder();
        while (bufferedReader.readLine() !=null ){
            sb.append(bufferedReader.readLine());
        }


                System.out.println(format(sb.toString().getBytes()));
    }
}
