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
package net.alphadev.ntfslib.structures.entries;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.structures.entries.MftEntry;

public class VolumeInfo {
    private String volumeLabel;

    private VolumeInfo(MftEntry volumeEntry) {
        try {
            ByteBuffer buffer = volumeEntry.getAttribute(AttributeType.VOLUME_NAME);
            volumeLabel = new String(buffer.array(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            // utf8 unsupported?!
        }
    }

    public static VolumeInfo read(MftEntry volumeEntry) {
        return new VolumeInfo(volumeEntry);
    }

    public String getVolumeLabel() {
        return volumeLabel;
    }
}