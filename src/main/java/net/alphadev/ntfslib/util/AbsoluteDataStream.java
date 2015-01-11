/**
 * Copyright © 2014-2015 Jan Seeger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.alphadev.ntfslib.util;

import net.alphadev.ntfslib.api.StreamProvider;

import java.io.DataInputStream;
import java.io.IOException;

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
        if (pos < current) {
            input.close();
            input = new DataInputStream(provider.getStream());
        }

        current = pos;
        input.skipBytes(current);
    }
}