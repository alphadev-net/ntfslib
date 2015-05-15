package net.alphadev.ntfslib.structures.entries;

import java.util.HashMap;

public class EntryCache extends HashMap<String, net.alphadev.ntfslib.api.Entry> {
    public void putAll(Iterable<net.alphadev.ntfslib.api.Entry> entries) {
        for(net.alphadev.ntfslib.api.Entry entry: entries) {
            put(entry.getName(), entry);
        }
    }
}
