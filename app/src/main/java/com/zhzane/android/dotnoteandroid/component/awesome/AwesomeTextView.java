package com.zhzane.android.dotnoteandroid.component.awesome;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zhzane.android.dotnoteandroid.plugins.fonts.FontAwesome;

/**
 * Created by zhzane on 16/2/29.
 */
public class AwesomeTextView extends TextView {

    public AwesomeTextView(Context context) {
        this(context, null);
    }

    public AwesomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontAwesome.getFontAwesome(context.getAssets()).getTypeface());
    }
}
