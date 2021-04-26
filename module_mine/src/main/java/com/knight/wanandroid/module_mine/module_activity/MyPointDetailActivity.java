package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityDetailpointBinding;
import com.knight.wanandroid.module_mine.module_adapter.UserDetailCoinAdapter;
import com.knight.wanandroid.module_mine.module_contract.UserDetailCoinContract;
import com.knight.wanandroid.module_mine.module_entity.UserDetailCoinListEntity;
import com.knight.wanandroid.module_mine.module_model.UserDetailCoinModel;
import com.knight.wanandroid.module_mine.module_presenter.UserDetailCoinPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/16 16:12
 * @descript:积分明细 我的积分
 */

@Route(path = RoutePathActivity.Mine.UserCoin_pager)
public class MyPointDetailActivity extends BaseActivity<MineActivityDetailpointBinding, UserDetailCoinPresenter, UserDetailCoinModel> implements UserDetailCoinContract.DetailCoinView, OnRefreshListener, OnLoadMoreListener {


    @Autowired(name = "userCoin")
    String userCoin = "";

    private UserDetailCoinAdapter mUserDetailCoinAdapter;
    private int page = 1;
    private View userInfoCoinHeadView;
    @Override
    public int layoutId() {
        return R.layout.mine_activity_detailpoint;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.includePointToolbar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includePointToolbar.baseTvTitle.setText(getString(R.string.mine_coin_detail));
        mUserDetailCoinAdapter = new UserDetailCoinAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.mineRvUserdetailcoin,new LinearLayoutManager(this),mUserDetailCoinAdapter,false);
        userInfoCoinHeadView = LayoutInflater.from(this).inflate(R.layout.mine_detailpoint_head,null);
        ((TextView)userInfoCoinHeadView.findViewById(R.id.tv_head_detailpoint)).setText(userCoin);
        mDatabind.mineDetailpointFreshlayout.setOnRefreshListener(this);
        mDatabind.mineDetailpointFreshlayout.setOnLoadMoreListener(this);
        showLoading(mDatabind.mineDetailpointFreshlayout);

    }


    @Override
    public void initData(){
        mPresenter.requestUserDetailCoin(this,page);
    }


    @Override
    public void reLoadData(){
        mPresenter.requestUserDetailCoin(this,page);
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
    public void setUserDetailCoin(UserDetailCoinListEntity result) {
        showSuccess();
        mDatabind.mineDetailpointFreshlayout.finishRefresh();
        mDatabind.mineDetailpointFreshlayout.finishLoadMore();
        if (result.getDatas().size() > 0) {
            if (page == 1) {
                mDatabind.mineRvUserdetailcoin.removeHeaderView(userInfoCoinHeadView);
                if (mDatabind.mineRvUserdetailcoin.getHeaderCount() == 0) {
                    mDatabind.mineRvUserdetailcoin.addHeaderView(userInfoCoinHeadView);
                }
                mUserDetailCoinAdapter.setNewInstance(result.getDatas());
            } else {
                mUserDetailCoinAdapter.addData(result.getDatas());
            }
            page ++;

        } else {
            mDatabind.mineDetailpointFreshlayout.setEnableLoadMore(false);
        }


    }






    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestUserDetailCoin(this,page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.requestUserDetailCoin(this,page);
        mDatabind.mineDetailpointFreshlayout.setEnableLoadMore(true);
    }
}
