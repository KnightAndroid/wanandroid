package com.knight.wanandroid.library_base.basefragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.knight.wanandroid.library_base.R;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/26 17:36
 * @descript:
 */
public abstract class BaseDBDialogFragment<DB extends ViewDataBinding> extends DialogFragment  {
    protected DB mDatabind;



    /**
     * 布局视图id
     * @return
     */
    protected abstract int layoutId();

    protected abstract void initView();

    /**
     *
     * 方向
     * @return
     */
    protected abstract int getGravity();





    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        mDatabind = DataBindingUtil.inflate(inflater,layoutId(),container,false);
        mDatabind.setLifecycleOwner(this);
        return mDatabind.getRoot();

    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.base_dialog_anim);
        getGravity();
        return dialog;
    }




    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.gravity = getGravity();
            dialog.getWindow().setAttributes(params);
        }
    }

    public void showAllowingStateLoss(FragmentManager manager, String tag){
        try {
            Field dismissed = DialogFragment.class.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(this, false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field shown = DialogFragment.class.getDeclaredField("mShownByMe");
            shown.setAccessible(true);
            shown.set(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }


}
