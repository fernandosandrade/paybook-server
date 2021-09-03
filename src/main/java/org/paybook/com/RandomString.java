package org.paybook.com;

import java.security.SecureRandom;
import java.util.Locale;

public class RandomString {

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

    private static final String DIGITS = "0123456789";

    private static final String ALPHANUM = UPPER + LOWER + DIGITS;

    private static final SecureRandom RANDOM = new SecureRandom();

    /** randomid de 20 posicoes */
    public static String next() {
        return next(20);
    }

    public static String next(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; ++i) {
            sb.append(ALPHANUM.charAt(RANDOM.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

}
