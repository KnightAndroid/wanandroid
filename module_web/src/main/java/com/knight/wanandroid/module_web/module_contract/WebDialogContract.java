package com.knight.wanandroid.module_web.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseDialogFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 11:05
 * @descript:
 */
public interface WebDialogContract {


    interface WebDialogView extends BaseView {
        void collectArticleSuccess();
    }

    interface WebDialogModel extends BaseModel {
        //收藏站内文章
        void requestCollectArticle(BaseDialogFragment fragment, int collectArticleId, MvpListener mvpListener);

    }

    abstract class WebDialogDataPresenter extends BasePresenter<WebDialogModel, WebDialogView> {

        public abstract void  requestCollectArticle(int collectArticleId);
    }
}
