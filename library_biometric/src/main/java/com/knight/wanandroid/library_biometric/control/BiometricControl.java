package com.knight.wanandroid.library_biometric.control;

import com.knight.wanandroid.library_biometric.BiometricPromptManager;
import com.knight.wanandroid.library_biometric.R;
import com.knight.wanandroid.library_biometric.utils.BlometricUtils;

import javax.crypto.Cipher;

import androidx.fragment.app.FragmentActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/23 11:38
 * @descript:
 */
public final class BiometricControl {
    public interface BiometricStatusCallback {
        /**
         *
         * 取消或者使用密码
         */
        void onUsePassword();

        /**
         *
         * 验证成功
         */
        void onVerifySuccess(Cipher cipher);

        /**
         *
         * 失败
         */
        void onFailed();

        /**
         *
         * 错误
         * @param code
         * @param reason
         */
        void error(int code, String reason);

        /**
         *
         * 取消
         */
        void onCancel();
    }



    /**
     *
     * 开启指纹解锁
     */
    public static void openBlomtric(FragmentActivity activity,BiometricStatusCallback mBiometricStatusCallback) {
        if (BlometricUtils.isBiometricPromptEnable(activity)) {
            new BiometricPromptManager.Builder()
                    .setActivity(activity)
                    .setTitle(activity.getString(R.string.biometric_touch_verify_finger))
                    .setDesc(activity.getString(R.string.biometric_touch_sensor))
                    .setNegativeText(activity.getString(R.string.biometric_not_open_touchverify))
                    .build()
                    .authenticate(false, new BiometricPromptManager.OnBiometricIdentifyCallback() {
                        @Override
                        public void onUsePassword() {
                            mBiometricStatusCallback.onUsePassword();
                        }

                        @Override
                        public void onSucceeded(Cipher cipher) {
                            mBiometricStatusCallback.onVerifySuccess(cipher);
                        }

                        @Override
                        public void onFailed() {
                            mBiometricStatusCallback.onFailed();
                        }

                        @Override
                        public void onError(int code, String reason) {
                            mBiometricStatusCallback.error(code,reason);
                        }

                        @Override
                        public void onCancel() {
                            mBiometricStatusCallback.onCancel();
                        }
                    });
        } else {
            mBiometricStatusCallback.onFailed();
        }
    }


    /**
     *
     * 通过指纹识别进行登录
     * @param activity
     * @param mBiometricStatusCallback
     */
    public static void loginBlomtric(FragmentActivity activity,BiometricStatusCallback mBiometricStatusCallback) {
        if (BlometricUtils.isBiometricPromptEnable(activity)) {
            new BiometricPromptManager.Builder()
                    .setActivity(activity)
                    .setTitle(activity.getString(R.string.biometric_touch_verify_finger))
                    .setDesc(activity.getString(R.string.biometric_touch_sensor))
                    .setNegativeText(activity.getString(R.string.biometric_use_password_login))
                    .build()
                    .authenticate(true, new BiometricPromptManager.OnBiometricIdentifyCallback() {
                        @Override
                        public void onUsePassword() {
                            mBiometricStatusCallback.onUsePassword();
                        }

                        @Override
                        public void onSucceeded(Cipher cipher) {
                            mBiometricStatusCallback.onVerifySuccess(cipher);
                        }

                        @Override
                        public void onFailed() {
                            mBiometricStatusCallback.onFailed();
                        }

                        @Override
                        public void onError(int code, String reason) {
                            mBiometricStatusCallback.error(code,reason);
                        }

                        @Override
                        public void onCancel() {
                            mBiometricStatusCallback.onCancel();
                        }
                    });
        } else {
            mBiometricStatusCallback.onFailed();
        }
    }




}
