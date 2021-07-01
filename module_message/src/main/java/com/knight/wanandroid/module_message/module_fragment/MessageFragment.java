package com.knight.wanandroid.module_message.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_message.R;
import com.knight.wanandroid.module_message.databinding.MessageReadedFragmentBinding;
import com.knight.wanandroid.module_message.module_adapter.MessageAdapter;
import com.knight.wanandroid.module_message.module_contract.MessageContract;
import com.knight.wanandroid.module_message.module_entity.MessageListEntity;
import com.knight.wanandroid.module_message.module_model.MessageModel;
import com.knight.wanandroid.module_message.module_presenter.MessagePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/30 15:32
 * @descript:
 */

@Route(path = RoutePathFragment.Message.Readed_Message)
public class MessageFragment extends BaseFragment<MessageReadedFragmentBinding, MessagePresenter, MessageModel> implements MessageContract.MessageView, OnRefreshListener, OnLoadMoreListener {


    private int page = 1;
    private boolean readed;
    private MessageAdapter mMessageAdapter;

    public static MessageFragment newInstance(boolean readed){
        MessageFragment messageFragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putBoolean("readed", readed);
        messageFragment.setArguments(args);
        return messageFragment;
    }






    @Override
    protected int layoutId() {
        return R.layout.message_readed_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        readed = getArguments().getBoolean("readed");
        loadLoading(mDatabind.includeMessage.baseFreshlayout);
        mDatabind.includeMessage.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeMessage.baseFreshlayout.setOnLoadMoreListener(this);
        mMessageAdapter = new MessageAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeMessage.baseBodyRv,new LinearLayoutManager(getActivity()),mMessageAdapter,false);

    }


    /**
     *
     * 懒加载
     *
     */
    @Override
    public void lazyLoadData(){
        super.lazyLoadData();
        page = 1;
        if (readed) {
            //如果是已读
            mPresenter.requestMessageReaded(page);
        } else {
            //如果是未读
            mPresenter.requestMessageUnread(page);
        }

    }

    @Override
    protected void reLoadData() {
        lazyLoadData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
        if (page == 1) {
            showloadFailure();
        }
    }

    @Override
    public void setMessageReaded(MessageListEntity messageListEntity) {
        showSuccess();
        mDatabind.includeMessage.baseFreshlayout.finishRefresh();
        mDatabind.includeMessage.baseFreshlayout.finishLoadMore();
        if (page == 1 ) {
            if (messageListEntity.getDatas().size() > 0) {
                mMessageAdapter.setNewInstance(messageListEntity.getDatas());
            } else {
                showEmptyData();
            }

        } else {
            mMessageAdapter.addData(messageListEntity.getDatas());
        }

        if (messageListEntity.getSize() < 10) {
            mDatabind.includeMessage.baseFreshlayout.setEnableLoadMore(false);
        } else {
            page ++;
        }
    }

    @Override
    public void setMessageUnread(MessageListEntity messageListEntity) {
        showSuccess();
        mDatabind.includeMessage.baseFreshlayout.finishRefresh();
        mDatabind.includeMessage.baseFreshlayout.finishLoadMore();
        if (page == 1 ) {
            if (messageListEntity.getDatas().size() > 0) {
                EventBus.getDefault().post(new EventBusUtils.ReadAllMessage());
                mMessageAdapter.setNewInstance(messageListEntity.getDatas());
            } else {
                showEmptyData();
            }

        } else {
            mMessageAdapter.addData(messageListEntity.getDatas());
        }

        if (messageListEntity.getSize() < 10) {
            mDatabind.includeMessage.baseFreshlayout.setEnableLoadMore(false);
        } else {
            page ++;
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (readed) {
            //如果是已读
            mPresenter.requestMessageReaded(page);
        } else {
            //如果是未读
            mPresenter.requestMessageUnread(page);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        lazyLoadData();
    }
}
