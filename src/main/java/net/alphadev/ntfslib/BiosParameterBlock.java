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
package net.alphadev.ntfslib;

import java.nio.ByteBuffer;

public class BiosParameterBlock {
    public static final byte BYTES_PER_SECTOR_OFFSET = 0X0B;
    public static final byte SECTORS_PER_CLUSTER_OFFSET = 0X0D;
    public static final byte RESERVED_SECTORS_OFFSET = 0X0E;
    public static final byte FAT_COPIES_OFFSET = 0X10;
    public static final byte ROOT_DIR_ENTRIES_OFFSET = 0x11;
    public static final byte NUMBER_OF_SECTORS_OFFSET = 0x13;
    public static final byte MEDIA_DESCRIPTOR_OFFSET = 0x15;
    public static final byte SECTORS_PER_FAT_OFFSET = 0x16;
    public static final byte SECTORS_PER_TRACK_OFFSET = 0x18;
    public static final byte NUMBER_OF_HEADS_OFFSET = 0x1A;
    public static final byte HIDDEN_SECTORS_OFFSET = 0x1C;
    public static final byte SECTORS_BIG_OFFSET = 0x20;

    private short bytesPerSector;
    private short reservedSectors;
    private byte sectorsPerCluster;
    private byte mediaDescriptor;

    public BiosParameterBlock(ByteBuffer bb) {
        bytesPerSector = bb.getShort(BYTES_PER_SECTOR_OFFSET);
        sectorsPerCluster = bb.get(SECTORS_PER_CLUSTER_OFFSET);
        reservedSectors = bb.getShort(RESERVED_SECTORS_OFFSET);

        byte fatCopies = bb.get(FAT_COPIES_OFFSET);
        if (fatCopies != 0) {
            throw new IllegalArgumentException("FAT copies must be zero!");
        }

        short rootDirEntries = bb.getShort(ROOT_DIR_ENTRIES_OFFSET);
        if (rootDirEntries != 0) {
            throw new IllegalArgumentException("RootDir entries must be zero!");
        }

        short numberOfSectors = bb.getShort(NUMBER_OF_SECTORS_OFFSET);
        if (numberOfSectors != 0) {
            throw new IllegalArgumentException("Number of Sectors must be zero!");
        }

        mediaDescriptor = bb.get(MEDIA_DESCRIPTOR_OFFSET);

        short sectorsPerFat = bb.getShort(SECTORS_PER_FAT_OFFSET);
        if (sectorsPerFat != 0) {
            throw new IllegalArgumentException("Sectors per FAT must be zero!");
        }

        int sectorsBig = bb.getInt(SECTORS_BIG_OFFSET);
        if (sectorsBig != 0) {
            throw new IllegalArgumentException("Sectors per FAT must be zero!");
        }
    }

    public short getBytesPerSector() {
        return bytesPerSector;
    }

    public short getReservedSectors() {
        return reservedSectors;
    }

    public byte getSectorsPerCluster() {
        return sectorsPerCluster;
    }

    public byte getMediaDescriptor() {
        return mediaDescriptor;
    }
}