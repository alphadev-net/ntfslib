/*
 * Copyright (C) 2009-2013 Matthias Treydte <mt@waldheinz.de>
 *               2014-2015 Jan Seeger <jan@alphadev.net>
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; If not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package net.alphadev.ntfslib.structures;

import net.alphadev.ntfslib.api.BlockDevice;
import net.alphadev.ntfslib.structures.attributes.VolumeName;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Volume implements BlockDevice {
    private BlockDevice device;
    private BootSector boot;
    private VolumeName volumeName;

    public Volume(BlockDevice device, BootSector boot) {
        this.device = device;
        this.boot = boot;
    }

    @Override
    public long getSize() {
        return device.getSize();
    }

    @Override
    public void read(long devOffset, ByteBuffer dest) throws IOException {
        device.read(devOffset, dest);
    }

    @Override
    public void write(long devOffset, ByteBuffer src) throws IOException {
        device.write(devOffset, src);
    }

    @Override
    public void flush() throws IOException {
        device.flush();
    }

    @Override
    public int getSectorSize() {
        return device.getSectorSize();
    }

    @Override
    public boolean isClosed() {
        return device.isClosed();
    }

    @Override
    public boolean isReadOnly() {
        return device.isReadOnly();
    }

    @Override
    public void close() throws IOException {
        device.close();
    }

    public ExtendedBpb getParameter() {
        return boot.getBootPartitionParameter();
    }

    public long getEntryAddress(long address) {
        final ExtendedBpb parameter = boot.getBootPartitionParameter();
        return address * parameter.getClustersPerMftRecord()
                * parameter.getSectorsPerCluster()
                * parameter.getBytesPerSector();
    }
}
