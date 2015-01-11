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

public enum AttributeType {
    STANDARD_INFORMATION(0x10),
    ATTRIBUTE_LIST(0x20),
    FILE_NAME(0x30),
    OBJECT_ID(0x40),
    SECURITY_DESCRIPTOR(0x50),
    VOLUME_NAME(0x60),
    VOLUME_INFORMATION(0x70),
    DATA(0x80),
    INDEX_ROOT(0x90),
    INDEX_ALLOCATION(0xa0),
    BITMAP(0xb0),
    REPARSE_POINT(0xc0),
    EA_INFORMATION(0xd0),
    EA(0xe0),
    LOGGED_UTILITY_STREAM(0x100),

    @Deprecated
    SYMBOLIC_LINK(0xc0),
    @Deprecated
    PROPERTY_SET(0xf0),
    @Deprecated
    VOLUME_VERSION(0x40);

    private short value;

    AttributeType(int value) {
        this.value = (short) value;
    }

    public static AttributeType parse(int value) {
        for (AttributeType type : AttributeType.values()) {
            if ((value & 0xffff) == type.getValue()) {
                return type;
            }
        }
        throw new IllegalArgumentException("Could not find attribute type (" + value + ")");
    }

    public short getValue() {
        return value;
    }
}
