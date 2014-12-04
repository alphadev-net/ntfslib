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

import java.io.IOException;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.api.Filesystem;
import net.alphadev.ntfslib.structures.BootSector;
import net.alphadev.ntfslib.structures.ExtendedBpb;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.entries.VolumeInfo;

/**
 * NTFS Filesystem.
 * 
 * @author Jan Seeger
 */
public class NtfsFilesystem implements Filesystem {
    private BlockDevice mDevice;
    private MasterFileTable mft;

    public NtfsFilesystem(BlockDevice device) throws IOException {
        mDevice = device;

        final BootSector boot = BootSector.read(device);
        ExtendedBpb pbp = boot.getBootPartitionParameter();
        mft = MasterFileTable.read(device, pbp.getMftLogicalCluster(),
                pbp.getMftMirrorLogicalCluster());
    }

    @Override
    public String getVolumeName() {
        return mft.getVolumeInfo().getVolumeLabel();
    }
}
