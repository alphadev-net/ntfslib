/**
 * Copyright © 2014 Jan Seeger
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
package net.alphadev.ntfslib;

import net.alphadev.ntfslib.util.BufferUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

public class BufferUtilTest {

    @Test
    public void convertsSimpleString() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(
            new byte[]{ 0x0, 0x74, 0x0, 0x65, 0x0, 0x73, 0x0, 0x74 });

        String result = BufferUtil.readString(buffer, 0, 8);
        Assert.assertEquals("test", result);
    }

    @Test
    public void convertsSimpleASCIIString() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(
            new byte[]{ 0x74, 0x65, 0x73, 0x74 });

        String result = BufferUtil.readAsciiString(buffer, 0, 4);
        Assert.assertEquals("test", result);
    }

    @Test
    public void convertsSimpleASCIIStringWithBoundaries() throws IOException {
        final ByteBuffer buffer = ByteBuffer.wrap(
            new byte[]{ 0x11, 0x74, 0x65, 0x73, 0x74, 0x11 });

        String result = BufferUtil.readAsciiString(buffer, 1, 4);
        Assert.assertEquals("test", result);
    }

    @Test
    public void copyTruncatesCorrectly() {
        final ByteBuffer buffer = ByteBuffer.wrap(
            new byte[]{ 0x11, 0x74, 0x65, 0x73, 0x74, 0x11 });
        final ByteBuffer expected  = ByteBuffer.wrap(
            new byte[]{ 0x74, 0x65, 0x73, 0x74, 0x11 });

        final ByteBuffer result = BufferUtil.copy(buffer, 1);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void copyTruncatesCorrectlyBothEnds() {
        final ByteBuffer buffer = ByteBuffer.wrap(
            new byte[]{ 0x11, 0x74, 0x65, 0x73, 0x74, 0x11 });
        final ByteBuffer expected  = ByteBuffer.wrap(
            new byte[]{ 0x74, 0x65, 0x73, 0x74 });

        final ByteBuffer result = BufferUtil.copy(buffer, 1, 4);

        Assert.assertEquals(expected, result);
    }
}
