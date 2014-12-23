package net.alphadev.ntfslib.structures.attributes.index;

import java.nio.ByteBuffer;

import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.structures.attributes.AttributeType;

/**
 * Created by jan on 13.12.14.
 */
public class IndexRoot extends Attribute {
    private static final byte ATTRIBUTE_TYPE = 0x00;
    private static final byte COLLATION_RULE = 0x04;

    /**
     * as Bytes.
     */
    private static final byte INDEX_ALLOCATION_ENTRY_SIZE = 0x08;
    private static final byte CLUSTERS_PER_INDEX_RECORD = 0x0c;
    private static final byte FIRST_ENTRY_OFFSET = 0x10;
    private static final byte TOTAL_INDEX_ENTRIES = 0x14;
    private static final byte ALLOCATED_INDEX_ENTRY_SIZE = 0x18;
    private static final byte FLAGS = 0x1c;

    private ByteBuffer bb;

    public IndexRoot(ByteBuffer attributeData) {
        super(attributeData);

        bb = getPayloadBuffer();
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
        return super.getPayloadBuffer();
    }
}
