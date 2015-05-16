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
package net.alphadev.ntfslib;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.api.Directory;
import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.api.Filesystem;
import net.alphadev.ntfslib.util.RamDisk;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import java.io.IOException;

public class FileNameTest {
    private BlockDevice dev;
    private Filesystem instance;

    @Before
    public void init() throws IOException {
        dev = RamDisk.readGzipped(getClass().getResourceAsStream("ntfs.img.gz"));
        instance = new NtfsFilesystem(dev);
    }

    @Test
    public void checkRoot() throws IOException {
        Directory root = instance.getRoot();

        Assert.assertNull(root.getParent());
        Assert.assertEquals(".", root.getName());
    }

    @Test
    public void readFolderName() {
        Directory root = instance.getRoot(); // 0x15C00 offset - 0x4000 baseoffset
        Entry folder = root.getEntry("Test");

        Assert.assertEquals("Test", folder.getName());
        Assert.assertEquals(root, folder.getParent());
    }
}
