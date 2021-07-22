package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_home.module_entity.EveryDayPushArticlesEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 11:08
 * @descript:首页contract
 */
public interface HomeContract {

    interface HomeView extends BaseView{

        //获取版本更新内容
        void setAppUpdateMessage(AppUpdateEntity appUpdateEntity);
        //获取每日推荐
        void setEveryDayPushArticle(EveryDayPushArticlesEntity everyDayPushArticlesEntity);


    }


    interface HomeModel extends BaseModel{
        //请求版本更新信息
        void requestAppUpdateMessage(BaseFragment fragment,MvpListener mvpListener);

        //请求每日推荐信息
        void requestEveryDayPushArticle(BaseFragment fragment,MvpListener mvpListener);


    }

    abstract class HomeDataPresenter extends BasePresenter<HomeModel,HomeView>{

        public abstract void requestAppUpdateMessage();
        public abstract void requestEveryDayPushArticle();




    }





}
