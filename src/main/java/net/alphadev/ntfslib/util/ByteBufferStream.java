package net.alphadev.ntfslib.util;

import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ByteBufferStream extends InputStream {
    private ByteBuffer buf;

    public ByteBufferStream(ByteBuffer buf) {
        this.buf = buf;
    }

    public int read() throws IOException {
        if (!buf.hasRemaining()) {
            return -1;
        }
        return buf.get() & 0xFF;
    }

    public int read(byte[] bytes, int off, int len)
            throws IOException {
        if (!buf.hasRemaining()) {
            return -1;
        }

        len = Math.min(len, buf.remaining());
        buf.get(bytes, off, len);
        return len;
    }
}
