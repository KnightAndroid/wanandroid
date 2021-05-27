package com.knight.wanandroid.library_util;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/26 18:55
 * @descript:
 */
public class TextClickUtils extends ClickableSpan {



    public interface OnClickToWebListener{
        void goWeb();
    }

    private OnClickToWebListener mOnClickToWebListener;

    public TextClickUtils setOnClickWebListener(OnClickToWebListener mOnClickToWebListener){
         this.mOnClickToWebListener = mOnClickToWebListener;
         return this;
    }



    @Override
    public void onClick(@NonNull View widget) {
        mOnClickToWebListener.goWeb();

    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(Color.parseColor("#55aff4"));

    }


}
