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

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.entries.MftEntry;
import net.alphadev.ntfslib.structures.entries.VolumeInfo;

/**
 * @author Jan Seeger <jan@alphadev.net>
 */
public class MasterFileTable {
    public static final String VOLUME_INFO = "$Volume";

    private BlockDevice device;
    private VolumeInfo volumeInfo;
    private long baseOffset;
    private long clusterSize;

    private MasterFileTable(BlockDevice device, long offset) {
        this.device = device;
        this.baseOffset = offset;
    }

    public static MasterFileTable read(BlockDevice device, BootSector boot) {
        final ExtendedBpb partitionParameter = boot.getBootPartitionParameter();
        final long mftMainStart = partitionParameter.getMftLogicalCluster();
        final long mftCopyStart = partitionParameter.getMftMirrorLogicalCluster();

        final long clusterSize = 0;//partitionParameter.
        final MasterFileTable mainMft = new MasterFileTable(device, mftMainStart);
        return mainMft;
    }

    public <T extends MftEntry> T getEntry(String entryNumber) {
        return null;
    }

    public <T extends MftEntry> T getEntry(long entryNumber) {
        return null;
    }

    public VolumeInfo getVolumeInfo() {
        if(volumeInfo == null) {
            volumeInfo = (VolumeInfo) getEntry(VOLUME_INFO);
        }

        return volumeInfo;
    }
}
