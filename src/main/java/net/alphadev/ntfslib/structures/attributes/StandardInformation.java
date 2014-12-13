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
package net.alphadev.ntfslib.structures.attributes;

import net.alphadev.ntfslib.util.TimeConversionUtil;

import java.nio.ByteBuffer;

public class StandardInformation extends Attribute {
    public static final byte CREATION_TIME_OFFSET = 0x00;
    public static final byte ALTERATION_TIME_OFFSET = 0x08;
    public static final byte MFT_CHANGE_TIME_OFFSET = 0x10;
    public static final byte READ_TIME_OFFSET = 0x18;
    public static final byte DOS_FILE_PERMISSIONS_OFFSET = 0x20;
    public static final byte MAX_VERSIONS_OFFSET = 0x24;
    public static final byte VERSION_NUMBER_OFFSET = 0x28;
    public static final byte CLASS_ID_OFFSET = 0x2c;
    public static final byte OWNER_ID_OFFSET = 0x30;
    public static final byte SECURITY_ID_OFFSET = 0x34;
    public static final byte QUOTA_CHARGED_OFFSET = 0x38;
    public static final byte UPDATE_SEQUENCE_NUMBER_OFFSET = 0x40;

    private ByteBuffer bb;

    public StandardInformation(ByteBuffer data) {
        super(data);
        bb = getPayloadBuffer();
    }

    private DosPermissions parseDosPermissions(ByteBuffer bb, int offset) {
        return new DosPermissions();
    }

    public long getFileCreationTime() {
        return TimeConversionUtil.parseTimestamp(bb, CREATION_TIME_OFFSET);
    }

    public long getFileModificationTime() {
        return TimeConversionUtil.parseTimestamp(bb, ALTERATION_TIME_OFFSET);
    }

    public long getMftModificationTime() {
        return TimeConversionUtil.parseTimestamp(bb, MFT_CHANGE_TIME_OFFSET);
    }

    public long getFileAccessTime() {
        return TimeConversionUtil.parseTimestamp(bb, READ_TIME_OFFSET);
    }

    public DosPermissions getDosPermissions() {
        return parseDosPermissions(bb, DOS_FILE_PERMISSIONS_OFFSET);
    }

    public int getMaxVersions() {
        return bb.getInt(MAX_VERSIONS_OFFSET);
    }

    public int getVersionNumber() {
        return bb.getInt(VERSION_NUMBER_OFFSET);
    }

    public int getClassId() {
        return bb.getInt(CLASS_ID_OFFSET);
    }

    /**
     * Owner Id
     * Win2k
     */
    public int getOwnerId() {
        return bb.getInt(OWNER_ID_OFFSET);
    }

    /**
     * Security Id
     * Win2k
     */
    public int getSecurityId() {
        return bb.getInt(SECURITY_ID_OFFSET);
    }

    /**
     * Quota Charged
     * Win2k
     */
    public long getQuotaCharged() {
        return bb.getLong(QUOTA_CHARGED_OFFSET);
    }

    /**
     * Update Sequence Number (USN)
     * Win2k
     */
    public long getUpdateSequenceNumber() {
        return bb.getLong(UPDATE_SEQUENCE_NUMBER_OFFSET);
    }
}
