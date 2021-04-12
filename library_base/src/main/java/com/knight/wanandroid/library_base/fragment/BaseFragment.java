package com.knight.wanandroid.library_base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_util.CreateUtils;

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
public abstract class BaseFragment<DB extends ViewDataBinding,T extends BasePresenter,M extends BaseModel> extends Fragment {

    //是否第一次加载
    private boolean isFirst = true;
    protected DB mDatabind;

    public T mPresenter;
    public M mModel;
    public LoadService mLoadService;

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
//         mLoadService = LoadSir.getDefault().register(mDatabind.getRoot(), new Callback.OnReloadListener() {
//            @Override
//            public void onReload(View v) {
//                mLoadService.showCallback(LoadCallBack.class);
//            }
//        });

         return mDatabind.getRoot();
      //   return mLoadService.getLoadLayout();
    }



    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
        //从内部获取第二个类型参数的真实类型，反射出new对象
        mPresenter = CreateUtils.get(this,1);
        //从内部获取第三个类型参数的真实类型，反射出new对象
        mModel = CreateUtils.get(this,2);
        //使得p层绑定M层和V层，持有M和V的引用
        mPresenter.attachModelView(mModel,this);
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
     * 默认加载
     * @param view
     */
    protected void loadLoading(View view) {
        if (mLoadService == null) {
            mLoadService = LoadSir.getDefault().register(view, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mLoadService.showCallback(LoadCallBack.class);
                }
            });
        }
        mLoadService.showCallback(LoadCallBack.class);

    }

    /**
     *
     * 成功请求数据
     */
    protected void showSuccess() {
        if (mLoadService != null) {
            mLoadService.showSuccess();
        }
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


    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDettach();
    }









}
