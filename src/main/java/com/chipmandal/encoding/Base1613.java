package com.chipmandal.encoding;

import java.util.Arrays;

/**
 *  This class is the main engine for encoding and decoding.
 *
 *  @see com.chipmandal.encoding.Encoding1613 for details
 *
 *  More info  at <a href="https://github.com/ChipMandal/baseencoding/wiki">here</a>
 *
 */
class Base1613 implements BaseEncoding{

    private final char[] alphabet;
    private final byte[] reverse;
    private final int num;
    private final Encoding1613 encoding1613 = new Encoding1613();

    /**
     * If you want to use a different alphabet.
     * An alphabet is "num" distinct bytes each in the range 33 to 126
     * each 13 bits is represented by 2 characters.
     *
     * "num" is a number >= 91 and <= 94
     * we need atleast 91 characters so that 2 of them would cover 2^13 ( 91^2 > 2^13 )
     * We have 94 printable characters.
     *
     * @param useAlphabet the alphabet to use
     */
    public Base1613(int num, char[] useAlphabet) {
        this.num = num;

        if ( useAlphabet.length == num ) {
            boolean[] chekbytes = new boolean[127];
            for (char anUseAlphabet : useAlphabet) {
                if (anUseAlphabet > 32 && anUseAlphabet < 127 && !chekbytes[anUseAlphabet]) {
                    chekbytes[anUseAlphabet] = true;
                } else {
                    throw new IllegalArgumentException("Invalid or duplicate byte in alphabet " + String.valueOf(anUseAlphabet));
                }
            }
        } else {
            throw new IllegalArgumentException("Size of alphabet has to be " + String.valueOf(num));
        }

        alphabet = Arrays.copyOf(useAlphabet, num);
        reverse = new byte[127];
        for ( int i = 0; i < alphabet.length; i++) {
            reverse[alphabet[i]] = (byte) i;
        }
    }

    /**
     *
     * This encodes each 13-bit of the input into 2 bytes of output
     *
     * Input : Bytes to encode
     * @return encoded bytes
     *
     */
    @Override
    public byte[] encode(byte[] input) {
        return encoding1613.encode(input);
    }

    /**
     Same as {@link #encode(byte[])}, but encodes into a String specified by the alphabet
     */
    @Override
    public String encodeToString(byte[] input) {
        return bytesToString(encode(input));
    }

    @Override
    /**
     *
     * This decodes the bytes which were previously encoded using {@link #encode(byte[])}
     * to the original form
     *
     * Input : Base1613 encoded bytes
     * @return decoded original bytes
     *
     */
    public byte[] decode(byte[] input) {
        return encoding1613.decode(input);
    }

    private String bytesToString(byte[] encode) {
        StringBuilder builder = new StringBuilder();

        for ( int i = 0; i + 1 < encode.length; i +=2 ) {
            int val = encode[i] << 8 | (encode[i+1] & 0xFF) ;
            int first = val  % num;
            int second = val / num;
            builder.append(alphabet[second]);
            builder.append(alphabet[first]);
        }
        if ( encode.length  %2 ==1 ) {
            //encode the last byte
            builder.append(alphabet[encode[encode.length-1]]);
        }

        return builder.toString();
    }


    @Override
    /**
     *
     * @param input Input string encoding in the right format
     * @return bytes corresponding to the input string
     */
    public byte[] decodeString(String input) {
        char[] array = input.toCharArray();

        byte[] output = new byte[array.length];
        for ( int i = 0; i + 1 < array.length; i+= 2 ) {
            if ( array[i] < 33 || array[i] > 126 || array[i+1] < 33 || array[i+1] > 126 ) {
                throw new IllegalArgumentException("Illegal character");
            }
            int val = reverse[array[i]] * num + reverse[array[i+1]];
            output[i+1] = (byte) (val & 0xFF);
            output[i] = (byte) (val >>> 8);
        }
        if ( array.length %2 == 1 ) {
            output[array.length-1] = reverse[array[array.length-1]];
        }
        return decode(output);
    }
}
