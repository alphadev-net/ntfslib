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
import net.alphadev.ntfslib.api.Filesystem;
import net.alphadev.ntfslib.util.RamDisk;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class VolumeNameTest {
    private BlockDevice dev;

    @Test
    public void readVolumeName() throws IOException {
        dev = RamDisk.readGzipped(getClass().getResourceAsStream("ntfs.img.gz"));
        Filesystem ntfs = new NtfsFilesystem(dev);
        String label = ntfs.getVolumeName();
        Assert.assertEquals("Bla", label);
    }
}
