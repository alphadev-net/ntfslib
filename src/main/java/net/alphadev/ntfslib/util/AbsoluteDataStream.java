package net.alphadev.ntfslib.util;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;

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
        try {
            checkPosition(absPosition);
            current += 8;
            return input.readLong();
        } catch (IOException ex) {
            return -1;
        }
    }

    public int getInt(int absPosition) {
        try {
            checkPosition(absPosition);
            current += 4;
            return input.readInt();
        } catch (IOException ex) {
            return -1;
        }
    }

    public short getShort(int absPosition) {
        try {
            checkPosition(absPosition);
            current += 2;
            return input.readShort();
        } catch (IOException ex) {
            return -1;
        }
    }

    public byte getByte(int absPosition) {
        try {
            checkPosition(absPosition);
            current += 1;
            return input.readByte();
        } catch (IOException ex) {
            return -1;
        }
    }

    private void checkPosition(int pos) throws IOException {
        if(pos < current) {
            input.close();
            input = new DataInputStream(provider.getStream());
        }

        current = pos;
        input.skipBytes(current);
    }
}