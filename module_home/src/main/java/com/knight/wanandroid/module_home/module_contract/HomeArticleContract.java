package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:27
 * @descript:
 */
public interface HomeArticleContract {

    interface HomeArticleView extends BaseView{

        //设置全部文章数据
        void setAllHomeArticle(HomeArticleListEntity result);
        //设置搜索文章数据
        void setSearchArticle(HomeArticleListEntity result);
    }


    interface HomeArticleModel extends BaseModel{
        //请求全部文章数据
        void requestAllHomeArticle(BaseFragment fragment, int page, MvpListener mvpListener);
        //搜索接口文章数据
        void requestSerchArticle(BaseFragment fragment,int page,String keyWords,MvpListener mvpListener);

    }

    abstract class HomeArticleDataPresenter extends BasePresenter<HomeArticleModel,HomeArticleView>{
        //具体实现
        public abstract void requestAllHomeArticle(int page);

        public abstract void requestSearchArticle(int page,String keyWords);
    }

}
