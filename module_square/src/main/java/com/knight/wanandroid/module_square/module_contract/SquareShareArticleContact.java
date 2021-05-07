package com.knight.wanandroid.module_square.module_contract;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/7 15:34
 * @descript:
 */
public interface SquareShareArticleContact {


    interface SquareShareArticleView extends BaseView{
        //分享成功
        void successShareArticle();
    }

    interface SquareShareArticleModel extends BaseModel{

        //分享最新文章
        void requestShareArticle(BaseActivity activity, String title,String link,MvpListener mvpListener);
    }

    abstract class SquareSharedataPresenter extends BasePresenter<SquareShareArticleModel,SquareShareArticleView>{

        //分享最新文章
        public abstract void requestShareArticle(String title,String link);
    }




}
