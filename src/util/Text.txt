package util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

public class TextFile extends ArrayList<String> {

    public TextFile(String fileName,String splitter) {
        super(Arrays.asList(read(fileName).split(splitter)));
        if (get(0).equals(""))
            remove(0);
    }
    public TextFile(String fileName){
        this(fileName, "\n");
    }

    public static String read(String fileName){
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader in = new BufferedReader(new FileReader(new File(fileName).getAbsoluteFile()));
            String s ;
            try{
                while ((s= in.readLine())!=null)
                    sb.append(s).append("\n");
            }finally {
                in.close();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
    public static void write(String fileName , String text){
        try {
            PrintWriter out = new PrintWriter(new File(fileName).getAbsoluteFile());
            try{
                out.print(text);
            }finally {
                out.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void write(String fileName){
        try {
            PrintWriter out = new PrintWriter(new FileWriter(fileName));
            try {
                for (String item : this){
                    out.println(item);
                }
            }finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String file = read("src/util/TextFile.java");
        write("src/util/Text.txt",file);
        TextFile text = new TextFile("src/util/Text.txt");
        text.write("src/util/text2.txt");

        TreeSet<String> words = new TreeSet<>(new TextFile("src/util/TextFile.java", "\\W+"));
        System.out.println(words.headSet("a"));
    }
}
