package com.knight.wanandroid.library_base.activity;

import android.os.Bundle;

import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_util.CreateUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2020/12/28 19:54
 * @descript:Activity基类
 */
public abstract class BaseActivity<DB extends ViewDataBinding,T extends BasePresenter,M extends BaseModel> extends AppCompatActivity {

    public abstract int layoutId();

    public DB mDatabind;
    public T mPresenter;
    public M mModel;


    public abstract void initView(Bundle savedInstanceState);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createViewDataBinding();
        initView(savedInstanceState);
        //内部获取第二个类型参数的真实类型，反射new出对象
        mPresenter = CreateUtils.get(this,1);
        //内部获取第三个类型参数的真实类型，反射new出对手
        mModel = CreateUtils.get(this,2);
        //使得p层绑定M层和V层，持有M和V的引用
        mPresenter.attachModelView(mModel,this);



    }


    private void createViewDataBinding() {
        mDatabind = DataBindingUtil.setContentView(this, layoutId());
        mDatabind.setLifecycleOwner(this);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mPresenter.onDettach();
    }

}
