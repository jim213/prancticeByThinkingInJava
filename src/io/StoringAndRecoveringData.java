package io;

import java.io.*;

public class StoringAndRecoveringData {
    public static void main(String[] args) throws IOException {
        DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("data.txt")));
        out.writeUTF("fsafafsda");
        out.writeDouble(2.3443414);
        out.flush();
        out.close();
        DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream("data.txt")));
        System.out.println(in.readUTF());
        System.out.println(in.readDouble());
        in.close();
    }
}
