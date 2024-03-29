package com.knight.wanandroid.module_square.module_model;

import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.module_contract.SquareShareArticleContact;
import com.knight.wanandroid.module_square.module_request.SquareShareArticleApi;

import okhttp3.Call;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/7 16:05
 * @descript:
 */
public final class SquareShareArticleModel implements SquareShareArticleContact.SquareShareArticleModel {
    @Override
    public void requestShareArticle(BaseActivity activity, String title,String link,MvpListener mvpListener) {

        activity.showLoadingHud(activity.getString(R.string.square_sharearticle_loading));
        GoHttp.post(activity)
                .api(new SquareShareArticleApi().setTitle(title).setLink(link))
                .request(new HttpCallback<HttpData>(activity){
                    @Override
                    public void onSucceed(HttpData result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mvpListener.onError(e.getMessage());
                    }

                    @Override
                    public void onEnd(Call call){
                        activity.dismissLoadingHud();
                    }
                });
    }
}
