package net.alphadev.ntfslib;

import net.alphadev.ntfslib.structures.MasterFileTable;

/**
 * Created by jan on 13.12.14.
 */
public class NtfsStructure {
    private final MasterFileTable masterFileTable;

    public NtfsStructure(MasterFileTable masterFileTable) {

        this.masterFileTable = masterFileTable;
    }

    public MasterFileTable getMasterFileTable() {
        return masterFileTable;
    }
}
