package net.alphadev.ntfslib.util;

import java.nio.ByteBuffer;

public class BitStitching {
    public static String convertBytesToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x ", b & 0xff));
        return sb.toString();
    }

    public static String convertByteBufferToHex(final ByteBuffer buffer) {
        final byte[] bytes = new byte[buffer.remaining()];
        buffer.duplicate().get(bytes);
        return convertBytesToHex(bytes);
    }
}