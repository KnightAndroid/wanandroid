package com.knight.wanandroid.module_square.module_model;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_square.module_contract.SquareContact;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.module_square.module_entity.SquareArticleListEntity;
import com.knight.wanandroid.library_base.api.HotKeyApi;
import com.knight.wanandroid.module_square.module_request.SquareArticleApi;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:23
 * @descript:
 */
public class SquareModel implements SquareContact.SquareModel {


    /**
     * 搜索热词
     *
     * @param activity
     * @param mvpListener
     */
    @Override
    public void requestHotKey(BaseDBActivity activity, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new HotKeyApi())
                .request(new HttpCallback<HttpData<List<SearchHotKeyEntity>>>(activity) {
                    @Override
                    public void onSucceed(HttpData<List<SearchHotKeyEntity>> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mvpListener.onError(e.getMessage());
                    }
                });

    }

    @Override
    public void requestShareArticles(BaseDBActivity activity, int page, MvpListener mvpListener) {
        GoHttp.get(activity)
                .api(new SquareArticleApi().setPage(page))
                .request(new HttpCallback<HttpData<SquareArticleListEntity>>(activity) {
                             @Override
                             public void onSucceed(HttpData<SquareArticleListEntity> result) {
                                 mvpListener.onSuccess(result.getData());
                             }

                             @Override
                             public void onFail(Exception e) {
                                 mvpListener.onError(e.getMessage());
                             }
                         }

                );
    }


}
