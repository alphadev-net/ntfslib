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
package net.alphadev.ntfslib.structures.attributes;

import java.io.UnsupportedEncodingException;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.entries.FileRecord;
import net.alphadev.ntfslib.util.AbsoluteDataStream;

public class VolumeInfo {
    private String volumeLabel;

    public VolumeInfo(Attribute attr) {
        final AbsoluteDataStream buffer = new AbsoluteDataStream(attr.getPayload());

        int length = attr.getPayloadLength();
        byte[] name = new byte[length];
        for (int i = 0; i < length; i++) {
            name[i] = buffer.getByte(i);
        }

        try {
            volumeLabel = new String(name, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
             throw new IllegalStateException("UTF-8 charset missing", ex);
        }
    }

    public String getVolumeLabel() {
        return volumeLabel;
    }
}
