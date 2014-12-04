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
package net.alphadev.ntfslib.structures;

public enum KnownMftEntries {
    MFT_MAIN(0),
    MFT_MIRROR(1),
    LOG(2),
    VOLUME_INFO(3);

    private long value;

    KnownMftEntries(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}