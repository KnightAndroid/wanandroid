package com.knight.wanandroid.module_mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityCoinrankBinding;
import com.knight.wanandroid.module_mine.adapter.UserRankCoinAdapter;
import com.knight.wanandroid.module_mine.contract.CoinRankContract;
import com.knight.wanandroid.module_mine.entity.CoinRankListEntity;
import com.knight.wanandroid.module_mine.model.CoinRankModel;
import com.knight.wanandroid.module_mine.presenter.CoinRankPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/23 17:25
 * @descript:积分排行榜
 */
@Route(path = RoutePathActivity.Mine.UserCoinRank_Pager)
public final class CoinRankActivity extends BaseActivity<MineActivityCoinrankBinding, CoinRankPresenter, CoinRankModel> implements CoinRankContract.CoinRankView, OnRefreshListener, OnLoadMoreListener {


    private UserRankCoinAdapter mUserRankCoinAdapter;
    private int page = 1;
    @Override
    public int layoutId() {
        return R.layout.mine_activity_coinrank;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeCoinrankToolbar.baseTvTitle.setText(getString(R.string.mine_coin_rank));
        mDatabind.includeCoinrankToolbar.baseIvBack.setOnClickListener(v -> finish());
        mUserRankCoinAdapter = new UserRankCoinAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeMineCoinrank.baseBodyRv,new LinearLayoutManager(this),mUserRankCoinAdapter,false);
        mDatabind.includeMineCoinrank.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeMineCoinrank.baseFreshlayout.setOnLoadMoreListener(this);
        showLoading(mDatabind.includeMineCoinrank.baseFreshlayout);
        initListener();

    }


    @Override
    public void initData(){
        mPresenter.requestRankCoin(page);
    }


    @Override
    public void reLoadData(){
        mDatabind.includeMineCoinrank.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestRankCoin(page);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.show(errorMsg);
    }

    @Override
    public void setRankCoin(CoinRankListEntity result) {
        showSuccess();
        mDatabind.includeMineCoinrank.baseFreshlayout.finishRefresh();
        mDatabind.includeMineCoinrank.baseFreshlayout.finishLoadMore();
        if (result.getDatas().size() > 0) {
            if (page == 1) {
                mUserRankCoinAdapter.setNewInstance(result.getDatas());
            } else {
                mUserRankCoinAdapter.addData(result.getDatas());
            }
            page ++;
        } else {
            mDatabind.includeMineCoinrank.baseFreshlayout.setEnableLoadMore(false);
        }
    }





    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestRankCoin(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.requestRankCoin(page);
        mDatabind.includeMineCoinrank.baseFreshlayout.setEnableLoadMore(true);
    }

    private void initListener(){
        mUserRankCoinAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(CoinRankActivity.this,OtherShareArticleActivity.class)
                .putExtra("uid",mUserRankCoinAdapter.getData().get(position).getUserId()));
            }
        });
    }
}
