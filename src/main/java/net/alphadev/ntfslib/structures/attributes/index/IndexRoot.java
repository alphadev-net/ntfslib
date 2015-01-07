package net.alphadev.ntfslib.structures.attributes.index;

import java.nio.ByteBuffer;

import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.util.BufferUtil;
import net.alphadev.ntfslib.util.BitStitching;

/**
 * Created by jan on 13.12.14.
 */
public class IndexRoot extends Attribute {
    public static final byte ROOT_SIZE = 0x10;
    public static final byte HEADER_SIZE = 0x10;

    // Index Root
    private static final byte ATTRIBUTE_TYPE = 0x00;
    private static final byte COLLATION_RULE = 0x04;
    private static final byte INDEX_ALLOCATION_ENTRY_SIZE = 0x08;
    private static final byte CLUSTERS_PER_INDEX_RECORD = 0x0c;

    // Index Header
    private static final byte FIRST_ENTRY_OFFSET = ROOT_SIZE + 0x00;
    private static final byte TOTAL_INDEX_ENTRIES = ROOT_SIZE + 0x04;
    private static final byte ALLOCATED_INDEX_ENTRY_SIZE = ROOT_SIZE + 0x08;
    private static final byte FLAGS = ROOT_SIZE + 0x0c;

    private ByteBuffer bb;

    public IndexRoot(ByteBuffer attributeData) {
        super(attributeData);
        bb = super.getPayloadBuffer();
    }

    public boolean isLargeIndex() {
        return bb.get(FLAGS) == 1;
    }

    public boolean isDirectory() {
        return AttributeType.FILE_NAME.equals(getAttributeType());
    }

    private AttributeType getAttributeType() {
        return AttributeType.parse(bb.getInt(ATTRIBUTE_TYPE));
    }

    public int getCollationRule() {
        return bb.getInt(COLLATION_RULE);
    }

    public byte getClustersPerIndex() {
        return bb.get(CLUSTERS_PER_INDEX_RECORD);
    }

    public int getFirstEntryOffset() {
        return bb.get(FIRST_ENTRY_OFFSET);
    }

    public int getTotalEntrySize() {
        return bb.get(TOTAL_INDEX_ENTRIES);
    }

    public int getIndexEntryAllocationSize() {
        return bb.get(INDEX_ALLOCATION_ENTRY_SIZE);
    }

    @Override
    public ByteBuffer getPayloadBuffer() {
        return BufferUtil.copy(bb, HEADER_SIZE);
    }
}
