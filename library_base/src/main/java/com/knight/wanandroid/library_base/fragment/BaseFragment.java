package com.knight.wanandroid.library_base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 18:59
 * @descript:fragment基类
 */
public abstract class BaseFragment<DB extends ViewDataBinding> extends Fragment {

    //是否第一次加载
    private boolean isFirst = true;
    protected DB mDatabind;


    /**
     *
     *
     * 布局视图id
     * @return
     */
    protected abstract int layoutId();


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
     *
     * 加载布局
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState){
         mDatabind = DataBindingUtil.inflate(inflater,layoutId(),container,false);
         mDatabind.setLifecycleOwner(this);
         return mDatabind.getRoot();
    }



    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        onVisible();
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









}
