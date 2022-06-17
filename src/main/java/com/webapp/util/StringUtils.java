package com.webapp.util;

import java.util.Random;

public class StringUtils {
    public static boolean isEmpty(String string) {
        if (string != null && !string.isEmpty()) {
            return false;
        }
        return true;
    }
    public static String randomString(int size) {
        int range = 'z' - 'a';
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            char letter = (char) ('a' + random.nextInt(range));
            if (random.nextBoolean()) {
                letter = Character.toUpperCase(letter);
            }
            stringBuffer.append(letter);
        }
        return stringBuffer.toString();
    }
}
