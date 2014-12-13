package net.alphadev.ntfslib.util;

import java.nio.ByteBuffer;

/**
 * Created by jan on 13.12.14.
 */
public class TimeConversionUtil {
    public static final long EPOCH_DIFFERENCE = 116444736000000000L;

    /**
     * Converts Microsoft time value to Unix timestamp.
     */
    public static long parseTimestamp(AbsoluteDataStream bb, int offset) {
        long msftTime = bb.getLong(offset);
        long unixTimestamp = msftTime - EPOCH_DIFFERENCE;
        return unixTimestamp / 100L;
    }

    public static long parseTimestamp(ByteBuffer bb, int offset) {
        long msftTime = bb.getLong(offset);
        long unixTimestamp = msftTime - EPOCH_DIFFERENCE;
        return unixTimestamp / 100L;
    }
}
