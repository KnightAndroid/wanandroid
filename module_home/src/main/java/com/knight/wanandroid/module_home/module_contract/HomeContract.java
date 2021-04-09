package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.BannerModel;
import com.knight.wanandroid.module_home.module_entity.TopArticleModel;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:08
 * @descript:首页contract
 */
public interface HomeContract {

    interface HomeView extends BaseView{
        //获取置顶文章数据
        void setTopArticle(List<TopArticleModel> topArticleModelList);
        //获取banner
        void setBannerData(List<BannerModel> result);

        //获取列表数据
        void setListData();
    }


    interface HomeModel extends BaseModel{
        //请求置顶文章
        void requestTopArticle(BaseDBActivity activity,MvpListener mvpListener);


        //请求banner数据
        void requestBannerData(BaseDBActivity activity, MvpListener mvpListener);
    }

    abstract class HomeDataPresenter extends BasePresenter<HomeModel,HomeView>{
        //具体实现
        public abstract void requestBannerData(BaseDBActivity activity);
        //具体实现
        public abstract void requestTopArticle(BaseDBActivity activity);


    }





}
