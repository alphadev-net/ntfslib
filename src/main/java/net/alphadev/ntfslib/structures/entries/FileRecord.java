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
import java.util.HashMap;
import java.util.Map;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.Volume;
import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.structures.attributes.AttributeType;

public class FileRecord {
    public static final int FILE_SIGNATURE = 0x454c4946;
    public static final int BAAD_SIGNATURE = 0x44414142;

    private Map<AttributeType, ? extends Attribute> attributes;
    private Volume volume;
    private final long offset;
    private int length;
    private long attributeOffset;

    public FileRecord(Volume volume, long offset) throws IOException {
        this.volume = volume;
        this.offset = offset;

        length = volume.getParameter().getBytesPerMftRecord();

        this.attributes = new HashMap<>();
        ByteBuffer buffer = ByteBuffer.allocate(length);
        volume.read(offset, buffer);

        int magic = buffer.getInt(0);
        if(magic != FILE_SIGNATURE) {
            throw new IllegalArgumentException("no magic signature found!");
        }

        this.attributeOffset = offset + 5;
    }

    public Attribute getAttribute(AttributeType attribute) {
        if(!attributes.containsKey(attribute)) {
            
        }

        return attributes.get(attribute);
    }
}
