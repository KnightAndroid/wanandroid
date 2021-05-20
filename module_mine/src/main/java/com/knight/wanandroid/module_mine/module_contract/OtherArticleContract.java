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
         void setOtherShareArticle(OtherShareArticleListEntity otherShareArticleListEntity);
     }

    interface OtherShareArticleModel extends BaseModel {
        void requestOtherShareArticle(BaseActivity activity, int uid,int page, MvpListener mvpListener);
    }

    abstract class OtherShareArticleDataPresenter extends BasePresenter<OtherShareArticleModel,OtherShareArticleView> {
        public abstract void requestOtherShareArticle(int uid,int page);
    }
}
