package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/21 15:11
 * @descript:
 */
public interface RecommendContract {


    interface RecommendView extends BaseView{
        //设置置顶文章数据
        void setTopArticle(List<TopArticleEntity> topArticleEntityList);
        //设置banner
        void setBannerData(List<BannerEntity> result);
        //设置公众号数据
        void setOfficialAccountData(List<OfficialAccountEntity> officialAccountModels);
        //设置全部文章数据
        void setAllHomeArticle(HomeArticleListEntity result);
        //获取未读消息数量
        void setUnreadMessage(int number);

        //收藏站内文章
        void collectArticleSuccess(int position);
        //取消站内文章
        void cancelArticleSuccess(int position);
    }

    interface RecommendModel extends BaseModel{
        //请求置顶文章
        void requestTopArticle(BaseFragment fragment, MvpListener mvpListener);
        //请求banner数据
        void requestBannerData(BaseFragment fragment, MvpListener mvpListener);
        //请求公众号数据
        void requestOfficialAccountData(BaseFragment fragment, MvpListener mvpListener);
        //请求全部文章数据
        void requestAllHomeArticle(BaseFragment fragment, int page, MvpListener mvpListener);
        //请求每日未读消息数量
        void requestUnreadMessage(BaseFragment fragment,MvpListener mvpListener);
        //收藏站内文章
        void requestCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
        //取消收藏站内文章
        void requestCancelCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
    }

    abstract class RecommendDataPresenter extends BasePresenter<RecommendModel,RecommendView>{
        //具体实现
        public abstract void requestBannerData();
        public abstract void requestTopArticle();
        public abstract void requestOfficialAccountData();
        public abstract void requestAllHomeArticle(int page);
        public abstract void requestUnreadMessage();
        public abstract void requestCollectArticle(int collectArticleId,int position);
        public abstract void requestCancelCollectArticle(int collectArticleId,int position);
    }
}
