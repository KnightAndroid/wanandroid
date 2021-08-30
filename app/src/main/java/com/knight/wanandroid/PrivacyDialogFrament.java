package com.knight.wanandroid;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.databinding.DialogPrivacyAgreementBinding;
import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.ActivityManagerUtils;
import com.knight.wanandroid.library_util.TextClickUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/26 17:43
 * @descript:
 */
public class PrivacyDialogFrament extends BaseDBDialogFragment<DialogPrivacyAgreementBinding> {

    SpannableStringBuilder spannable;
    @Override
    protected int layoutId() {
        return R.layout.dialog_privacy_agreement;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initView() {
        mDatabind.setClick(new ProxyClick());
        spannable = new SpannableStringBuilder(mDatabind.appPrivacyTip.getText().toString());
        mDatabind.appPrivacyTip.setMovementMethod(LinkMovementMethod.getInstance());
        spannable.setSpan(new TextClickUtils().setOnClickWebListener(new TextClickUtils.OnClickToWebListener() {
            @Override
            public void goWeb() {
                ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                        .withString("webUrl","file:android_asset/wanandroid_useragree.html")
                        .withString("webTitle","用户协议")
                        .navigation();
            }
        }),8,14, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TextClickUtils().setOnClickWebListener(new TextClickUtils.OnClickToWebListener() {
            @Override
            public void goWeb() {
                ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                        .withString("webUrl","file:android_asset/wanandroid_userprivacy.html")
                        .withString("webTitle","隐私政策")
                        .navigation();
            }
        }),15,21, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mDatabind.appPrivacyTip.setText(spannable);
        ViewSetUtils.avoidHintColor(mDatabind.appPrivacyTip);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                     return true;
                }
                return false;
            }
        });
        return dialog;
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    public class ProxyClick{
        //同意进入app
        public void goAgreeToMain(){
              CacheUtils.getInstance().saveIsAgreeMent(true);
              startActivity(new Intent(getActivity(),MainActivity.class));
              dismiss();
        }

        //不同意退出app
        public void disAgreeExitApp(){
            dismiss();
            ActivityManagerUtils.getInstance().finishAllActivity();
            System.exit(0);
        }
    }
}
