package com.chipmandal.encoding;

public interface BaseEncoding {


    static Base94 base94() {
        return new Base94();
    }

    byte[] encode(byte [] input);
    String encodeToString(byte [] input);
    byte[] decode(byte [] input);
    byte[] decodeString(String input);


}
