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

    private MasterFileTable(BlockDevice device, long offset, boolean isMirror) {
        this.device = device;
    }

    public static MasterFileTable read(BlockDevice device, long offsetMain, long offsetMirror) {
        //MasterFileTable main = 
        return null;
    }

    public MftEntry getEntry(String entryName) {
        return null;
    }

    public MftEntry getEntry(long entryNumber) {
        return null;
    }

    public VolumeInfo getVolumeInfo() {
        if(volumeInfo == null) {
            volumeInfo = VolumeInfo.read(getEntry(VOLUME_INFO));
        }

        return volumeInfo;
    }
}
