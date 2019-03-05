package io;

import java.io.*;

public class BufferedInputFile {
    public static String read(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
        String s ;
        StringBuilder sb = new StringBuilder();
        while ((s=bufferedReader.readLine())!=null){
            sb.append(s + " \n");
        }
        bufferedReader.close();
        return sb.toString();
    }

    public static void main(String[] args) throws IOException{
        System.out.println(read("src/io/BufferedInputFile.java"));
    try{

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(read("src/io/BufferedInputFile.java").getBytes()));
        while (true) {
            System.out.print((char) in.readByte());
        }
    }catch (EOFException e){
        System.err.println("end of in");
        e.printStackTrace();
        }

    }
}
