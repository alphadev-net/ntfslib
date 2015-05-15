package net.alphadev.ntfslib.structures.entries;

import net.alphadev.ntfslib.NtfsEntry;
import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.attributes.AttributeType;
import net.alphadev.ntfslib.structures.attributes.index.IndexAllocation;
import net.alphadev.ntfslib.structures.attributes.index.IndexEntry;
import net.alphadev.ntfslib.structures.attributes.index.IndexRoot;

import java.util.Iterator;

public class IndexIterator implements Iterable<Entry> {
    private final IndexRoot indexRoot;
    private final IndexAllocation indexAllocation;
    private final MasterFileTable mft;
    private final Entry parentDir;

    public IndexIterator(MasterFileTable mft, FileRecord fileRecord, Entry parentDir) {
        indexRoot = (IndexRoot) fileRecord.getAttribute(AttributeType.INDEX_ROOT);
        indexAllocation = (IndexAllocation) fileRecord.getAttribute(AttributeType.INDEX_ALLOCATION);
        this.mft = mft;
        this.parentDir = parentDir;
    }

    @Override
    public Iterator<Entry> iterator() {
        return new InternalIterator();
    }

    private final class InternalIterator implements Iterator<Entry> {
        private IndexEntry entry;
        private int offset;

        public InternalIterator() {
            offset = indexRoot.getFirstEntryOffset() + 0x10;
            entry = new IndexEntry(indexRoot, offset);
        }

        @Override
        public boolean hasNext() {
            return entry != null && !entry.isLast();
        }

        @Override
        public Entry next() {
            final NtfsEntry dirEntry = new NtfsEntry(mft, entry, parentDir);
            offset += entry.getSize();
            return dirEntry;
        }

        @Override
        public void remove() {
        }
    }
}
