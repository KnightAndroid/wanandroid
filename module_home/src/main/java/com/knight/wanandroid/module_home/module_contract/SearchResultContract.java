package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 18:03
 * @descript:搜索结果界面
 */
public interface SearchResultContract {

    interface SearchResultView extends BaseView{
        //设置搜索结果
        void setSearchResultData(HomeArticleListEntity result);
        //收藏站内文章
        void collectArticleSuccess(int position);
        //取消站内文章
        void cancelArticleSuccess(int position);
    }

    interface SearchResultModel extends BaseModel{
        //向后台请求搜索
        void requestSerchArticle(BaseActivity activity, int page, String keyWords, MvpListener mvpListener);
        //收藏站内文章
        void requestCollectArticle(BaseActivity activity, int collectArticleId, MvpListener mvpListener);
        //取消收藏站内文章
        void requestCancelCollectArticle(BaseActivity activity,int collectArticleId,MvpListener mvpListener);
    }

    abstract class SearchResultDataPresenter extends BasePresenter<SearchResultModel,SearchResultView>{
        public abstract void requestSearchResult(int page, String keyWords);
        public abstract void requestCollectArticle(int collectArticleId,int position);
        public abstract void requestCancelCollectArticle(int collectArticleId,int position);
    }






}
