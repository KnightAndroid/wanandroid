package com.knight.wanandroid.module_square.module_model;

import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_square.module_contract.SquareShareArticleContact;
import com.knight.wanandroid.module_square.module_request.SquareShareArticleApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/7 16:05
 * @descript:
 */
public class SquareShareArticleModel implements SquareShareArticleContact.SquareShareArticleModel {
    @Override
    public void requestShareArticle(BaseActivity activity, String title,String link,MvpListener mvpListener) {
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
                });
    }
}
