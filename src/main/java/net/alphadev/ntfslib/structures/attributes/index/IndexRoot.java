/**
 * Copyright © 2014-2015 Jan Seeger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
    private static final byte ALLOCATED_INDEX_BLOCK_SIZE = 0x08;
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

    private int getAttributeType() {
        return bb.getInt(ATTRIBUTE_TYPE);
    }

    public int getCollationRule() {
        return bb.getInt(COLLATION_RULE);
    }

    public int getIndexEntryAllocationSize() {
        return bb.get(ALLOCATED_INDEX_BLOCK_SIZE);
    }

    public boolean isLargeIndex() {
        return bb.get(FLAGS) == 1;
    }

    public boolean isDirectory() {
        return AttributeType.FILE_NAME.equals(getAttributeType());
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

    @Override
    public ByteBuffer getPayloadBuffer() {
        final int entryOffset = getFirstEntryOffset();
        final int entryLength = getTotalEntrySize();
        return BufferUtil.copyFor(bb, entryOffset, entryLength);
    }
}
