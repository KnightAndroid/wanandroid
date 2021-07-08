package com.knight.wanandroid.module_message.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_message.module_entity.MessageListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/30 14:42
 * @descript:
 */
public interface MessageContract {

    interface MessageView extends BaseView{
        void setMessageReaded(MessageListEntity messageListEntity);
        void setMessageUnread(MessageListEntity messageListEntity);
    }

    interface MessageModel extends BaseModel{
        void requestMessageReaded(BaseFragment fragment, int page, MvpListener mvpListener);
        void requestMessageUnread(BaseFragment fragment,int page,MvpListener mvpListener);
    }

    abstract class MessageDataPresenter extends BasePresenter<MessageModel, MessageView> {
        public abstract void requestMessageReaded(int page);
        public abstract void requestMessageUnread(int page);

    }


}
