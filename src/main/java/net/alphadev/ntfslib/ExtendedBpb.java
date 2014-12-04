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

import java.nio.ByteBuffer;

public class ExtendedBpb extends BiosParameterBlock {
    public static final byte TOTAL_SECTORS_OFFSET = 0x28;
    public static final byte MFT_LOGICAL_CLUSTER_OFFSET = 0x30;
    public static final byte MFT_MIRROR_LOGICAL_CLUSTER_OFFSET = 0x38;
    public static final byte CLUSTERS_PER_MFT_RECORD = 0x40;
    public static final byte FLAGS_OFFSET = 0x41;
    public static final byte CLUSTERS_PER_INDEX_BUFFER_OFFSET = 0x44;
    public static final byte VOLUME_SERIAL_NUMBER_OFFSET = 0x48;

    private long totalSectors;
    private long mftLogicalCluster;
    private long mftMirrorLogicalCluster;
    private long volumeSerialNumber;
    private byte clustersPerMftRecord;
    private byte clustersPerIndexBuffer;

    public ExtendedBpb(ByteBuffer bb) {
        super(bb);

        totalSectors = bb.getLong(TOTAL_SECTORS_OFFSET);
        mftLogicalCluster = bb.getLong(MFT_LOGICAL_CLUSTER_OFFSET);
        mftMirrorLogicalCluster = bb.getLong(MFT_MIRROR_LOGICAL_CLUSTER_OFFSET);
        clustersPerMftRecord = bb.get(CLUSTERS_PER_MFT_RECORD);
        clustersPerIndexBuffer = bb.get(CLUSTERS_PER_INDEX_BUFFER_OFFSET);
        volumeSerialNumber = bb.getLong(VOLUME_SERIAL_NUMBER_OFFSET);
    }

    public long getTotalSectors() {
        return totalSectors;
    }

    public long getMftLogicalCluster() {
        return mftLogicalCluster;
    }

    public long getMftMirrorLogicalCluster() {
        return mftMirrorLogicalCluster;
    }

    public byte getClustersPerMftRecord() {
        return clustersPerMftRecord;
    }

    public byte getClustersPerIndexBuffer() {
        return clustersPerIndexBuffer;
    }

    public long getVolumeSerialNumber() {
        return volumeSerialNumber;
    }
}
