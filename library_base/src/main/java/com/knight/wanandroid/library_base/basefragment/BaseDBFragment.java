package com.knight.wanandroid.library_base.basefragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knight.wanandroid.library_util.CacheUtils;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/30 10:40
 * @descript:非业务Fragment
 */
public abstract class BaseDBFragment<DB extends ViewDataBinding> extends Fragment {

    protected DB mDatabind;
    //是否第一次加载
    private boolean isFirst = true;

    protected int themeColor;
    protected int bgColor;
    protected boolean isDarkMode;
    /**
     *
     *
     * 布局视图id
     * @return
     */
    protected abstract int layoutId();


    /**
     * 主题色设置
     *
     */
    protected abstract void setThemeColor(boolean isDarkMode);


    /**
     *
     * 初始化view
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);


    /**
     *
     * 懒加载
     *
     */
    protected void lazyLoadData(){

    }


    /**
     *
     * Fragment执行onCreate后触发的方法
     */
    protected void initData(){

    }

    /**
     *
     * 重新加载当前页面请求
     */
    /**
     *
     *
     * 加载布局
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
        initView(savedInstanceState);
        onVisible();
        isDarkMode = CacheUtils.getInstance().getNormalDark();
        initThemeColor();
        initBgColor();
        setThemeColor(isDarkMode);
        initData();

    }



    @Override
    public void onResume(){
        super.onResume();
        onVisible();
    }

    /**
     *
     *  是否需要懒加载
     *
     */
    private void onVisible(){
        if(getLifecycle().getCurrentState() == Lifecycle.State.STARTED && isFirst){
            lazyLoadData();
            isFirst = false;
        }
    }

    /**
     *
     * 获取主题颜色
     *
     */
    protected void initThemeColor() {
        themeColor = CacheUtils.getInstance().getThemeColor();
    }



    /**
     *
     * 获取背景颜色
     */
    protected void initBgColor() {
        bgColor = CacheUtils.getInstance().getBgThemeColor();
    }

}
