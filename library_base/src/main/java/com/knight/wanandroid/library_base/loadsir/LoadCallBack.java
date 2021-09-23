package com.knight.wanandroid.library_base.loadsir;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ProgressBar;

import com.kingja.loadsir.callback.Callback;
import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_common.utils.CacheUtils;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/7 11:46
 * @descript:
 */
public class LoadCallBack extends Callback {

    private ProgressBar load_progress_bar;


    @Override
    protected int onCreateView () {
        return R.layout.base_layout_load;
    }


    private void setProgressColor(){

    }

    @Override
    public void onViewCreate(Context context, View view) {
        super.onViewCreate(context,view);
        load_progress_bar = view.findViewById(R.id.load_progress_bar);
        load_progress_bar.setIndeterminateTintList(ColorStateList.valueOf(CacheUtils.getThemeColor()));

    }

}
