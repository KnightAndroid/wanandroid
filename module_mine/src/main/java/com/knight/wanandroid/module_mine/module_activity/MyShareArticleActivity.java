package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityMyshareBinding;
import com.knight.wanandroid.module_mine.module_adapter.MyShareArticleAdapter;
import com.knight.wanandroid.module_mine.module_contract.MyShareArticleContract;
import com.knight.wanandroid.module_mine.module_entity.MyShareEntity;
import com.knight.wanandroid.module_mine.module_model.MyShareArticleModel;
import com.knight.wanandroid.module_mine.module_presenter.MyShareArticlePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/8 10:03
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.MyShareArticle_Pager)
public class MyShareArticleActivity extends BaseActivity<MineActivityMyshareBinding, MyShareArticlePresenter, MyShareArticleModel> implements MyShareArticleContract.MyShareArticleView, OnLoadMoreListener, OnRefreshListener {

    private int page = 1;
    private MyShareArticleAdapter mMyShareArticleAdapter;
    @Override
    public int layoutId() {
        return R.layout.mine_activity_myshare;

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeMineshareToolbar.baseTvTitle.setText("我的分享");
        mDatabind.includeMineshareToolbar.baseIvBack.setOnClickListener(v -> finish());
        showLoading(mDatabind.includeMineSharerefreshalayout.baseFreshlayout);
        mMyShareArticleAdapter = new MyShareArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeMineSharerefreshalayout.baseBodyRv,new LinearLayoutManager(this),mMyShareArticleAdapter,false);
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.setOnLoadMoreListener(this);
    }

    @Override
    public void initData(){
        mPresenter.requestMyShareArticle(page);
    }



    @Override
    public void reLoadData(){
        page = 1;
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestMyShareArticle(page);
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
        showFailure();
    }

    @Override
    public void setMyShareArticle(MyShareEntity myShareEntity) {
        showSuccess();
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.finishRefresh();
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.finishLoadMore();
        if (myShareEntity.getShareArticles().getDatas().size() > 0) {
            if (page == 1) {
                mMyShareArticleAdapter.setNewInstance(myShareEntity.getShareArticles().getDatas());
            } else {
                mMyShareArticleAdapter.addData(myShareEntity.getShareArticles().getDatas());
            }
            page ++;
        } else {
            if (page == 1) {
                showEmptyData();
            }
            mDatabind.includeMineSharerefreshalayout.baseFreshlayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestMyShareArticle(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestMyShareArticle(page);
    }
}
