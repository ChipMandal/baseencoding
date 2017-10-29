package com.chipmandal.encoding;

/**
 * see  {@link Base1613}
 * Here we specify the alphabet of 94 characters we will use for the encodings
 */
@SuppressWarnings("SpellCheckingInspection")
public class Base94 extends Base1613 implements BaseEncoding{

    @SuppressWarnings("SpellCheckingInspection")
    private final static char[] standardAlphabet =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~`!@#$%^&*-_=+|\\;:\"'/?.>,<(){}[]".toCharArray();
    public Base94() {
        this(standardAlphabet);
    }

    /**
     * If you want to use a different alphabet.
     * An alphabet is 94 distinct bytes each in the range 0x21 to 0x126
     * @param useAlphabet alphabet to use
     */
    public Base94(char[] useAlphabet) {
        super(94, useAlphabet);
    }
}
