package com.knight.wanandroid.module_hierachy.module_contract;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_hierachy.module_entity.NavigateListEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/30 14:18
 * @descript:
 */
public interface NavigateContract {
    interface NavigateView extends BaseView{
        //设置导航数据
        void setNavigateData(List<NavigateListEntity> navigateListEntity);
    }

    interface NavigateModel extends BaseModel{
        //请求导航数据
        void requestNavigateData(BaseDBActivity activity, MvpListener mvpListener);
    }

    abstract class NavigateDataPrensenter extends BasePresenter<NavigateModel,NavigateView>{
        public abstract void requestNavigateData(BaseDBActivity activity);

    }






}
