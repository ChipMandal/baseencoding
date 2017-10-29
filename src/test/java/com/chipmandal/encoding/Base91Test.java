package com.chipmandal.encoding;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Base91Test {
    static byte[] standardAlphabet =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!@#$%^&*-_=+|\\;:\"'/?.>,<(){}[]".getBytes(StandardCharsets.UTF_8);
    static Base91 base91;
    static Random random;

    @BeforeAll
    public static void init() {
        random = new Random();
        base91 = new Base91();
    }

    @Test
    @RepeatedTest(5000)
    public void randomLengthsRandomEncodeDecode() {

        int length = random.nextInt(5000);
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        String encoded = base91.encodeToString(bytes);
        byte[] decoded = base91.decodeString(encoded);
        assertTrue(Arrays.equals(bytes, decoded), "Failed for length " + length);
    }


}