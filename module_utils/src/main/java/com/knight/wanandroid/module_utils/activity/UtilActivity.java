package com.knight.wanandroid.module_utils.activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_utils.R;
import com.knight.wanandroid.module_utils.adapter.UtilItemAdapter;
import com.knight.wanandroid.module_utils.contract.UtilsContract;
import com.knight.wanandroid.module_utils.databinding.UtilsActivityBinding;
import com.knight.wanandroid.module_utils.entity.UtilsEntity;
import com.knight.wanandroid.module_utils.model.UtilsModel;
import com.knight.wanandroid.module_utils.presenter.UtilsPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


@Route(path = RoutePathActivity.Util.Util_pager)
public class UtilActivity extends BaseActivity<UtilsActivityBinding, UtilsPresenter, UtilsModel> implements UtilsContract.UtilsView, OnRefreshListener, OnLoadMoreListener {



    private UtilItemAdapter mUtilItemAdapter;

    @Override
    public int layoutId() {
        return R.layout.utils_activity;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeUtilsToolbar.baseTvTitle.setText(getString(R.string.utils_title));
        mDatabind.includeUtilsToolbar.baseIvBack.setOnClickListener(v -> finish());
        mUtilItemAdapter = new UtilItemAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeUtilsRv.baseBodyRv,new LinearLayoutManager(this),mUtilItemAdapter,false);
        mDatabind.includeUtilsRv.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeUtilsRv.baseFreshlayout.setEnableLoadMore(false);
        showLoading(mDatabind.includeUtilsRv.baseFreshlayout);
        initListener();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void initData(){
        mPresenter.requestUtilLists();
    }


    @Override
    public void reLoadData(){
        mPresenter.requestUtilLists();
    }

    @Override
    public void showError(String errorMsg) {
        showFailure();
    }

    @Override
    public void setUtilsData(List<UtilsEntity> utilsData) {
        showSuccess();
        mDatabind.includeUtilsRv.baseFreshlayout.finishRefresh();
        mUtilItemAdapter.setNewInstance(utilsData);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestUtilLists();
    }

    private void initListener(){
        mUtilItemAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                        .withString("webUrl",mUtilItemAdapter.getData().get(position).getLink())
                        .withString("webTitle",mUtilItemAdapter.getData().get(position).getTabName())
                        .navigation();
            }
        });
    }
}