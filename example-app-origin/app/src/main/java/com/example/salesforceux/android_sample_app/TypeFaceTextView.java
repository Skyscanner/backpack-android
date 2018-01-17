package com.example.salesforceux.android_sample_app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class TypeFaceTextView extends TextView {
    private static final String TAG = "TextView";

    public TypeFaceTextView(Context context) {
        super(context);
    }

    public TypeFaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public TypeFaceTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TypeFaceTextView);
        String customFont = a.getString(R.styleable.TypeFaceTextView_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(ctx.getAssets(), asset);
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface: "+e.getMessage());
            return false;
        }

        setTypeface(tf);
        return true;
    }

}