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
package net.alphadev.ntfslib.structures.boot;

import net.alphadev.ntfslib.api.BlockDevice;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BootSector extends Sector {
    public static final int OEM_ID_OFFSET = 0x03;

    /**
     * The size of a boot sector in bytes.
     */
    public final static int SIZE = 512;

    private final String oemId;
    private final ExtendedBpb pbp;

    private BootSector(BlockDevice device) throws IOException {
        super(device, 0, SIZE);
        oemId = getString(OEM_ID_OFFSET, 8);
        pbp = new ExtendedBpb(super.buffer);
    }

    public static BootSector read(BlockDevice device) throws IOException {
        final ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        device.read(510, bb);

        if ((bb.get(0) & 0xff) != 0x55 ||
                (bb.get(1) & 0xff) != 0xaa) throw new IOException(
                "missing boot sector signature");

        return new BootSector(device);
    }

    public ExtendedBpb getBootPartitionParameter() {
        return pbp;
    }

    public String getOemId() {
        return oemId;
    }
}
