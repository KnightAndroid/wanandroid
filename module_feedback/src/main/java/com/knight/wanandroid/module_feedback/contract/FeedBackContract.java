package com.knight.wanandroid.module_feedback.contract;

import com.knight.wanandroid.library_base.basefragment.BaseDialogFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/21 16:20
 * @descript:
 */
public interface FeedBackContract {

    interface FeedBackView extends BaseView {
        void feedBackErrorSuccess();
    }

    interface FeedBackodel extends BaseModel {
        //反馈链接错误
        void requestfeedBackError(BaseDialogFragment fragment, int collectArticleId, MvpListener mvpListener);
    }

    abstract class FeedBackDataPresenter extends BasePresenter<FeedBackodel, FeedBackView> {

        public abstract void requestfeedBackError(int articleId);
    }

}
