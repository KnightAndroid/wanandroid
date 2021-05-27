package com.knight.wanandroid.module_mine.module_contract;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.module_entity.MyShareEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/8 10:20
 * @descript:
 */
public interface MyShareArticleContract {

    interface MyShareArticleView extends BaseView{
        void setMyShareArticle(MyShareEntity myShareEntity);
        //删除我的分享成功
        void deleteArticleSuccess(int position);
    }

    interface MyShareArticleModel extends BaseModel{
        void requestMyShareArticle(BaseActivity activity, int page,MvpListener mvpListener);
        //取消收藏站内文章
        void requestDeleteCollectArticle(BaseActivity activity,int collectArticleId,MvpListener mvpListener);
    }

    abstract class MyShareArticleDataPresenter extends BasePresenter<MyShareArticleModel,MyShareArticleView>{
        public abstract void requestMyShareArticle(int page);
        public abstract void requestDeleteCollectArticle(int collectArticleId,int position);

    }


}
