package com.chipmandal.encoding;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public interface BaseEncoding {


    static Base94 base94() {
        return new Base94();
    }

    byte[] encode(byte [] input);
    String encodeString(byte [] input);
    byte[] decode(byte [] input);
    byte[] decodeString(String input);


}
