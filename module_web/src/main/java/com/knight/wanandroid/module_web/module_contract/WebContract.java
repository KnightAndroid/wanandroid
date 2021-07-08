package com.knight.wanandroid.module_web.module_contract;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 15:51
 * @descript:
 */
public interface WebContract {
    interface WebView extends BaseView{
        void collectArticleSuccess();
    }

    interface WebModel extends BaseModel {
        //收藏站内文章
        void requestCollectArticle(BaseActivity activity, int collectArticleId, MvpListener mvpListener);
    }

    abstract class WebDataPresenter extends BasePresenter<WebModel, WebView> {

        public abstract void requestCollectArticle(int collectArticleId);
    }


}
