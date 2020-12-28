package com.knight.wanandroid.library_base.presenter;

import java.lang.ref.WeakReference;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 20:10
 * @descript:基类Presenter
 */

/**
 *
 * View --> Presenter，反应View的一些用户事件到Presenter上。
 * Presenter --> Model, Presenter去读写操作一些我们需要的数据。
 * Presenter --> View, Presenter在获取数据之后，将更新内容反馈给Activity，进行view更新。
 * @param <M>
 * @param <V>
 */
public abstract class BasePresenter<M,V> {

    public M mModel;
    public V mView;

    public WeakReference<V> mViewRef;

    public void attachModelView(M pModel,V pView){
        mViewRef = new WeakReference<V>(pView);
        this.mModel = pModel;
    }

    public V getView(){
        if(isAttach()){
            return mViewRef.get();
        } else {
            return null;
        }
    }


    public boolean isAttach(){
        return null != mViewRef && null != mViewRef.get();
    }

    public void onDettach(){
        if(null != mViewRef){
            mViewRef.clear();
            mViewRef = null;

        }
    }

}
