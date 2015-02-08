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

import net.alphadev.ntfslib.api.Directory;
import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.structures.attributes.Filename;
import net.alphadev.ntfslib.structures.attributes.index.IndexAllocation;
import net.alphadev.ntfslib.structures.attributes.index.IndexEntry;
import net.alphadev.ntfslib.structures.attributes.index.IndexRoot;
import net.alphadev.ntfslib.structures.entries.FileRecord;
import net.alphadev.ntfslib.util.BufferUtil;

import java.util.HashMap;
import java.util.Map;
import java.nio.ByteBuffer;

/**
 * Created by jan on 13.12.14.
 */
public class NtfsDirectory extends NtfsStructure implements Directory {
    private Map<String, Entry> entryCache = new HashMap<>();
    private IndexRoot indexRoot;
    private IndexAllocation indexAllocation;
    private Entry parentDir;
    private FileRecord fileRecord;

    public NtfsDirectory(MasterFileTable mft, FileRecord fileRecord, Entry parentDir) {
        super(mft);

        this.parentDir = parentDir;
        indexRoot = (IndexRoot) rootDir.getAttribute(AttributeType.INDEX_ROOT);
        indexAllocation = (IndexAllocation) rootDir.getAttribute(AttributeType.INDEX_ALLOCATION);
        this.fileRecord = fileRecord;
    }

        int offset = indexRoot.getFirstEntryOffset() + 0x10;
        IndexEntry entry;
        do {
            entry = new IndexEntry(indexRoot, offset);
            final NtfsEntry dirEntry = new NtfsEntry(mft, entry, parentDir);
            entryCache.put(dirEntry.getName(), dirEntry);
            offset += entry.getSize();
        } while (entry != null && !entry.isLast());
    }

    @Override
    public Iterable<Entry> getEntries() {
        return entryCache.values();
    }

    @Override
    public Entry getEntry(String name) {
        return entryCache.get(name);
    }

    @Override
    public Entry getParent() {
        return parentDir;
    }

    @Override
    public String getName() {
        final Filename filename =
                (Filename) fileRecord.getAttribute(AttributeType.FILE_NAME);

        return filename.getFilename();
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
