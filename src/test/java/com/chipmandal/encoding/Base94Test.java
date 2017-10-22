package com.chipmandal.encoding;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Base94Test {
    static byte[] standardAlphabet =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!@#$%^&*-_=+|\\;:\"'/?.>,<(){}[]".getBytes(StandardCharsets.UTF_8);
    static Base94 base94;
    static Random random;

    @BeforeAll
    public static void init() {
        random = new Random();
        base94 = BaseEncoding.base94();
    }

    @Test
    @RepeatedTest(5000)
    public void randomLengthsRandomEncodeDecode() {

        int length = random.nextInt(5000);
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        String encoded = base94.encodeString(bytes);
        byte[] decoded = base94.decodeString(encoded);
        assertTrue(Arrays.equals(bytes, decoded), "Failed for length " + length);
    }
    @Test
    @DisplayName("Invalid size of alphabet")
    public void testAlphabetCheck() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Base94("abc".toCharArray());
        });
        assertTrue(exception.getMessage().contains("Size of alphabet"), "Size mismatch");

    }

    @Test
    @DisplayName("Duplicates in input alphabet")
    public void duplicatesInInput() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Base94("0123456789ABCDEFGHIJKLMNOPQRSTUVWXXZabcdefghijklmnopqrstuvwxyz~`!@#$%^&*-_=+|\\;:\"'/?.>,<(){}[]".toCharArray());
        });
        assertTrue(exception.getMessage().contains("Invalid or duplicate"), "Invalid or duplicate");

    }

}