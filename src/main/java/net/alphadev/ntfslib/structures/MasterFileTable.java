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
import net.alphadev.ntfslib.structures.Volume;
import net.alphadev.ntfslib.structures.attributes.VolumeInfo;
import net.alphadev.ntfslib.structures.entries.FileRecord;
import net.alphadev.ntfslib.structures.entries.KnownMftEntries;

/**
 * @author Jan Seeger <jan@alphadev.net>
 */
public class MasterFileTable {
    private Volume volume;
    private VolumeInfo volumeInfo;
    private long offset;

    private MasterFileTable(Volume volume, long offset) {
        this.volume = volume;
        this.offset = offset;
    }

    public static MasterFileTable read(Volume volume) {
        final ExtendedBpb parameter = volume.getParameter();
        final long mftMainStart = parameter.getMftLogicalCluster();
        final long mftMirrorStart = parameter.getMftMirrorLogicalCluster();
        final MasterFileTable mainMft = new MasterFileTable(volume, mftMainStart);

        /*
         * TODO: check mft integrity here and restore broken entries from mftMirror
         */

        return mainMft;
    }

    public FileRecord getEntry(KnownMftEntries entry) throws IOException {
        long address = offset * entry.getValue();
        return getEntry(address);
    }

    public FileRecord getEntry(long address) throws IOException {
        final long beginIndex = offset * address;
        return new FileRecord(volume, beginIndex);
    }
}
