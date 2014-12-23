package net.alphadev.ntfslib.structures.attributes.index;

import java.nio.ByteBuffer;

/**
 * Created by jan on 21.12.14.
 */
public class IndexEntry {
    private ByteBuffer buffer;

    public IndexEntry(ByteBuffer entryData, int offset) {
        buffer = entryData;
    }

    public String getName() {
        return null;
    }

    public boolean isLast() {
        return true;
    }
}