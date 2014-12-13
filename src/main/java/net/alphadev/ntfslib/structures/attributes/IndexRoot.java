package net.alphadev.ntfslib.structures.attributes;

import net.alphadev.ntfslib.api.Entry;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jan on 13.12.14.
 */
public class IndexRoot extends Attribute {
    private Map<String, Entry> entryCache = new HashMap<>();

    public IndexRoot(ByteBuffer attributeData) {
        super(attributeData);

        final ByteBuffer bb = getPayloadBuffer();

    }

    public Iterable<Entry> getEntries() {
        return entryCache.values();
    }

    public Entry getEntry(String name) {
        return entryCache.get(name);
    }
}
