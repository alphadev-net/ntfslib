package net.alphadev.ntfslib.structures.attributes.index;

import java.nio.ByteBuffer;

import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.util.BitStitching;
import net.alphadev.ntfslib.util.BufferUtil;

/**
 * Created by jan on 21.12.14.
 */
public class IndexEntry {
    private static final byte REFERENCE_OFFSET = 0x00;
    private static final byte ENTRY_SIZE = 0x08;
    private static final byte STREAM_SIZE = 0x0a;
    private static final byte FLAGS = 0x0c;

    private ByteBuffer buffer;

    public IndexEntry(IndexRoot indexRoot, int offset) {
        buffer = indexRoot.getPayloadBuffer();//BufferUtil.copy(indexRoot.getPayloadBuffer(), offset);
    }

    public long getFileReference() {
        return buffer.getLong(REFERENCE_OFFSET);
    }

    public boolean isLast() {
        return true;
    }

    public EntryFlag getFlags() {
        return null;
    }

    public short getSize() {
        return buffer.getShort(ENTRY_SIZE);
    }

    private static class EntryFlag {
    }
}
