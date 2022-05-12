package com.knight.wanandroid.module_utils.contract;

import android.webkit.WebView;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_utils.entity.UtilsEntity;

import java.util.List;

/**
 * Author:Knight
 * Time:2022/5/12 14:46
 * Description:UtilsContract
 */
public interface UtilsContract {

    interface UtilsView extends BaseView {


        //设置工具类数据
        void setUtilsData(List<UtilsEntity> utilsData);
    }


    interface UtilsModel extends BaseModel {

        //搜索接口文章数据
        void requestUtilLists(BaseActivity activity, MvpListener mvpListener);
    }

    abstract class UtilsPresenter extends BasePresenter<UtilsModel, UtilsView> {

        public abstract void requestUtilLists();
    }

}
