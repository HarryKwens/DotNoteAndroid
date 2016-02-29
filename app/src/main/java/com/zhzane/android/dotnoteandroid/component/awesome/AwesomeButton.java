package com.zhzane.android.dotnoteandroid.component.awesome;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import com.zhzane.android.dotnoteandroid.plugins.fonts.FontAwesome;

/**
 * Created by zhzane on 16/2/29.
 */
public class AwesomeButton extends Button {

    public AwesomeButton(Context context) {
        this(context, null);
    }

    public AwesomeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(FontAwesome.getFontAwesome(context.getAssets()).getTypeface());
    }

}
