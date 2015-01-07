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
