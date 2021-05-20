package com.knight.wanandroid.module_web.module_model;

import com.knight.wanandroid.library_base.api.CollectArticleApi;
import com.knight.wanandroid.library_base.fragment.BaseDialogFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_web.module_contract.WebDialogContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/20 11:31
 * @descript:
 */
public class WebDialogModel implements WebDialogContract.WebDialogModel {
    @Override
    public void requestCollectArticle(BaseDialogFragment fragment, int collectArticleId, MvpListener mvpListener) {
        GoHttp.post(fragment)
                .api(new CollectArticleApi().setCollectArticleId(collectArticleId))
                .request(new HttpCallback<HttpData>(fragment){
                    @Override
                    public void onSucceed(HttpData result) {
                        mvpListener.onSuccess(result);
                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }


                });
    }
}
