package com.zhzane.android.dotnoteandroid.plugins.fonts;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by zhzane on 16/2/28.
 */
public class FontAwesome extends Font {
    private static FontAwesome fontAwesome = null;
    private static final String fontPath = "fonts/fontawesome-webfont.ttf";

    private static final String FONT_TAG = "fonts.FontAwesome";


    private AssetManager assetManager;
    private Typeface typeface;

    private FontAwesome(AssetManager assetManager) {
        this.assetManager = assetManager;
        if (assetManager != null) {
            try {
                this.typeface = Typeface.createFromAsset(assetManager, fontPath);
            } catch (Exception e) {
                Log.d(FONT_TAG, "FontAwesome: Typeface can't be created");
            }
        }
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public static FontAwesome getFontAwesome(AssetManager assetManager) {
        if (fontAwesome == null) {
            fontAwesome = new FontAwesome(assetManager);
        }
        return fontAwesome;
    }
}
