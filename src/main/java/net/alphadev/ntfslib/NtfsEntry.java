/**
 * Copyright © 2014-2015 Jan Seeger
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

import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.structures.attributes.FileFlag;
import net.alphadev.ntfslib.structures.attributes.Filename;
import net.alphadev.ntfslib.structures.attributes.index.IndexEntry;
import net.alphadev.ntfslib.structures.entries.FileRecord;

import java.nio.ByteBuffer;

/**
 * Created by jan on 13.12.14.
 */
public class NtfsEntry extends NtfsStructure implements Entry {
    private MasterFileTable mft;
    private IndexEntry indexEntry;
    private Entry parentDir;

    public NtfsEntry(MasterFileTable mft, IndexEntry entry, Entry parentDir) {
        super(mft);
        this.parentDir = parentDir;
        this.indexEntry = entry;
        this.mft = mft;
    }

    @Override
    public Entry getParent() {
        return parentDir;
    }

    @Override
    public String getName() {
        final FileRecord fileRecord = mft.getEntry(indexEntry.getFileReference());
        final Filename filename = (Filename) fileRecord.getAttribute(AttributeType.FILE_NAME);
        return filename.getFilename();
    }

    @Override
    public boolean isDirectory() {
        return false;
    }
}
