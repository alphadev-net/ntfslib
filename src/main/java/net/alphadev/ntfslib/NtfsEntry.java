package net.alphadev.ntfslib;

import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.structures.MasterFileTable;
import net.alphadev.ntfslib.structures.attributes.FileFlag;
import net.alphadev.ntfslib.structures.attributes.Filename;

import java.nio.ByteBuffer;

/**
 * Created by jan on 13.12.14.
 */
public class NtfsEntry extends NtfsStructure implements Entry {
    private Filename filename;

    public NtfsEntry(MasterFileTable mft, ByteBuffer buffer) {
        super(mft);

        filename = new Filename(buffer);
    }

    @Override
    public Entry getParent() {
        return null;//filename.getParentDirectory();
    }

    @Override
    public String getName() {
        return filename.getFilename();
    }

    @Override
    public boolean isDirectory() {
        return filename.getFilenameFlags().contains(FileFlag.DIRECTORY);
    }
}
