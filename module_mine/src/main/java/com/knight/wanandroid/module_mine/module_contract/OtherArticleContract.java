package com.knight.wanandroid.module_mine.module_contract;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.module_entity.OtherShareArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 18:51
 * @descript:
 */
public interface OtherArticleContract {

     interface OtherShareArticleView extends BaseView{
         //获取他人分享文章
         void setOtherShareArticle(OtherShareArticleListEntity otherShareArticleListEntity);
         //收藏站内文章
         void collectArticleSuccess(int position);
         //取消站内文章
         void cancelArticleSuccess(int position);
     }

    interface OtherShareArticleModel extends BaseModel {
        void requestOtherShareArticle(BaseActivity activity, int uid,int page, MvpListener mvpListener);
        //收藏站内文章
        void requestCollectArticle(BaseActivity activity, int collectArticleId, MvpListener mvpListener);
        //取消收藏站内文章
        void requestCancelCollectArticle(BaseActivity activity,int collectArticleId,MvpListener mvpListener);
     }

    abstract class OtherShareArticleDataPresenter extends BasePresenter<OtherShareArticleModel,OtherShareArticleView> {
        public abstract void requestOtherShareArticle(int uid,int page);
        public abstract void requestCollectArticle(int collectArticleId,int position);
        public abstract void requestCancelCollectArticle(int collectArticleId,int position);
    }
}
