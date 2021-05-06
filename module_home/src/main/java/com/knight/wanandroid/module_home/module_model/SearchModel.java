package com.knight.wanandroid.module_home.module_model;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.api.HotKeyApi;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_home.module_contract.SearchContract;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 15:48
 * @descript:
 */
public class SearchModel implements SearchContract.SearchModel {
    @Override
    public void requestSearchHotkey(BaseActivity activity, MvpListener mvpListener) {
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
}
