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
package net.alphadev.ntfslib.structures.entries;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.Volume;
import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.util.BitStitching;

public class FileRecord {
    public static final int FILE_SIGNATURE = 0x454c4946;
    public static final int BAAD_SIGNATURE = 0x44414142;

    private Map<AttributeType, ? extends Attribute> attributes;
    private Volume volume;
    private final long offset;
    private int length;

    public FileRecord(Volume volume, long offset) throws IOException {
        this.volume = volume;
        this.offset = offset;

        final int size = volume.getParameter().getBytesPerMftRecord();
        ByteBuffer buffer = ByteBuffer.allocate(size);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        volume.read(offset, buffer);
        buffer.rewind();

        int magic = buffer.getInt(0);
        if(magic != FILE_SIGNATURE) {
            throw new IllegalArgumentException("no magic signature found!");
        }

        this.attributes = new HashMap<>();

        long attributeOffset = offset + 5;
        while(attributeOffset != 0xff) {
            parseAttribute(attributeOffset);
        }
    }

    public int parseAttribute(long baseAddress) {
        Attribute attribute = new Attribute();
        attribute.setFixupOffset();
        this.attributes.add(attribute);
        return attribute.getLength();
    }

    public Attribute getAttribute(AttributeType attribute) {
        return attributes.get(attribute);
    }
}
