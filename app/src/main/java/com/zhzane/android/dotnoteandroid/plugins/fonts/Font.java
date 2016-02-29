package com.zhzane.android.dotnoteandroid.plugins.fonts;

import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by zhzane on 16/2/28.
 */
public abstract class Font {
    private static AssetManager assetManager;

    public abstract Typeface getTypeface();
}
