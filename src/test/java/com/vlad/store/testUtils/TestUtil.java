package com.vlad.store.testUtils;

import java.math.BigDecimal;
import java.util.Random;

public class TestUtil {
    public static String generateRandomName() {
        char[] chararray = new char[5];
        Random random = new Random();
        for (int i = 0; i < chararray.length; i++) {
            // 0-9a-zA-Z (plus special symbols) range is {48; 122}
            char next = (char) (47 + (char)(Math.random() * (122 - 47) + 1));
            // special symbols ranges are: :;<=>?@ {58; 64} and [\]^_` {91; 96}
            if(next >= 58 && next <= 64 || next >= 91 && next <= 96) {
                --i;
                continue;
            }
            chararray[i] = next;
        }
        return String.valueOf(chararray);
    }

    public static BigDecimal generateRandomPrice() {
        return new BigDecimal(Math.random() * 1000);
    }

    public static int generateRandomSize() {
        return 30 + (int) (Math.random() * 40);
    }
}
