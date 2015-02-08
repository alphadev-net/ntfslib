package net.alphadev.ntfslib.structures.entries;

import net.alphadev.ntfslib.api.Entry;

import java.util.HashMap;

/**
 * Created by jan on 08.02.15.
 */
public class EntryCache extends HashMap<String, Entry> {
    public void putAll(Iterable<Entry> entries) {
        for(Entry entry: entries) {
            put(entry.getName(), entry);
        }
    }
}
