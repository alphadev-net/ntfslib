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
package net.alphadev.ntfslib.structures.attributes;

import net.alphadev.ntfslib.structures.attributes.index.IndexRoot;
import net.alphadev.ntfslib.util.BufferUtil;

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

    protected final ByteBuffer attributeData;

    protected Attribute(ByteBuffer attributeData) {
        this.attributeData = attributeData;
    }

    public static Attribute create(ByteBuffer bb, int offset) {
        final ByteBuffer temp = BufferUtil.copy(bb, offset);
        final Attribute attr = new Attribute(temp);
        System.out.println(offset + ": " + attr.getType() + ", " + attr.getLength());
        bb = BufferUtil.copy(temp, 0, attr.getLength());

        switch (attr.getType()) {
            case VOLUME_NAME:
                return new VolumeName(bb);
            case INDEX_ROOT:
                return new IndexRoot(bb);
            case FILE_NAME:
                return new Filename(bb);
            default:
                return new Attribute(bb);
        }
    }

    public String getAttributeName() {
        return BufferUtil.readString(attributeData, getAttributeNameOffset(), getAttributeNameLength() * 2);
    }

    public boolean isNamedAttribute() {
        return getAttributeNameLength() != 0;
    }

    protected final byte getAttributeNameLength() {
        return attributeData.get(NAME_LENGTH);
    }

    protected final short getAttributeNameOffset() {
        return attributeData.getShort(NAME_OFFSET);
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

    public short getId() {
        return attributeData.getShort(IDENTIFIER);
    }

    protected final int getPayloadLength() {
        return attributeData.getInt(PAYLOAD_LENGTH);
    }

    protected final short getPayloadOffset() {
        /*
        * According to [1] the attribute's payload beginns at 2n+0x18 where 0x18 is the
        * begin of the name and n is the name size.
        *
        * [1]: http://ftp.kolibrios.org/users/Asper/docs/NTFS/ntfsdoc.html#concept_attribute_header
        */
        final short attributeNameLength =  (short) (2 * getAttributeNameLength());
        return (short) (getAttributeNameOffset() + attributeNameLength);
    }

    protected ByteBuffer getPayloadBuffer() {
        System.out.println("  name: " + getAttributeName() + "(" + getAttributeNameLength() + ")");
        System.out.println("  payload: " + getPayloadOffset() + " -> " + getPayloadLength());
        return BufferUtil.copyFor(attributeData, getPayloadOffset(), getPayloadLength());
    }
}
