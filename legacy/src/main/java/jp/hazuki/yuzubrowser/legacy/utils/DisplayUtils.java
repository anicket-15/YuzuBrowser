/*
 * Copyright (C) 2017-2019 Hazuki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.hazuki.yuzubrowser.legacy.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import jp.hazuki.yuzubrowser.legacy.settings.data.AppData;
import jp.hazuki.yuzubrowser.ui.theme.ThemeData;

public class DisplayUtils {
    private DisplayUtils() {
        throw new UnsupportedOperationException();
    }

    public static int getDisplayHeight(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.y;
    }

    public static int getFullScreenVisibility() {
        switch (AppData.fullscreen_hide_mode.get()) {
            case 0:
            default:
                return View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | ThemeData.getSystemUiVisibilityFlag();
            case 1:
                return View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | ThemeData.getSystemUiVisibilityFlag();
            case 2:
                return View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | ThemeData.getSystemUiVisibilityFlag();
        }
    }

    public static boolean isNeedFullScreenFlag() {
        return AppData.fullscreen_hide_mode.get() != 1;
    }
}
