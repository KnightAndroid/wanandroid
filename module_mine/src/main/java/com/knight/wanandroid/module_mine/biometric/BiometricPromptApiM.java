package com.knight.wanandroid.module_mine.biometric;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.util.Base64;
import android.util.Log;

import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.module_mine.fragment.BiometricPromptDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/9/15 17:45
 * @descript:
 */
@RequiresApi(api = Build.VERSION_CODES.M)
public class BiometricPromptApiM implements IBiometricPromptImpl {
    private static final String TAG = "BiometricPromptApi23";
    private FragmentActivity mActivity;
    private BiometricPromptDialog mDialog;
    private FingerprintManager mFingerprintManager;
    private CancellationSignal mCancellationSignal;
    private BiometricPromptManager.OnBiometricIdentifyCallback mManagerIdentifyCallback;
    private FingerprintManager.AuthenticationCallback mFmAuthCallback = new FingerprintManageCallbackImpl();

    @RequiresApi(api = Build.VERSION_CODES.M)
    public BiometricPromptApiM(FragmentActivity activity) {
        mActivity = activity;
        mFingerprintManager = getFingerprintManager(activity);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void authenticate(boolean loginFlg, @Nullable CancellationSignal cancel,
                             @NonNull BiometricPromptManager.OnBiometricIdentifyCallback callback) {
        //指纹识别的回调
        mManagerIdentifyCallback = callback;

        mDialog = BiometricPromptDialog.newInstance();
        mDialog.setOnBiometricPromptDialogActionCallback(new BiometricPromptDialog.OnBiometricPromptDialogActionCallback() {
            @Override
            public void onDialogDismiss() {
                //当dialog消失的时候，包括点击userPassword、点击cancel、和识别成功之后
                if (mCancellationSignal != null && !mCancellationSignal.isCanceled()) {
                    mCancellationSignal.cancel();
                }
            }

            @Override
            public void onCancel() {
                //点击cancel键
                if (mManagerIdentifyCallback != null) {
                    mManagerIdentifyCallback.onCancel();
                }
            }
        });
        mDialog.show(mActivity.getSupportFragmentManager(), "BiometricPromptApiM");

        mCancellationSignal = cancel;
        if (mCancellationSignal == null) {
            mCancellationSignal = new CancellationSignal();
        }
        mCancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
            @Override
            public void onCancel() {
                mDialog.dismiss();
            }
        });

        KeyGenTool mKeyGenTool = new KeyGenTool(mActivity);
        FingerprintManager.CryptoObject object;
        if (loginFlg){
            //解密
            try {
                /**
                 * 可通过服务器保存iv,然后在使用之前从服务器获取
                 */
                String ivStr = CacheUtils.getInstance().getCliperIv();
                byte[] iv = Base64.decode(ivStr, Base64.URL_SAFE);

                object = new FingerprintManager.CryptoObject(mKeyGenTool.getDecryptCipher(iv));
                getFingerprintManager(mActivity).authenticate(object, mCancellationSignal,
                        0, mFmAuthCallback, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //加密
            try {
                object = new FingerprintManager.CryptoObject(mKeyGenTool.getEncryptCipher());
                getFingerprintManager(mActivity).authenticate(object, mCancellationSignal,
                        0, mFmAuthCallback, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private class FingerprintManageCallbackImpl extends FingerprintManager.AuthenticationCallback {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            mDialog.setState(BiometricPromptDialog.STATE_ERROR);
            mManagerIdentifyCallback.onError(errorCode, errString.toString());
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();

            mDialog.setState(BiometricPromptDialog.STATE_FAILED);
            mManagerIdentifyCallback.onFailed();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            Log.d(TAG, "onAuthenticationHelp() called with: helpCode = [" + helpCode + "], helpString = [" + helpString + "]");
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            mDialog.setState(BiometricPromptDialog.STATE_SUCCEED);
            mManagerIdentifyCallback.onSucceeded(result);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private FingerprintManager getFingerprintManager(Context context) {
        if (mFingerprintManager == null) {
            mFingerprintManager = context.getSystemService(FingerprintManager.class);
        }
        return mFingerprintManager;
    }


}
