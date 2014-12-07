/**
 * Copyright © 2014 Jan Seeger
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
package net.alphadev.ntfslib.structures.attributes;

import net.alphadev.ntfslib.api.StreamProvider;
import net.alphadev.ntfslib.util.AbsoluteDataStream;
import net.alphadev.ntfslib.util.BufferUtil;
import net.alphadev.ntfslib.util.ByteBufferStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class Attribute {
    public static final long EPOCH_DIFFERENCE = 116444736000000000L;

    public static final short TYPE_IDENTIFIER = 0x00;
    public static final short ATTRIBUTE_LENGTH = 0x04;
    public static final short NON_RESIDENT = 0x08;
    public static final short NAME_LENGTH = 0x09;
    public static final short NAME_OFFSET = 0x0a;
    public static final short FLAGS = 0x0c;
    public static final short IDENTIFIER = 0x0e;
    public static final short PAYLOAD_LENGTH = 0x10;
    public static final short PAYLOAD_OFFSET = 0x14;

    private ByteBuffer payload;
    private AttributeType type;
    private String attributeName;
    private int length;
    private int payloadLength;
    private short payloadOffset;
    private short flags;
    private short identifier;
    private byte nonResident;

    public Attribute(ByteBuffer bb) {
        int typeInt = bb.getInt(TYPE_IDENTIFIER);
        type = AttributeType.parse(typeInt);
        length = bb.getInt(ATTRIBUTE_LENGTH);
        nonResident = bb.get(NON_RESIDENT);

        byte nameLength = bb.get(NAME_LENGTH);
        short nameOffset = bb.getShort(NAME_OFFSET);
        if (nameLength != 0) {
            attributeName = readString(bb, nameOffset, nameLength);
        }

        flags = bb.getShort(FLAGS);
        identifier = bb.getShort(IDENTIFIER);

        payloadLength = bb.getInt(PAYLOAD_LENGTH);
        payloadOffset = (short) (bb.getShort(PAYLOAD_OFFSET) + 2 * nameLength);
        final int payloadEnd = payloadOffset + payloadLength;
        payload = BufferUtil.copy(bb, payloadOffset, payloadEnd);
    }

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

    public static String readString(ByteBuffer buffer, short offset, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i += 2) {
            sb.append(buffer.getChar(i + offset));
        }

        return sb.toString();
    }

    public StreamProvider getPayload() {
        return new StreamProvider() {
            @Override
            public InputStream getStream() {
                if (payload.hasArray()) {
                    // use heap buffer; no array is created; only the reference is used
                    return new ByteArrayInputStream(payload.array());
                }

                return new ByteBufferStream(payload);
            }
        };
    }

    public AttributeType getType() {
        return type;
    }

    public int getLength() {
        return this.length;
    }

    public boolean isResident() {
        return nonResident == 0;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public String getPayloadAsString() {
        return readString(payload, (short) 0, payloadLength);
    }
}
