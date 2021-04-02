package com.knight.wanandroid.library_util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/2 10:56
 * @descript:简单吐司工具类
 */
public class ToastUtils {

    private static Toast mToast = null;
    private Application application;

    private ToastUtils(){

    }


    private static class SingleHolder{
        private static ToastUtils instance = new ToastUtils();
    }

    public static ToastUtils getInstance(){
        return SingleHolder.instance;
    }


    public void initApplicaion(Application application){
        this.application = application;

    }


    /**
     *
     * 普通情况吐司
     * @param message
     */
    public void showToast(String message){
         if(mToast == null){
             mToast = Toast.makeText(application,message,Toast.LENGTH_SHORT);
         } else {
             mToast.cancel();
             mToast = Toast.makeText(application,message,Toast.LENGTH_SHORT);
         }
         mToast.show();

    }


    /**
     *
     * 针对华为安全键盘的吐司情况
     * @param context
     * @param message
     */
    public void showHuaweiToast(Context context,String message){
        if(mToast == null){
            Toast.makeText(context,message,Toast.LENGTH_SHORT);
        } else {
            mToast.cancel();
            Toast.makeText(context,message,Toast.LENGTH_SHORT);
        }
        mToast.show();
        if(MobileUtils.isEMUI() && context instanceof Activity){
            View focusView = ((Activity) context).getWindow().getDecorView().findFocus();
            if(focusView instanceof EditText){
                if(((EditText) focusView).getInputType() == (InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT)){
                    SystemUtils.hideSoftKeyboard((Activity) context);
                }
            }

        }

    }














}
