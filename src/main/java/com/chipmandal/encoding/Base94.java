package com.chipmandal.encoding;

public class Base94 extends Base1613 implements BaseEncoding{

    private final static char[] standardAlphabet =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!@#$%^&*-_=+|\\;:\"'/?.>,<(){}[]".toCharArray();
    public Base94() {
        this(standardAlphabet);
    }

    /**
     * If you want to use a different alphabet.
     * An alphabet is 94 distinct bytes each in the range 0x21 to 0x126
     * @param useAlphabet
     */
    public Base94(char[] useAlphabet) {
        super(94, useAlphabet);
    }
}
