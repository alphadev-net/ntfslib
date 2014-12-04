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

public enum AttributeType {
    STANDARD_INFORMATION((byte) 0x10),
    ATTRIBUTE_LIST((byte) 0x20),
    FILE_NAME((byte) 0x30),

    /**
     * WinNt
     */
    VOLUME_VERSION((byte) 0x40),

    /**
     * Win2k
     */
    OBJECT_ID((byte) 0x40),
    SECURITY_DESCRIPTOR((byte) 0x50),
    VOLUME_NAME((byte) 0x60),
    VOLUME_INFORMATION((byte) 0x70),
    DATA((byte) 0x80),
    INDEX_ROOT((byte) 0x90),
    INDEX_ALLOCATION((byte) 0xa0),
    BITMAP((byte) 0xb0),

    /**
     * WinNt
     */
    SYMBOLIC_LINK((byte) 0xc0),

    /**
     * Win2k
     */
    REPARSE_POINT((byte) 0xc0),
    EA_INFORMATION((byte) 0xd0),
    EA((byte) 0xe0),

    /**
     * WinNt
     */
    PROPERTY_SET((byte) 0xf0),

    /**
     * Win2k
     */
    LOGGED_UTILITY_STREAM((byte) 0x100);

    private byte value;

    AttributeType(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
