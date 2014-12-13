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
import net.alphadev.ntfslib.util.BufferUtil;
import net.alphadev.ntfslib.util.ByteBufferStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Attribute {
    private static final short TYPE_IDENTIFIER = 0x00;
    private static final short ATTRIBUTE_LENGTH = 0x04;
    private static final short NON_RESIDENT = 0x08;
    private static final short NAME_LENGTH = 0x09;
    private static final short NAME_OFFSET = 0x0a;
    private static final short FLAGS = 0x0c;
    private static final short IDENTIFIER = 0x0e;
    private static final short PAYLOAD_LENGTH = 0x10;
    private static final short PAYLOAD_OFFSET = 0x14;

    private ByteBuffer attributeData;

    public Attribute(ByteBuffer attributeData) {
        this.attributeData = attributeData;
    }

    public static String readString(ByteBuffer buffer, short offset, int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i += 2) {
            sb.append(buffer.getChar(i + offset));
        }

        return sb.toString();
    }

    public static Attribute create(ByteBuffer bb) {
        switch (new Attribute(bb).getType()) {
            case VOLUME_NAME:
                return new VolumeName(bb);
            default:
                return new Attribute(bb);
        }
    }

    /**
     * Attribute name or null.
     *
     * @return name ore null
     */
    public String getAttributeName() {
        byte nameLength = getAttributeNameLength();
        if (nameLength == 0) {
            return null;
        }

        short nameOffset = getAttributeNameOffset();
        return readString(attributeData, nameOffset, nameLength);
    }

    protected final byte getAttributeNameLength() {
        return attributeData.get(NAME_LENGTH);
    }

    protected final short getAttributeNameOffset() {
        return attributeData.getShort(NAME_OFFSET);
    }

    public StreamProvider getPayload() {
        final ByteBuffer payload = getPayloadBuffer();
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
        final int typeInt = attributeData.getInt(TYPE_IDENTIFIER);
        return AttributeType.parse(typeInt);
    }

    public int getLength() {
        return attributeData.getInt(ATTRIBUTE_LENGTH);
    }

    public boolean isResident() {
        return attributeData.get(NON_RESIDENT) == 0;
    }

    public short getFlags() {
        return attributeData.getShort(FLAGS);
    }

    public short getIdentifier() {
        return attributeData.getShort(IDENTIFIER);
    }

    protected final int getPayloadLength() {
        return attributeData.getInt(PAYLOAD_LENGTH);
    }

    protected final short getPayloadOffset() {
        return (short) (attributeData.getShort(PAYLOAD_OFFSET) + 2 * getAttributeNameLength());
    }

    protected ByteBuffer getPayloadBuffer() {
        int payloadLength = getPayloadLength();
        short payloadOffset = getPayloadOffset();
        final int payloadEnd = payloadOffset + payloadLength;
        return BufferUtil.copy(attributeData, payloadOffset, payloadEnd);
    }
}
