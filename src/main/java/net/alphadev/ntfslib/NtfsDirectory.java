package net.alphadev.ntfslib;

import net.alphadev.ntfslib.api.Directory;
import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.structures.attributes.Filename;
import net.alphadev.ntfslib.structures.attributes.IndexRoot;
import net.alphadev.ntfslib.structures.entries.FileRecord;

import java.util.HashMap;
import java.util.Map;

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
        indexRoot.getIndexEntryAllocationSize();


        if (indexRoot.isLargeIndex()) {
            // TODO: fetch INDEX_ALLOCATION_FILE
        }
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
