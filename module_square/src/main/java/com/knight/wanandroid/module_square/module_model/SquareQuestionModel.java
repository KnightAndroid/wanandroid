package com.knight.wanandroid.module_square.module_model;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_square.module_contract.SquareQuestionContract;
import com.knight.wanandroid.module_square.module_entity.SquareQuestionListEntity;
import com.knight.wanandroid.module_square.module_request.SquareQuestionApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/17 14:53
 * @descript:
 */
public class SquareQuestionModel implements SquareQuestionContract.SquareQuestionModel {

    @Override
    public void requestQuestions(BaseFragment fragment, int page, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new SquareQuestionApi().setPage(page))
                .request(new HttpCallback<HttpData<SquareQuestionListEntity>>(fragment) {
                    @Override
                    public void onSucceed(HttpData<SquareQuestionListEntity> result) {
                        mvpListener.onSuccess(result.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mvpListener.onError(e.getMessage());
                    }
                });
    }
}
