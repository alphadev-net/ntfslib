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

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.api.Directory;
import net.alphadev.ntfslib.api.Entry;
import net.alphadev.ntfslib.api.Filesystem;
import net.alphadev.ntfslib.util.RamDisk;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FileNameTest {
    private BlockDevice dev;

    @Before
    public void init() throws IOException {
        dev = RamDisk.readGzipped(getClass().getResourceAsStream("ntfs.img.gz"));
    }

    @Test
    public void readFolderName() throws IOException {
        Filesystem ntfs = new NtfsFilesystem(dev);
        Directory root = ntfs.getRoot();
        Entry folder = root.getEntry("Test");
        Assert.assertNull(root.getParent());
        Assert.assertEquals(".", root.getName());
        Assert.assertEquals("Test", folder.getName());
        Assert.assertEquals(root, folder.getParent());
    }
}
