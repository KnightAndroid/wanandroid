package com.knight.wanandroid.module_feedback.module;

import com.knight.wanandroid.library_base.basefragment.BaseDialogFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_feedback.api.FeedBackApi;
import com.knight.wanandroid.module_feedback.contract.FeedBackContract;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/21 16:25
 * @descript:
 */
public final class FeedBackModel implements FeedBackContract.FeedBackodel {
    @Override
    public void requestfeedBackError(BaseDialogFragment fragment, int errorArticleId, MvpListener mvpListener) {
        GoHttp.post(fragment)
                .api(new FeedBackApi().setErrorArticleId(errorArticleId))
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
