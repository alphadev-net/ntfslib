package net.alphadev.ntfslib.util;

import java.io.DataInputStream;
import java.io.InputStream;

import net.alphadev.ntfslib.api.StreamProvider;

public class AbsoluteDataStream {
    private final StreamProvider provider;
    private DataInputStream input;
    private int current;

    public AbsoluteDataStream(StreamProvider provider) {
        this.provider = provider;
        this.input = new DataInputStream(provider.getStream());
    }

    public long getLong(int absPosition) {
        return 0;
    }

    public int getInt(int absPosition) {
        return 0;
    }

    public short getShort(int absPosition) {
        return 0;
    }

    public byte getByte(int absPosition) {
        return 0;
    }

    private void checkPosition(int pos) {
        
    }
}