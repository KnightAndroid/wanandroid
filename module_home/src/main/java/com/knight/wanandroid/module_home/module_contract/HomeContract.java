package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:08
 * @descript:首页contract
 */
public interface HomeContract {

    interface HomeView extends BaseView{
        //获取banner
        void setBannerData();
        //获取列表数据
        void setListData();
    }


    interface HomeModel extends BaseModel{
        //请求banner数据
        void requestBannerData();
    }

    abstract class HomeDataPresenter extends BasePresenter<HomeModel,HomeView>{
        //具体实现
        public abstract void requestBannerData();


    }





}
