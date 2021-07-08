package com.knight.wanandroid.module_hierachy.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyTabArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 17:05
 * @descript:
 */
public interface HierachyTabContract {

    interface HierachyTabView extends BaseView {
        //设置体系数据
        void setHierachyTabArticles(HierachyTabArticleListEntity data);
        //收藏站内文章
        void collectArticleSuccess(int position);
        //取消站内文章
        void cancelArticleSuccess(int position);

    }

    interface HierachyTabModel extends BaseModel {
        //请求指定体系列表下文章数据
        void requestHierachyTabArticles(BaseFragment fragment, int page,int cid,MvpListener mvpListener);
        //收藏站内文章
        void requestCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
        //取消收藏站内文章
        void requestCancelCollectArticle(BaseFragment fragment,int collectArticleId,MvpListener mvpListener);
    }

    abstract class HierachyTabDataPresenter extends BasePresenter<HierachyTabModel,HierachyTabView> {
        public abstract void requestHierachyTabArticles(int page,int cid);
        public abstract void requestCollectArticle(int collectArticleId,int position);
        public abstract void requestCancelCollectArticle(int collectArticleId,int position);

    }
}
