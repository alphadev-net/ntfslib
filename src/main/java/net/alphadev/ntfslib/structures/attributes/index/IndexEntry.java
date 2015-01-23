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
import java.util.EnumSet;

import net.alphadev.ntfslib.structures.attributes.Attribute;
import net.alphadev.ntfslib.util.BitStitching;
import net.alphadev.ntfslib.util.BufferUtil;

/**
 * Created by jan on 21.12.14.
 */
public class IndexEntry {
    private static final byte REFERENCE_OFFSET = 0x00;
    private static final byte ENTRY_SIZE = 0x08;
    private static final byte STREAM_SIZE = 0x0a;
    private static final byte FLAGS = 0x0c;

    private ByteBuffer buffer;

    public IndexEntry(IndexRoot indexRoot, int offset) {
        buffer = BufferUtil.copy(indexRoot.getPayloadBuffer(), offset);
    }

    public long getFileReference() {
        return buffer.getLong(REFERENCE_OFFSET);
    }

    public boolean isLast() {
        return getFlags().contains(IndexEntryFlag.LAST);
    }

    public EnumSet<IndexEntryFlag> getFlags() {
        return EnumSet.noneOf(IndexEntryFlag.class);
    }

    public short getSize() {
        return buffer.getShort(ENTRY_SIZE);
    }
}
