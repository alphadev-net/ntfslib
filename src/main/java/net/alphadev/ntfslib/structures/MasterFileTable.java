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

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.attributes.VolumeInfo;
import net.alphadev.ntfslib.structures.entries.KnownMftEntries;
import net.alphadev.ntfslib.structures.entries.MftEntry;

/**
 * @author Jan Seeger <jan@alphadev.net>
 */
public class MasterFileTable {
    private BlockDevice device;
    private VolumeInfo volumeInfo;
    private long offset;
    private int clusterSize;

    private MasterFileTable(BlockDevice device, long offset, int clusterSize) {
        this.device = device;
        this.offset = offset;
        this.clusterSize = clusterSize;
    }

    public static MasterFileTable read(BlockDevice device, BootSector boot) {
        final ExtendedBpb partitionParameter = boot.getBootPartitionParameter();
        final long mftMainStart = partitionParameter.getMftLogicalCluster();
        final long mftCopyStart = partitionParameter.getMftMirrorLogicalCluster();
        final int clusterSize = (int) partitionParameter.getClustersPerMftRecord();
        final MasterFileTable mainMft = new MasterFileTable(device, mftMainStart, clusterSize);
        return mainMft;
    }

    public MftEntry getEntry(KnownMftEntries entry) throws IOException {
        return getEntry(entry.getValue());
    }

    public MftEntry getEntry(long entryNumber) throws IOException {
        final long beginIndex = offset * entryNumber;
        final MftEntry entry = new MftEntry(device, beginIndex, clusterSize);

        return entry;
    }
}
