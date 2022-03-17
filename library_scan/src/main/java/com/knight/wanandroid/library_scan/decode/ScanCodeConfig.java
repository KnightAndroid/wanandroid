package com.knight.wanandroid.library_scan.decode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.knight.wanandroid.library_scan.R;
import com.knight.wanandroid.library_scan.entity.ScanCodeEntity;
import com.knight.wanandroid.library_scan.utils.QrCodeUtil;

import androidx.fragment.app.Fragment;

public class ScanCodeConfig {
    public static final int QUESTCODE = 0x001;
    public static final String CODE_KEY = "code";
    public static final String MODEL_KEY = "model";

    private Activity mActivity;
    private Fragment mFragment;
    private ScanCodeEntity model;

    public ScanCodeConfig(Builder builder){
        this.mActivity = builder.mActivity;
        this.mFragment = builder.mFragment;
        this.model = new ScanCodeEntity(builder.mActivity,builder.mFragment);
        this.model.setPlayAudio(builder.playAudio);
        this.model.setStyle(builder.style);
    }


    public static ScanCodeEntity create(Activity mActivity){
        return new ScanCodeEntity(mActivity);
    }

    public static ScanCodeEntity create(Fragment mFragment){
        return new ScanCodeEntity(mFragment);
    }


    public static class Builder{
        private int style;
        private boolean playAudio;
        private Activity mActivity;
        private Fragment mFragment;
         public Builder setStyle(int style){
             this.style = style;
             return this;
         }

         public Builder setPlayAudio(boolean playAudio){
             this.playAudio = playAudio;
             return this;
         }

         public Builder setActivity(Activity mActivity){
             this.mActivity = mActivity;
             return this;

         }

         public Builder setFragment(Fragment fragment){
             this.mFragment = fragment;
             return this;

         }
        public ScanCodeConfig build() {
            return new ScanCodeConfig(this);
        }
    }

    public static ScanCodeEntity create(Activity mActivity, Fragment mFragment){
        return new ScanCodeEntity(mActivity, mFragment);
    }

    public void start(Class mClass){
        if(mFragment != null){
            Intent intent = new Intent(mActivity, mClass);
            intent.putExtra(MODEL_KEY, model);
            mFragment.startActivityForResult(intent, QUESTCODE);
            mActivity.overridePendingTransition(R.anim.base_bottom_in,R.anim.base_bottom_slient);
        }else{
            Intent intent = new Intent(mActivity, mClass);
            intent.putExtra(MODEL_KEY, model);
            mActivity.startActivityForResult(intent, QUESTCODE);
        }
    }

    public static Bitmap createQRCode(String text){
        return QrCodeUtil.createQRCode(text);
    }

    public static Bitmap createQRCode(String text, int size) {
        return QrCodeUtil.createQRCode(text, size);
    }

    public static Bitmap createQRcodeWithLogo(String text, Bitmap logo){
        return QrCodeUtil.createQRcodeWithLogo(text, logo);
    }

    public static Bitmap createQRcodeWithLogo(String text, int size, Bitmap logo, int logoWith, int logoHigh, float logoRaduisX, float logoRaduisY){
        return QrCodeUtil.createQRcodeWithLogo(text, size, logo, logoWith, logoHigh, logoRaduisX, logoRaduisY);
    }

    public static Bitmap createQRcodeWithStrokLogo(String text, int size, Bitmap logo, int logoWith, int logoHigh, float logoRaduisX, float logoRaduisY, int storkWith, int storkColor){
        return QrCodeUtil.createQRcodeWithStrokLogo(text, size, logo, logoWith, logoHigh, logoRaduisX, logoRaduisY, storkWith, storkColor);
    }

    public static Bitmap createBarcode(String content, int widthPix, int heightPix, boolean isShowContent){
        return QrCodeUtil.createBarcode(content, widthPix, heightPix, isShowContent);
    }

    public static String scanningImage(Activity mActivity, Uri uri) {
        return QrCodeUtil.scanningImage(mActivity, uri);
    }

    public static String scanningImageByBitmap(Bitmap bitmap){
        return QrCodeUtil.scanningImageByBitmap(bitmap);
    }
}
