package com.webapp.util;

public class CodeAid {

    public static <T> T todo() {
        throw new RuntimeException("This needs to be done yet.");
    }

    public static String print(Object arg) {
        String str = arg.toString();
        System.out.println(str);
        return str;
    }

    public static String print(String msg, Object... args) {
        String str = String.format(msg, args);
        System.out.println(str);
        return str;
    }

}
