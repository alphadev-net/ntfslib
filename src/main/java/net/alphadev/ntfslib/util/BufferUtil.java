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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtil {
    public static ByteBuffer copy(ByteBuffer origin, int start, int end) {
        ByteOrder order = origin.order();
        ByteBuffer copy = origin.duplicate();
        copy.position(start);
        copy.limit(end);
        copy = copy.slice();
        copy.order(order);
        return copy;
    }

    public static ByteBuffer copy(ByteBuffer origin, int start) {
        return copy(origin, start, origin.limit());
    }

    public static ByteBuffer copyFor(ByteBuffer origin, int start, int length) {
        return copy(origin, start, start + length);
    }

    public static String readAsciiString(ByteBuffer buffer, int offset, int length) {
        return _readString(buffer, offset, length, 1);
    }

    public static String readString(ByteBuffer buffer, int offset, int length) {
        return _readString(buffer, offset, length, 2);
    }

    private static String _readString(ByteBuffer buffer, int offset, int length, int step) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i += step) {
            int curOffset = offset + i;

            char character;
            if (step == 1 ) {
                character = (char) buffer.get(curOffset);
            } else {
                character = buffer.getChar(curOffset);
            }
            sb.append(character);
        }

        return sb.toString();
    }
}