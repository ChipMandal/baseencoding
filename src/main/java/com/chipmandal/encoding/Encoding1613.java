package com.chipmandal.encoding;

/**
 * This class encodes 13 bits to 16 bits
 *
 * Specification :
 * Each 13 bits in the input is stored in 16 bits (2 bytes) in the output.
 *
 * In general, for each "complete" 13 bit sequence of input
 * The lower 13 bits of the 2 byte combo
 *  i.e. the lower 5 bits of the first byte and the 8 bytes of the next byte contains the 13 bits
 *      the first 3 bits of the first byte is 0
 *
 * For last sequence of bits of less than 13 bits,
 *  1. There is 6 bits or less,
 *      output 1 byte with the bottom n bits with the last n bits of the input
 *      the upper part of the byte is undefined
 *  2. There are more than 6 bits left ( 7-12 )
 *      output 2 bytes with the remaining input bits at the less significant bits of the output
 *      The other bits in the output are undefined
 *
 *
 */
public class Encoding1613 {

    public byte[] encode(byte[] input) {
        if ( input == null || input.length == 0 ) {
            return new byte[0];
        }
        int outputBytes = ((input.length * 8) / 13)*2;
        int mod = (input.length * 8) % 13;
        int rem =  (mod <=6) ? 1 : 2;

        byte[] output = new byte[outputBytes+rem];

        int readPos = 7;
        int readByte = 0;
        int byteCount =0;
        for ( ; byteCount < outputBytes ; byteCount+=2) {
            //Read the next five bytes and put it in [4-0]
            if ( readPos >= 4) {
                //We have more than 4 in the current readbyte
                output[byteCount] = (byte) (( (input[readByte] & 0xFF) >>> (readPos-4)) & 0x1F);
                readPos = readPos - 5;
                if ( readPos < 0 ) {
                    readPos = 7;
                    readByte++;
                }
            } else {
                //We have less then 4, so have to take partially from the next byte
                output[byteCount] = (byte) (((input[readByte] << (4-readPos)) & 0x1F) |
                        ( (input[readByte+1] & 0xFF) >>> (4+readPos)));

                readByte++;
                readPos = 3+readPos;
            }
            //Read the next 8 bytes
            if (readPos == 7 ) {
                output[byteCount+1] = input[readByte];
            } else {
                output[byteCount+1] = (byte) ((input[readByte] << (7-readPos)) |
                        ( (input[readByte+1] & 0xFF) >>> (readPos+1)));
            }
            readByte++;
        }

        if (mod == 0 ) {
            output[outputBytes] = 0;
        } else if ( mod <= 6) {
            output[outputBytes] = (byte) (input[input.length-1] & 0x3F);
        } else if (mod <= 8){
            output[outputBytes] =  0;
            output[outputBytes+1] = input[input.length-1];
        } else {
            output[outputBytes] = (byte) (input[input.length-2] & 0x0F);
            output[outputBytes+1] = input[input.length-1];
        }
        return output;
    }

    public byte[] decode(byte[] input) {
        if ( input == null || input.length == 0 ) {
            return new byte[0];
        }
        int extraDigits = input.length % 2 == 0 ? 2 : 1;
        int evenLength = input.length - extraDigits ;
        int numbits = (evenLength/2) * 13;

        int outputLength = (numbits / 8 + (numbits%8 == 0 ? 0 : 1));
        if ( extraDigits == 2 && ( numbits % 8 >= 4 || numbits %8 == 0) ) {
            outputLength++;
        }
        byte[] output = new byte[outputLength];
        int writePos = 7;
        int writeByte = 0;
        for (int i =0; i < evenLength; i+= 2) {
            //Write the first byte
            if ( writePos >= 4) {
                output[writeByte] |= (input[i] << (writePos - 4));
                writePos = writePos - 5;
                if ( writePos < 0) {
                    writeByte++;
                    writePos = 7;
                }
            } else {
                output[writeByte] |= ((input[i] & 0xFF) >>> (4-writePos));
                output[writeByte+1] |= (input[i]  << (4+writePos));
                writeByte++;
                writePos = 3+writePos;
            }

            //2nd byte
            output[writeByte] |= ((input[i+1] & 0xFF) >>> (7-writePos));
            if ( writeByte + 1 < outputLength ) {
                //This condition is only needed in the special case where the bytes
                // are exactly divisible by 8. In this case we would not write the last byte.
                // TODO for efficency code this as a different function.
                output[writeByte + 1] |= (input[i + 1] << (writePos + 1));
            }

            writeByte++;
        }


        if ( writeByte == outputLength - 1) {
            output[writeByte] |= (input[input.length-1] & ( 0xFF >>> (7-writePos)));
        } else if(writeByte == outputLength - 2) {
            output[writeByte] |= (input[input.length-2] & ( 0xFF >>> (7-writePos)));
            output[writeByte+1] = input[input.length -1];
        }

        return output;
    }

}
