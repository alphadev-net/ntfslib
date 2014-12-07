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

import net.alphadev.ntfslib.structures.entries.FileRecord;
import net.alphadev.ntfslib.structures.entries.KnownMftEntries;

import java.io.IOException;

/**
 * @author Jan Seeger <jan@alphadev.net>
 */
public class MasterFileTable {
    private final Volume volume;
    private final ExtendedBpb parameter;
    private final long baseOffset;

    private MasterFileTable(Volume volume, long baseOffset) {
        this.volume = volume;
        this.parameter = volume.getParameter();
        this.baseOffset = parameter.calculateBytes((int) baseOffset);
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
        return getEntry(entry.getValue() * parameter.getBytesPerMftRecord());
    }

    public FileRecord getEntry(long address) throws IOException {
        return new FileRecord(volume, baseOffset + address);
    }
}
