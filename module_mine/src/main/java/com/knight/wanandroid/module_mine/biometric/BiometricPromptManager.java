package com.knight.wanandroid.module_mine.biometric;

import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;

import com.knight.wanandroid.library_util.BlometricUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/15 17:46
 * @descript:
 */
public class BiometricPromptManager {
    private IBiometricPromptImpl mImpl;
    private FragmentActivity mActivity;

    public interface OnBiometricIdentifyCallback {
        void onUsePassword();

        void onSucceeded(FingerprintManager.AuthenticationResult result);

        void onSucceeded(BiometricPrompt.AuthenticationResult result);

        void onFailed();

        void onError(int code, String reason);

        void onCancel();

    }

    public BiometricPromptManager(Builder builder) {
        mActivity = builder.mActivity;
        if (BlometricUtils.isAboveApiP()) {
            mImpl = new BiometricPromptApiP(mActivity,builder);
        } else if (BlometricUtils.isAboveApiM()) {
            mImpl = new BiometricPromptApiM(mActivity);
        }
    }

    public void authenticate(boolean loginFlg, @NonNull OnBiometricIdentifyCallback callback) {
        mImpl.authenticate(loginFlg, new CancellationSignal(), callback);
    }

    public void authenticate(@NonNull CancellationSignal cancel,
                             @NonNull OnBiometricIdentifyCallback callback) {
        // TODO: 2019/1/7
        //        mImpl.authenticate(cancel, callback);
    }


    public static class Builder {
        //标题
        public String mTitle;
        //标题描述
        public String mDesc;
        //取消按钮描述
        public String mNegativeText;
        //activity
        public FragmentActivity mActivity;


        public Builder setTitle(String title) {
            this.mTitle = title;
            return this;
        }

        public Builder setDesc(String desc) {
            this.mDesc = desc;
            return this;
        }

        public Builder setNegativeText(String negativeText) {
            this.mNegativeText = negativeText;
            return this;
        }

        public Builder setActivity(FragmentActivity mActivity) {
            this.mActivity = mActivity;
            return this;
        }

        public BiometricPromptManager build() {
            return new BiometricPromptManager(this);
        }
    }


}
