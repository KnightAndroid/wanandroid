package com.knight.wanandroid.module_hierachy.module_contract;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyListEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/29 15:31
 * @descript:体系
 */
public interface HierachyContract {

    interface HierachyView extends BaseView {
        //设置体系数据
        void setHierachyData(List<HierachyListEntity> data);
    }

    interface HierachyModel extends BaseModel {
        void requestHierachyData(BaseDBActivity activity, MvpListener mvpListener);
    }

    abstract class HierachyDataPresenter extends BasePresenter<HierachyModel,HierachyView>{
        public abstract void requestHierachyData(BaseDBActivity activity);
    }




}
