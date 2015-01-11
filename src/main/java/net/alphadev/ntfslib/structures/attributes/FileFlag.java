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
package net.alphadev.ntfslib.structures.attributes;

import java.util.ArrayList;
import java.util.EnumSet;

public enum FileFlag {
    READ_ONLY(0x0001),
    HIDDEN(0x0002),
    SYSTEM(0x0004),
    ARCHIVE(0x0020),
    DEVICE(0x0040),
    NORMAL(0x0080),
    TEMPORARY(0x0100),
    SPARSE_FILE(0x0200),
    REPARSE_POINT(0x0400),
    COMPRESSED(0x0800),
    OFFLINE(0x1000),
    NOT_CONTENT_INDEXED(0x2000),
    ENCRYPTED(0x4000),
    DIRECTORY(0x10000000),
    INDEX_VIEW(0x20000000);

    private int value;

    FileFlag(int value) {
        this.value = value;
    }

    public static EnumSet<FileFlag> parse(int value) {
        ArrayList<FileFlag> retval = new ArrayList<>();

        for (FileFlag flag : FileFlag.values()) {
            if ((value & flag.get()) == flag.get()) {
                retval.add(flag);
            }
        }

        return EnumSet.copyOf(retval);
    }

    public int get() {
        return value;
    }
}
