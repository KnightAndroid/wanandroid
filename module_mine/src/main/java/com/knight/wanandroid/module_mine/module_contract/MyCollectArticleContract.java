package com.knight.wanandroid.module_mine.module_contract;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_mine.module_entity.MyCollectArticleListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 17:04
 * @descript:
 */
public interface MyCollectArticleContract {

        interface MyCollectArticleView extends BaseView {

                void setCollectArticles(MyCollectArticleListEntity myCollectArticleListEntity);
        }

        interface MyCollectArticleModel extends BaseModel {
                void requestCollectArticles(BaseActivity activity, int page, MvpListener mvpListener);

        }

        abstract class MyCollectDataPresenter extends BasePresenter<MyCollectArticleModel,MyCollectArticleView>{
                public abstract void requestCollectArticles(int page);

        }
}
