package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListModel;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:27
 * @descript:
 */
public interface HomeArticleContract {

    interface HomeArticleView extends BaseView{

        //设置文章数据
        void setHomeArticle(HomeArticleListModel result);
    }


    interface HomeArticleModel extends BaseModel{
        //请求文章数据
        void requestHomeArticle(BaseDBActivity activity, MvpListener mvpListener);
    }

    abstract class HomeArticleDataPresenter extends BasePresenter<HomeArticleModel,HomeArticleView>{
        //具体实现
        public abstract void requestHomeArticle(BaseDBActivity activity);
    }

}
