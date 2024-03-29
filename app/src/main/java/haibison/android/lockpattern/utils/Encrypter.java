/*
 *   Copyright 2012 Hai Bison
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package haibison.android.lockpattern.utils;

import android.content.Context;

import java.util.List;

import haibison.android.lockpattern.widget.LockPatternView.Cell;

import androidx.annotation.NonNull;

/**
 * Interface for encrypter.
 *
 * @author Hai Bison
 * @since v2 beta
 */
public interface Encrypter {

    /**
     * Encrypts {@code pattern}.
     *
     * @param context the context.
     * @param pattern the pattern in the form of a list of {@link Cell}.
     * @return the encrypted char array of the pattern.
     * @since v2.1 beta
     */
    @NonNull
    char[] encrypt(@NonNull Context context, @NonNull List<Cell> pattern);

    /**
     * Decrypts an encrypted pattern.
     *
     * @param context          the context.
     * @param encryptedPattern the encrypted pattern.
     * @return the original pattern.
     */
    @NonNull
    List<Cell> decrypt(@NonNull Context context, @NonNull char[] encryptedPattern);

}