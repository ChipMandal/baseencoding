package com.chipmandal.encoding;

/**
 * see  {@link Base1613}
 * Here we specify the alphabet of 94 characters we will use for the encodings
 */
public class Base91 extends Base1613 implements BaseEncoding{

    private final static char[] standardAlphabet =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!@#$%^&*-_=+|;:/?.>,<(){}[]".toCharArray();
    public Base91() {
        this(standardAlphabet);
    }

    /**
     * If you want to use a different alphabet.
     * An alphabet is 91 distinct bytes each in the range 0x21 to 0x126
     * @param useAlphabet
     */
    public Base91(char[] useAlphabet) {
        super(91, useAlphabet);
    }
}
