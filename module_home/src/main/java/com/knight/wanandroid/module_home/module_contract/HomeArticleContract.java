package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:27
 * @descript:
 */
public interface HomeArticleContract {

    interface HomeArticleView extends BaseView{


        //设置搜索文章数据
        void setSearchArticle(HomeArticleListEntity result);
        //收藏站内文章
        void collectArticleSuccess(int position);
        //取消站内文章
        void cancelArticleSuccess(int position);
    }


    interface HomeArticleModel extends BaseModel{

        //搜索接口文章数据
        void requestSerchArticle(BaseFragment fragment,int page,String keyWords,MvpListener mvpListener);
        //收藏站内文章
        void requestCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
        //取消收藏站内文章
        void requestCancelCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
    }

    abstract class HomeArticleDataPresenter extends BasePresenter<HomeArticleModel,HomeArticleView>{
        //具体实现


        public abstract void requestSearchArticle(int page,String keyWords);
        public abstract void requestCollectArticle(int collectArticleId,int position);
        public abstract void requestCancelCollectArticle(int collectArticleId,int position);
    }

}
