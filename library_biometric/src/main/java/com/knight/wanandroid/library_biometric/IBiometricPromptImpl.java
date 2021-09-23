package com.knight.wanandroid.library_biometric;

import android.os.CancellationSignal;

import androidx.annotation.NonNull;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/15 17:45
 * @descript:
 */
public interface IBiometricPromptImpl {

    void authenticate(boolean loginFlg, @NonNull CancellationSignal cancel,
                      @NonNull BiometricPromptManager.OnBiometricIdentifyCallback callback);

}
