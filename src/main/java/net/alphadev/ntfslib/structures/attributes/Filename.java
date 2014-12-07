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

import net.alphadev.ntfslib.util.AbsoluteDataStream;

import java.util.EnumSet;

public class Filename {
    public static final byte PARENT_DIRECTORY_OFFSET = 0x0;
    public static final byte CREATION_TIME_OFFSET = 0x08;
    public static final byte ALTERATION_TIME_OFFSET = 0x10;
    public static final byte MFT_CHANGE_TIME_OFFSET = 0x18;
    public static final byte READ_TIME_OFFSET = 0x20;
    public static final byte ALLOCATED_SIZE_OFFSET = 0x28;
    public static final byte REAL_SIZE_OFFSET = 0x30;
    public static final byte FLAGS_OFFSET = 0x38;
    public static final byte EA_REPARSE_OFFSET = 0x3c;
    public static final byte FILENAME_LENGTH_CHARACTER = 0x40;
    public static final byte FILENAME_NAMESPACE = 0x41;
    public static final byte FILENAME_LENGTH_UNICODE = 0x42;

    private long parentDirectory;
    private long cTime;
    private long aTime;
    private long mTime;
    private long rTime;
    private long allocatedFileSize;
    private long realFileSize;
    private EnumSet<FileFlag> flags;
    private byte filenameNamespace;
    private String filenameUnicode;

    public Filename(Attribute attr) {
        final AbsoluteDataStream bb = new AbsoluteDataStream(attr.getPayload());
        parentDirectory = bb.getLong(PARENT_DIRECTORY_OFFSET);
        cTime = Attribute.parseTimestamp(bb, CREATION_TIME_OFFSET);
        aTime = Attribute.parseTimestamp(bb, ALTERATION_TIME_OFFSET);
        mTime = Attribute.parseTimestamp(bb, MFT_CHANGE_TIME_OFFSET);
        rTime = Attribute.parseTimestamp(bb, READ_TIME_OFFSET);
        allocatedFileSize = bb.getLong(ALLOCATED_SIZE_OFFSET);
        realFileSize = bb.getLong(REAL_SIZE_OFFSET);
        flags = FileFlag.parse(bb.getInt(FLAGS_OFFSET));
    }

    public long getParentDirectory() {
        return parentDirectory;
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

    public long getAllocatedFileSize() {
        return allocatedFileSize;
    }

    public long getRealFileSize() {
        return realFileSize;
    }

    public EnumSet<FileFlag> getFlags() {
        return flags;
    }
}
