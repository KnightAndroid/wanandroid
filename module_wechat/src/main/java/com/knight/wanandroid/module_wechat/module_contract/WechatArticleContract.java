package com.knight.wanandroid.module_wechat.module_contract;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_wechat.module_entity.WechatArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 14:16
 * @descript:
 */
public interface WechatArticleContract {

    interface WechatArticleView extends BaseView {
        void setWechatArticle(WechatArticleListEntity result);
        //收藏站内文章
        void collectArticleSuccess(int position);
        //取消站内文章
        void cancelArticleSuccess(int position);
        //搜索本公众号文章数据
        void setSearchArticlesByKeyword(WechatArticleListEntity result);
    }

    interface WechatArticleModel extends BaseModel {
        void requestWechatArticle(BaseFragment fragment, int page, int cid, MvpListener mvpListener);
        //收藏站内文章
        void requestCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
        //取消收藏站内文章
        void requestCancelCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
        //搜索公众号得数据
        void requestArticlesByKeywords(BaseFragment fragment,int page,int cid,String keywords,MvpListener mvpListener);
    }

    abstract class WechatArticleDataPresenter extends BasePresenter<WechatArticleModel,WechatArticleView> {
        public abstract void requestWechatArticle(int page, int cid);
        public abstract void requestCollectArticle(int collectArticleId,int position);
        public abstract void requestCancelCollectArticle(int collectArticleId,int position);
        public abstract void requestArticlesByKeywords(int page,int cid,String keywords);
    }

}
