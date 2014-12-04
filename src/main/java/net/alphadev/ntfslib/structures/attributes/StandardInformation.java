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

    private long cTime;
    private long aTime;
    private long mTime;
    private long rTime;
    private long quota;
    private long updateSequenceNumber;
    private DosPermissions dosPermissions;
    private int maxVersions;
    private int versionNumber;
    private int classId;
    private int ownerId;
    private int securityId;

    public StandardInformation(ByteBuffer bb) {
        cTime = parseTimestamp(bb, CREATION_TIME_OFFSET);
        aTime = parseTimestamp(bb, ALTERATION_TIME_OFFSET);
        mTime = parseTimestamp(bb, MFT_CHANGE_TIME_OFFSET);
        rTime = parseTimestamp(bb, READ_TIME_OFFSET);
        dosPermissions = parseDosPermissions(bb, DOS_FILE_PERMISSIONS_OFFSET);
        maxVersions = bb.getInt(MAX_VERSIONS_OFFSET);
        versionNumber = bb.getInt(VERSION_NUMBER_OFFSET);
        classId = bb.getInt(CLASS_ID_OFFSET);
        ownerId = bb.getInt(OWNER_ID_OFFSET);
        securityId = bb.getInt(SECURITY_ID_OFFSET);
        quota = bb.getLong(QUOTA_CHARGED_OFFSET);
        updateSequenceNumber = bb.getLong(UPDATE_SEQUENCE_NUMBER_OFFSET);
    }

    private DosPermissions parseDosPermissions(ByteBuffer bb, int offset) {
        return new DosPermissions();
    }

    public long getFileCreationTime() {
        return cTime;
    }

    public long getFileModificationTime() {
        return aTime;
    }

    public long getMftModificationTime() {
        return mTime;
    }

    public long getFileAccessTime() {
        return rTime;
    }

    public DosPermissions getDosPermissions() {
        return dosPermissions;
    }

    public int getMaxVersions() {
        return maxVersions;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public int getClassId() {
        return classId;
    }

    /**
     * Owner Id
     * Win2k
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Security Id
     * Win2k
     */
    public int getSecurityId() {
        return securityId;
    }

    /**
     * Quota Charged
     * Win2k
     */
    public long getQuotaCharged() {
        return quota;
    }

    /**
     * Update Sequence Number (USN)
     * Win2k
     */
    public long getUpdateSequenceNumber() {
        return updateSequenceNumber;
    }
}
