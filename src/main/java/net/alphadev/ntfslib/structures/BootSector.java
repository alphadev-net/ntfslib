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
package net.alphadev.ntfslib.structures;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.Sector;

public class BootSector extends Sector {
    public static final int OEM_ID_OFFSET = 0x03;
    
    /**
     * The size of a boot sector in bytes.
     */
    public final static int SIZE = 512;

    private long oemId;
    private ExtendedBpb pbp;

    private BootSector(BlockDevice device) throws IOException {
        super(device, 0, SIZE);
        markDirty();

        oemId = get64(OEM_ID_OFFSET);
        pbp = new ExtendedBpb(buffer);
    }

    public static BootSector read(BlockDevice device) throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(512);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        device.read(0, bb);

        if ((bb.get(510) & 0xff) != 0x55 ||
                (bb.get(511) & 0xff) != 0xaa) throw new IOException(
                "missing boot sector signature");

        return new BootSector(device);
    }

    public ExtendedBpb getBootPartitionParameter() {
        return pbp;
    }
}