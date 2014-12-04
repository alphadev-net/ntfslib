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
    /**
     * $STANDARD_INFORMATION = 0x10
     */
    STANDARD_INFORMATION,

    /**
     * $ATTRIBUTE_LIST = 0x20
     */
     ATTRIBUTE_LIST,

    /**
     * $FILE_NAME = 0x30
     */
     FILE_NAME,

    /**
     * $VOLUME_VERSION = 0x40
     * WinNt
     */
     VOLUME_VERSION,

    /**
     * $OBJECT_ID = 0x40
     * Win2k
     */
     OBJECT_ID,

    /**
     * $SECURITY_DESCRIPTOR = 0x50
     */
     SECURITY_DESCRIPTOR,

    /**
     * $VOLUME_NAME = 0x60
     */
     VOLUME_NAME,

    /**
     * $VOLUME_INFORMATION = 0x70
     */
     VOLUME_INFORMATION,

    /**
     * $DATA = 0x80
     */
     DATA,

    /**
     * $INDEX_ROOT = 0x90
     */
     INDEX_ROOT,

    /**
     * $INDEX_ALLOCATION = 0xa0
     */
     INDEX_ALLOCATION,

    /**
     * $BITMAP = 0xb0
     */
     BITMAP,

    /**
     * $SYMBOLIC_LINK = 0xc0
     * WinNt
     */
     SYMBOLIC_LINK,

    /**
     * $REPARSE_POINT = 0xc0
     * Win2k
     */
     REPARSE_POINT,

    /**
     * $EA_INFORMATION = 0xd0
     */
     EA_INFORMATION,

    /**
     * $EA = 0xe0
     */
     EA,

    /**
     * $PROPERTY_SET = 0xf0
     * WinNt
     */
     PROPERTY_SET,

    /**
     * $LOGGED_UTILITY_STREAM = 0x100
     * Win2k
     */
     LOGGED_UTILITY_STREAM
}
