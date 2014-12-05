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

import java.io.IOException;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.api.Filesystem;
import net.alphadev.ntfslib.structures.BootSector;
import net.alphadev.ntfslib.structures.ExtendedBpb;
import net.alphadev.ntfslib.util.RamDisk;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

public class BootSectorTest {
    private BlockDevice dev;

    @Before
    public void init() throws IOException {
        dev = RamDisk.readGzipped(getClass().getResourceAsStream("ntfs.img.gz"));
    }

    @Test
    public void readBootSector() throws IOException {
        BootSector bs = BootSector.read(dev);
        Assert.assertEquals("NTFS    ", bs.getOemId());
    }

    @Test
    public void readPartitionParameters() throws IOException {
        BootSector bs = BootSector.read(dev);
        ExtendedBpb bpb = bs.getBootPartitionParameter();
        Assert.assertEquals(512, bpb.getBytesPerSector());
        Assert.assertEquals(8, bpb.getSectorsPerCluster());
        Assert.assertEquals(1024, bpb.getClustersPerMftRecord());
        Assert.assertEquals(4096, bpb.getClustersPerIndexBuffer());
    }
}
