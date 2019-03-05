public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        StringBuffer sb = new StringBuffer("abcdefghijklmn");
        int index = sb.indexOf("cd");
        System.out.println(index);
        String substring = sb.substring(index - 1, index + 3);
        System.out.println(substring);

    }
}
