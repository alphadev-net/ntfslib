package net.alphadev.ntfslib.structures.attributes;

import net.alphadev.ntfslib.api.Directory;
import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.structures.entries.FileRecord;

/**
 * Created by jan on 13.12.14.
 */
public class NtfsDirectory implements Directory {
    private IndexRoot indexRoot;
    private Filename filename;

    public NtfsDirectory(FileRecord rootDir) {
        filename = (Filename) rootDir.getAttribute(AttributeType.FILE_NAME);
        indexRoot = (IndexRoot) rootDir.getAttribute(AttributeType.INDEX_ROOT);
    }

    @Override
    public Iterable<Entry> getEntries() {
        return indexRoot.getEntries();
    }

    @Override
    public Entry getEntry(String name) {
        return indexRoot.getEntry(name);
    }

    @Override
    public Entry getParent() {
        return null;
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
