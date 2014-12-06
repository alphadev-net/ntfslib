package net.alphadev.ntfslib.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtil {
    public static ByteBuffer copy(ByteBuffer origin, int start, int end) {
        ByteOrder order = origin.order();
        ByteBuffer copy = origin.duplicate();
        copy.position(start);
        copy.limit(end);
        copy = copy.slice();
        copy.order(order);
        return copy;
    }

    public static ByteBuffer copy(ByteBuffer origin, int start) {
        return copy(origin, start, origin.limit());
    }
}