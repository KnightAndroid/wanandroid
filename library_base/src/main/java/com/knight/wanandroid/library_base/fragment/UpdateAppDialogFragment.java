package com.knight.wanandroid.library_base.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;

import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.library_base.databinding.BaseUpdateappDialogBinding;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.ScreenUtils;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/10 18:48
 * @descript:
 */
public class UpdateAppDialogFragment extends BaseDBDialogFragment<BaseUpdateappDialogBinding> {



    private AppUpdateEntity mAppUpdateEntity;


    public UpdateAppDialogFragment(){

    }





    public static UpdateAppDialogFragment newInstance(AppUpdateEntity mAppUpdateEntity){
        UpdateAppDialogFragment homePushArticleFragment = new UpdateAppDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("updateEntity",(Serializable) mAppUpdateEntity);
        homePushArticleFragment.setArguments(args);
        return homePushArticleFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.base_updateapp_dialog;
    }

    @Override
    protected void initView() {
        mDatabind.setClick(new ProxyClick());
        mAppUpdateEntity = (AppUpdateEntity)getArguments().getSerializable("updateEntity");
        mDatabind.tvAppupdateVersion.setText("v"+mAppUpdateEntity.getVersionName());
        mDatabind.tvAppupdateTime.setText(mAppUpdateEntity.getUpdateTime());
        mDatabind.tvAppupdateContent.setText(mAppUpdateEntity.getUpdateDesc());
        if (mAppUpdateEntity.isForceUpdate()) {
            mDatabind.baseAppupdateClose.setVisibility(View.GONE);
        } else {
            mDatabind.baseAppupdateClose.setVisibility(View.VISIBLE);
        }
        mDatabind.tvAppupdateTitlename.setTextColor(CacheUtils.getThemeColor());
        mDatabind.tvAppupdateVersion.setTextColor(CacheUtils.getThemeColor());
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(CacheUtils.getThemeColor());
        gradientDrawable.setCornerRadius(ScreenUtils.dp2px(45));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDatabind.tvConfimUpdate.setBackground(gradientDrawable);
        } else {
            mDatabind.tvConfimUpdate.setBackgroundDrawable(gradientDrawable);
        }

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
        public void downLoadApp(){
            dismiss();
            DownLoadDialogFragment.newInstance(mAppUpdateEntity.getDownLoadLink()).showAllowingStateLoss(getParentFragmentManager(), "dialog_downlaod");
        }

        public void closeUpdateDialog(){
            dismiss();
        }
    }
}
