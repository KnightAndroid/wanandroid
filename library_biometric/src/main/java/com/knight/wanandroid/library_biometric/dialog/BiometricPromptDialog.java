package com.knight.wanandroid.library_biometric.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.library_biometric.R;
import com.knight.wanandroid.library_biometric.databinding.BiometricLoginDialogBinding;
import androidx.annotation.RequiresApi;

/**
 * @author created by knight
 * @organize waanandroid
 * @Date 2021/9/15 17:47
 * @descript:
 */
public class BiometricPromptDialog extends BaseDBDialogFragment<BiometricLoginDialogBinding> {

    public static final int STATE_NORMAL = 1;
    public static final int STATE_FAILED = 2;
    public static final int STATE_ERROR = 3;
    public static final int STATE_SUCCEED = 4;
    private OnBiometricPromptDialogActionCallback mDialogActionCallback;


    public static BiometricPromptDialog newInstance() {
        BiometricPromptDialog dialog = new BiometricPromptDialog();
        return dialog;
    }
    public interface OnBiometricPromptDialogActionCallback {
        void onDialogDismiss();
        void onCancel();
    }
    public void setOnBiometricPromptDialogActionCallback(OnBiometricPromptDialogActionCallback callback) {
        mDialogActionCallback = callback;
    }

    @Override
    protected int layoutId() {
        return R.layout.biometric_login_dialog;
    }

    @Override
    protected void initView() {
        mDatabind.setClick(new ProxyClick());
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    public class ProxyClick {
        
        public void cancel() {
            if (mDialogActionCallback != null) {
                mDialogActionCallback.onCancel();
            }
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mDialogActionCallback != null) {
            mDialogActionCallback.onDialogDismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setState(int state) {
        switch (state) {
            case STATE_NORMAL:
                mDatabind.fingureTvStatus.setTextColor(Color.BLACK);
                mDatabind.fingureTvStatus.setText(getString(R.string.biometric_touch_sensor));
                mDatabind.fingureTvCancel.setVisibility(View.VISIBLE);
                break;
            case STATE_FAILED:
                mDatabind.fingureTvStatus.setTextColor(Color.RED);
                mDatabind.fingureTvStatus.setText(getString(R.string.biometric_touch_verify_failure));
                mDatabind.fingureTvCancel.setVisibility(View.VISIBLE);
                break;
            case STATE_ERROR:
                mDatabind.fingureTvStatus.setTextColor(Color.RED);
                mDatabind.fingureTvStatus.setText(getString(R.string.biometric_touch_verify_error));
                mDatabind.fingureTvCancel.setVisibility(View.VISIBLE);
                break;
            case STATE_SUCCEED:
                mDatabind.fingureTvStatus.setTextColor(getActivity().getColor(R.color.base_color_theme));
                mDatabind.fingureTvStatus.setText(getString(R.string.biometric_touchverify_succeeded));
                mDatabind.fingureTvCancel.setVisibility(View.VISIBLE);
                mDatabind.fingureTvStatus.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 500);
                break;
            default:
                break;
        }
    }



}
