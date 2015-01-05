package net.alphadev.ntfslib.structures.attributes.index;

import java.nio.ByteBuffer;

import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.util.BufferUtil;

/**
 * Created by jan on 21.12.14.
 */
public class IndexEntry {
    private static final short REFERENCE_OFFSET = 0x00;
    private static final short FILENAME_LENGTH_CHARACTER = 0x50;
    private static final short FILENAME_OFFSET = 0x52;

    private ByteBuffer buffer;

    public IndexEntry(IndexRoot indexRoot, int offset) {
        buffer = BufferUtil.copy(indexRoot.getPayloadBuffer(), offset);
    }

    public String getName() {
        byte filenameLength = buffer.get(FILENAME_LENGTH_CHARACTER);
        return Attribute.readString(buffer, FILENAME_OFFSET, filenameLength, (byte) 1);
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

    private static class EntryFlag {
    }
}
