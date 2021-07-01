package com.knight.wanandroid.module_message.module_presenter;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_message.module_contract.MessageContract;
import com.knight.wanandroid.module_message.module_entity.MessageListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/30 15:11
 * @descript:
 */
public class MessagePresenter extends MessageContract.MessageDataPresenter {
    @Override
    public void requestMessageReaded(int page) {
        final MessageContract.MessageView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestMessageReaded((BaseFragment) mView, page, new MvpListener<MessageListEntity>() {
            @Override
            public void onSuccess(MessageListEntity data) {
              mView.setMessageReaded(data);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

    }

    @Override
    public void requestMessageUnread(int page) {
        final MessageContract.MessageView mView = getView();
        if (mView == null) {
            return;
        }
        mModel.requestMessageUnread((BaseFragment) mView, page, new MvpListener<MessageListEntity>() {
            @Override
            public void onSuccess(MessageListEntity data) {
                mView.setMessageUnread(data);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
