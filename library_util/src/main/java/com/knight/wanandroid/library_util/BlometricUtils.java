package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/15 18:41
 * @descript:
 */
public class BlometricUtils {
    public static boolean isAboveApiP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    public static boolean isAboveApiM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * Whether the device support biometric.
     * @return
     */
    public static boolean isBiometricPromptEnable(Activity activity) {
        return isAboveApiM()
                && isHardwareDetected(activity)
                && hasEnrolledFingerprints( activity)
                && isKeyguardSecure( activity);
    }


    /**
     * 确定是否至少有一个指纹登记
     * @param activity
     * @return
     */
    public static boolean hasEnrolledFingerprints(Activity activity) {
        if (isAboveApiP()) {
            //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
            final FingerprintManager manager = activity.getSystemService(FingerprintManager.class);
            return manager != null && manager.hasEnrolledFingerprints();
        } else if (isAboveApiM()) {
            return AbovehasEnrolledFingerprints(activity);
        } else {
            return false;
        }
    }

    /**
     *
     * 用来判断系统硬件是否支持指纹识别
     * @param activity
     * @return
     */
    public static boolean isHardwareDetected(Activity activity) {
        if (isAboveApiP()) {
            //TODO 这是Api23的判断方法，也许以后有针对Api28的判断方法
            final FingerprintManager fm = activity.getSystemService(FingerprintManager.class);
            return fm != null && fm.isHardwareDetected();
        } else if (isAboveApiM()) {
            return AboveisHardwareDetected(activity);
        } else {
            return false;
        }
    }

    /**
     *
     * 这个方法是判断系统有没有设置锁屏
     * @param activity
     * @return
     */
    public static boolean isKeyguardSecure(Activity activity) {
        KeyguardManager keyguardManager = (KeyguardManager) activity.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            return true;
        }

        return false;
    }

    /**
     *
     * 高于23判断硬件是否支持
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean AboveisHardwareDetected(Activity activity) {
        return activity.getSystemService(FingerprintManager.class).isHardwareDetected();
    }

    /**
     *
     * 高于23判断
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean AbovehasEnrolledFingerprints(Activity activity) {
        return activity.getSystemService(FingerprintManager.class).hasEnrolledFingerprints();
    }
    
}
