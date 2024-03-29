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

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

import androidx.annotation.NonNull;

import static haibison.android.lockpattern.Alp.TAG;
import static haibison.android.lockpattern.BuildConfig.DEBUG;

/**
 * UI utilities.
 *
 * @author Hai Bison
 */
public class UI {

    // Singleton class
    private UI() {}

    private static final String CLASSNAME = UI.class.getName();

    /**
     * The screen sizes.
     */
    public enum ScreenSize {
        /**
         * Small.
         */
        SMALL(1, 1, 1, 1),

        /**
         * Normal.
         */
        NORMAL(1, 1, 1, 1),

        /**
         * Large.
         */
        LARGE(.6f, .9f, .6f, .9f),

        /**
         * X-Large.
         */
        XLARGE(.6f, .9f, .5f, .7f),

        /**
         * Undefined.
         */
        UNDEFINED(1, 1, 1, 1);

        /**
         * The desired fixed width for a dialog along the minor axis (the screen is in portrait). This is a fraction.
         */
        public final float fixedWidthMinor;

        /**
         * The desired fixed width for a dialog along the major axis (the screen is in landscape). This is a fraction.
         */
        public final float fixedWidthMajor;

        /**
         * The desired fixed height for a dialog along the minor axis (the screen is in landscape). This is a fraction.
         */
        public final float fixedHeightMinor;

        /**
         * The desired fixed height for a dialog along the major axis (the screen is in portrait). This is a fraction.
         */
        public final float fixedHeightMajor;

        ScreenSize(float fixedHeightMajor, float fixedHeightMinor, float fixedWidthMajor, float fixedWidthMinor) {
            this.fixedHeightMajor = fixedHeightMajor;
            this.fixedHeightMinor = fixedHeightMinor;
            this.fixedWidthMajor = fixedWidthMajor;
            this.fixedWidthMinor = fixedWidthMinor;
        }//ScreenSize()

        /**
         * Gets current screen size.
         *
         * @param context the context.
         * @return current screen size.
         */
        @NonNull
        public static ScreenSize getCurrent(@NonNull Context context) {
            switch (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL: return SMALL;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL: return NORMAL;
            case Configuration.SCREENLAYOUT_SIZE_LARGE: return LARGE;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE: return XLARGE;
            }

            return UNDEFINED;
        }//getCurrent()

    }//ScreenSize

    /**
     * Uses a fixed size for {@code dialog} in large screens.
     *
     * @param dialog the dialog.
     */
    public static void adjustDialogSizeForLargeScreens(@NonNull Dialog dialog) {
        adjustDialogSizeForLargeScreens(dialog.getWindow());
    }//adjustDialogSizeForLargeScreens()

    /**
     * Uses a fixed size for {@code dialogWindow} in large screens.
     *
     * @param dialogWindow the window <i>of the dialog</i>.
     */
    public static void adjustDialogSizeForLargeScreens(@NonNull Window dialogWindow) {
        if (DEBUG) Log.d(TAG, CLASSNAME + "#adjustDialogSizeForLargeScreens(Window)");

        if (dialogWindow.isFloating() == false) return;

        final ScreenSize screenSize = ScreenSize.getCurrent(dialogWindow.getContext());
        switch (screenSize) {
        case LARGE: case XLARGE: break;
        default: return;
        }

        final DisplayMetrics metrics = dialogWindow.getContext().getResources().getDisplayMetrics();
        final boolean isPortrait = metrics.widthPixels < metrics.heightPixels;

        int width = metrics.widthPixels;// dialogWindow.getDecorView().getWidth();
        int height = metrics.heightPixels;// dialogWindow.getDecorView().getHeight();
        if (DEBUG) Log.d(TAG, String.format("width = %,d | height = %,d", width, height));

        width = (int) (width * (isPortrait ? screenSize.fixedWidthMinor : screenSize.fixedWidthMajor));
        height = (int) (height * (isPortrait ? screenSize.fixedHeightMajor : screenSize.fixedHeightMinor));

        if (DEBUG) Log.d(TAG, String.format("NEW >>> width = %,d | height = %,d", width, height));
        dialogWindow.setLayout(width, height);
    }//adjustDialogSizeForLargeScreens()

}