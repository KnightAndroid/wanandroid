package com.knight.wanandroid.module_message.module_model;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_network.GoHttp;
import com.knight.wanandroid.library_network.listener.HttpCallback;
import com.knight.wanandroid.library_network.model.HttpData;
import com.knight.wanandroid.module_message.module_contract.MessageContract;
import com.knight.wanandroid.module_message.module_entity.MessageListEntity;
import com.knight.wanandroid.module_message.module_request.MessageReadedApi;
import com.knight.wanandroid.module_message.module_request.MessageUnreadApi;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/30 15:03
 * @descript:
 */
public final class MessageModel implements MessageContract.MessageModel {


    @Override
    public void requestMessageReaded(BaseFragment fragment, int page, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new MessageReadedApi().setMessageReadedPage(page))
                .request(new HttpCallback<HttpData<MessageListEntity>>(fragment){

                    @Override
                    public void onSucceed(HttpData<MessageListEntity> result){
                        mvpListener.onSuccess(result.getData());

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }

    @Override
    public void requestMessageUnread(BaseFragment fragment, int page, MvpListener mvpListener) {
        GoHttp.get(fragment)
                .api(new MessageUnreadApi().setUnreadMessagePage(page))
                .request(new HttpCallback<HttpData<MessageListEntity>>(fragment){

                    @Override
                    public void onSucceed(HttpData<MessageListEntity> result){
                        mvpListener.onSuccess(result.getData());

                    }

                    @Override
                    public void onFail(Exception e){
                        mvpListener.onError(e.getMessage());
                    }

                });
    }
}
