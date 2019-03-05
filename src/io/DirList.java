package io;

import java.io.*;
import java.util.Arrays;
import java.util.regex.Pattern;

public class DirList {
    public static FilenameFilter filter(final String regex){
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            @Override
            public boolean accept(File dir, String name) {
                return pattern.matcher(name).matches();
            }
        };
    }

    public static void main(String[] args) {
        File file = new File(".");
        String[] list;
        list = file.list();

        Arrays.sort(list,String.CASE_INSENSITIVE_ORDER);
        for (String item : list){
            System.out.println(item);
        }

    }
}
