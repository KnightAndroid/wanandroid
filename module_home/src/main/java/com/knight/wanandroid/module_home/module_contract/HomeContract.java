package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.OfficialAccountEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:08
 * @descript:首页contract
 */
public interface HomeContract {

    interface HomeView extends BaseView{
        //设置置顶文章数据
        void setTopArticle(List<TopArticleEntity> topArticleEntityList);
        //设置banner
        void setBannerData(List<BannerEntity> result);
        //设置公众号数据
        void setOfficialAccountData(List<OfficialAccountEntity> officialAccountModels);

    }


    interface HomeModel extends BaseModel{
        //请求置顶文章
        void requestTopArticle(BaseFragment fragment, MvpListener mvpListener);

        //请求banner数据
        void requestBannerData(BaseFragment fragment, MvpListener mvpListener);

        //请求公众号数据
        void requestOfficialAccountData(BaseFragment fragment, MvpListener mvpListener);

    }

    abstract class HomeDataPresenter extends BasePresenter<HomeModel,HomeView>{
        //具体实现
        public abstract void requestBannerData();
        public abstract void requestTopArticle();
        public abstract void requestOfficialAccountData();



    }





}
