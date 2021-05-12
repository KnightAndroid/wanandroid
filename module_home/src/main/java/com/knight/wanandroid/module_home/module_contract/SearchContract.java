package com.knight.wanandroid.module_home.module_contract;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 15:38
 * @descript:
 */
public interface SearchContract {



    interface SearchView extends BaseView {
        //获取搜索热词
        void setSearchHotKey(List<SearchHotKeyEntity> searchHotKeyEntities);

    }


    interface SearchModel extends BaseModel{
        //请求搜索热词
        void requestSearchHotkey(BaseActivity activity, MvpListener mvpListener);

    }

    abstract class SearchDataPresenter extends BasePresenter<SearchModel,SearchView>{
        public abstract void requestSearchHotkey();


    }


}
