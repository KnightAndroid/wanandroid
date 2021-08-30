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
        load_progress_bar.setIndeterminateTintList(ColorStateList.valueOf(CacheUtils.getInstance().getThemeColor()));

    }



    //    //是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
//    @Override
//    public boolean getSuccessVisible() {
//        return true;
//    }
//
//    //将Callback添加到当前视图时的回调，View为当前Callback的布局View
//    @Override
//    public void onAttach(Context context, View view) {
//        super.onAttach(context, view);
//    }
//
//    //将Callback从当前视图删除时的回调，View为当前Callback的布局View
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    //当前Callback的点击事件，如果返回true则覆盖注册时的onReload()，如果返回false则两者都执行，先执行onReloadEvent()。
//    @Override
//    protected boolean onReloadEvent(Context context, View view) {
//        return true;
//    }

}
