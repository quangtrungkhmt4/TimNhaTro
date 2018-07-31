package com.example.quang.timnhatro.object;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class TextBold extends TextView {
    public TextBold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public TextBold(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public TextBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    @SuppressLint("NewApi")
    public TextBold(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Bold.ttf");
        setTypeface(custom_font);
    }
}
