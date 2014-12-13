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

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.api.Filesystem;
import net.alphadev.ntfslib.structures.BootSector;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.Volume;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.structures.attributes.VolumeName;
import net.alphadev.ntfslib.structures.entries.FileRecord;
import net.alphadev.ntfslib.structures.entries.KnownMftEntries;

import java.io.IOException;

/**
 * NTFS Filesystem.
 *
 * @author Jan Seeger
 */
public class NtfsFilesystem implements Filesystem {
    private final MasterFileTable mft;
    private final Volume volume;
    private VolumeName volumeName;

    public NtfsFilesystem(BlockDevice device) throws IOException {
        final BootSector boot = BootSector.read(device);
        volume = new Volume(device, boot);
        mft = MasterFileTable.read(volume);
    }

    @Override
    public String getVolumeName() {
        if (volumeName == null) {
            final FileRecord volumeFile = mft.getEntry(KnownMftEntries.VOLUME);

            if (volumeFile != null) {
                volumeName = (VolumeName) volumeFile.getAttribute(AttributeType.VOLUME_NAME);
            }
        }

        return volumeName.getVolumeLabel();
    }
}
