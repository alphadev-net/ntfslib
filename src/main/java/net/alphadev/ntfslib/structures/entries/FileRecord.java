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
package net.alphadev.ntfslib.structures.entries;

import net.alphadev.ntfslib.structures.Volume;
import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.structures.AttributeCache;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.util.BufferUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FileRecord {
    public static final int FILE_SIGNATURE = 0x454c4946;
    public static final int BAAD_SIGNATURE = 0x44414142;
    public static final int END_OF_ATTRIBUTES = 0xffffffff;

    public static final short SIGNATURE_OFFSET = 0;
    public static final short PAYLOAD_OFFSET = 4;
    public static final short PAYLOAD_LENGTH = 6;
    public static final short LOG_SEQUENCE_NUMBER = 8;
    public static final short SEQUENCE_VALUE = 16;
    public static final short LINK_COUNT = 18;
    public static final short FIRST_ATTRIBUTE = 20;
    public static final short FLAGS = 22;
    public static final short SIZE_USED = 24;
    public static final short SIZE_ALLOCATED = 28;
    public static final short BASE_RECORD = 32;
    public static final short NETX_ATTR = 40;

    private final AttributeCache attributes = new AttributeCache();
    private final Volume volume;
    private final long offset;
    private final ByteBuffer bb;

    public FileRecord(Volume volume, long offset) throws IOException {
        this.volume = volume;
        this.offset = offset;

        final int size = volume.getParameter().getBytesPerMftRecord();
        bb = ByteBuffer.allocate(size);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        volume.read(offset, bb);
        bb.rewind();

        int magic = getSignature();
        if (magic != FILE_SIGNATURE) {
            throw new IllegalArgumentException("no magic signature found!");
        }

        //bb.getShort(NETX_ATTR)

        int nextAttribute = bb.getShort(FIRST_ATTRIBUTE);
        while (bb.getInt(nextAttribute) != END_OF_ATTRIBUTES) {
            final ByteBuffer inner = BufferUtil.copy(bb, nextAttribute);
            final Attribute attribute = Attribute.create(inner);
            this.attributes.add(attribute);
            nextAttribute += attribute.getLength();
        }
    }

    public Attribute getAttribute(AttributeType attribute) {
        return attributes.get(attribute);
    }

    public AttributeCache getAttributes() {
        return attributes;
    }

    public long getOffset() {
        return offset;
    }

    public int getSignature() {
        return bb.getInt(SIGNATURE_OFFSET);
    }

    public long getLogFileSequenceNumber() {
        return bb.getLong(LOG_SEQUENCE_NUMBER);
    }

    public long getBaseRecord() {
        return bb.getLong(BASE_RECORD);
    }

    public int getMftSizeUsed() {
        return bb.getInt(SIZE_USED);
    }

    public int getMftSizeAllocated() {
        return bb.getInt(SIZE_ALLOCATED);
    }

    public short getPayloadOffset() {
        return bb.getShort(PAYLOAD_OFFSET);
    }

    public short getPayloadLength() {
        return bb.getShort(PAYLOAD_LENGTH);
    }

    public short getSequenceValue() {
        return bb.getShort(SEQUENCE_VALUE);
    }

    public short getLinkCount() {
        return bb.getShort(LINK_COUNT);
    }

    public short getFlags() {
        return bb.getShort(FLAGS);
    }
}
