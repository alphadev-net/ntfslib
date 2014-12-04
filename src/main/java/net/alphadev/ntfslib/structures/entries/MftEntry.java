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

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.attributes.AttributeType;

public class MftEntry {
    public static final int FILE_SIGNATURE = 0x454c4946;
    public static final int BAAD_SIGNATURE = 0x44414142;

    private BlockDevice device;
    private long offset;
    private int clusterSize;

    public MftEntry(BlockDevice device, long offset, int clusterSize) throws IOException {
        this.device = device;
        this.offset = offset;
        this.clusterSize = clusterSize;

        ByteBuffer buffer = ByteBuffer.allocate(clusterSize);
        device.read(offset, buffer);
    }

    public ByteBuffer getAttribute(AttributeType attribute) {
        return null;
    }
}
