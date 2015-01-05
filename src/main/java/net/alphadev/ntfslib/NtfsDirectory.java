package net.alphadev.ntfslib;

import net.alphadev.ntfslib.api.Directory;
import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.structures.attributes.Filename;
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
    private Filename filename;
    private Entry parentDir;

    public NtfsDirectory(MasterFileTable mft, FileRecord rootDir, Entry parentDir) {
        super(mft);

        this.parentDir = parentDir;
        filename = (Filename) rootDir.getAttribute(AttributeType.FILE_NAME);
        indexRoot = (IndexRoot) rootDir.getAttribute(AttributeType.INDEX_ROOT);

        int offset = indexRoot.getFirstEntryOffset();
        IndexEntry entry;
        do {
            entry = new IndexEntry(indexRoot, offset);
            entryCache.put(entry.getName(), new NtfsEntry(mft, entry, parentDir));
            offset += indexRoot.getIndexEntryAllocationSize();
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
        return filename.getFilename();
    }

    @Override
    public boolean isDirectory() {
        return true;
    }
}
