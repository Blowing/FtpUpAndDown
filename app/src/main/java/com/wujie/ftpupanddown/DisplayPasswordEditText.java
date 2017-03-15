package com.wujie.ftpupanddown;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class DisplayPasswordEditText extends EditText {
    private Drawable[] mDrawables;
    private Drawable mRightDrawable;
    private Rect mRightDrawableBounds;
    private boolean mDisplayPassword = false;

    public DisplayPasswordEditText(Context context) {
        super(context);
        init();
    }

    public DisplayPasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initDisplay(context, attrs);
    }

    public DisplayPasswordEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        initDisplay(context, attrs);
    }

    private void initDisplay(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DisplayPasswordEditText);
        mDisplayPassword = a.getBoolean(R.styleable.DisplayPasswordEditText_Display, true);
        if (!mDisplayPassword) {
            setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            Drawable rightDrawable = getResources().getDrawable(R.mipmap.display_edit_icon_eye_on);
            rightDrawable.setBounds(mRightDrawableBounds);
            setCompoundDrawables(mDrawables[0], mDrawables[1], rightDrawable, mDrawables[3]);
        } else {
            setTransformationMethod(PasswordTransformationMethod.getInstance());
            Drawable rightDrawable = getResources().getDrawable(R.mipmap.display_edit_icon_eye_off);
            rightDrawable.setBounds(mRightDrawableBounds);
            setCompoundDrawables(mDrawables[0], mDrawables[1], rightDrawable, mDrawables[3]);
        }
        mDisplayPassword = !mDisplayPassword;
        postInvalidate();
    }

    private void init() {
        //getCompoundDrawables:
        //Returns drawables for the left, top, right, and bottom borders.
        mDrawables = this.getCompoundDrawables();

        //取得right位置的Drawable
        //即我们在布局文件中设置的android:drawableRight
        mRightDrawable = mDrawables[2];
        mRightDrawableBounds = mRightDrawable.getBounds();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isDisplay = event.getX() > (getWidth() - getPaddingRight() - mRightDrawable.getIntrinsicWidth()) ;
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (isDisplay) {
                    if (!mDisplayPassword) {
                        setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        Drawable rightDrawable = getResources().getDrawable(R.mipmap.display_edit_icon_eye_on);
                        rightDrawable.setBounds(mRightDrawableBounds);
                        setCompoundDrawables(mDrawables[0], mDrawables[1], rightDrawable, mDrawables[3]);
                    } else {
                        setTransformationMethod(PasswordTransformationMethod.getInstance());
                        Drawable rightDrawable = getResources().getDrawable(R.mipmap.display_edit_icon_eye_off);
                        rightDrawable.setBounds(mRightDrawableBounds);
                        setCompoundDrawables(mDrawables[0], mDrawables[1], rightDrawable, mDrawables[3]);
                    }
                    mDisplayPassword = !mDisplayPassword;
                    postInvalidate();
                }

                break;
            default:
                break;
        }
        if(isDisplay) {
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }
}
