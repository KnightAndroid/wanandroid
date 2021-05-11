package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityCollectarticlesBinding;
import com.knight.wanandroid.module_mine.module_adapter.MyCollectArticleAdapter;
import com.knight.wanandroid.module_mine.module_contract.MyCollectArticleContract;
import com.knight.wanandroid.module_mine.module_entity.MyCollectArticleListEntity;
import com.knight.wanandroid.module_mine.module_model.MyCollectArticleModel;
import com.knight.wanandroid.module_mine.module_presenter.MyCollectArticlePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/11 16:57
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.MyCollectArticle_Pager)
public class MyCollectArticleActivity extends BaseActivity<MineActivityCollectarticlesBinding, MyCollectArticlePresenter, MyCollectArticleModel> implements MyCollectArticleContract.MyCollectArticleView, OnRefreshListener, OnLoadMoreListener {
    private int page = 0;
    private MyCollectArticleAdapter mMyCollectArticleAdapter;
    @Override
    public int layoutId() {
        return R.layout.mine_activity_collectarticles;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeMycollectToolbar.baseTvTitle.setText(getString(R.string.mine_me_collect));
        mDatabind.includeMycollectToolbar.baseIvBack.setOnClickListener(v->finish());
        mMyCollectArticleAdapter = new MyCollectArticleAdapter();
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeMineCollectfreshalayout.baseBodyRv,new LinearLayoutManager(this),mMyCollectArticleAdapter,false);
        mDatabind.includeMineCollectfreshalayout.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeMineCollectfreshalayout.baseFreshlayout.setOnLoadMoreListener(this);
        showLoading(mDatabind.includeMineCollectfreshalayout.baseFreshlayout);

    }

    @Override
    public void initData(){
        mPresenter.requestCollectArticles(page);
    }

    @Override
    public void reLoadData(){
        page = 0;
        mDatabind.includeMineCollectfreshalayout.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestCollectArticles(page);
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
    public void setCollectArticles(MyCollectArticleListEntity myCollectArticleListEntity) {
        showSuccess();
        mDatabind.includeMineCollectfreshalayout.baseFreshlayout.finishRefresh();
        mDatabind.includeMineCollectfreshalayout.baseFreshlayout.finishLoadMore();
        if (myCollectArticleListEntity.getDatas().size() > 0) {
            if (page == 0) {
                mMyCollectArticleAdapter.setNewInstance(myCollectArticleListEntity.getDatas());
                if (myCollectArticleListEntity.getDatas().size() <= 10) {
                    mDatabind.includeMineCollectfreshalayout.baseFreshlayout.setEnableLoadMore(false);
                }
            } else {
                mMyCollectArticleAdapter.addData(myCollectArticleListEntity.getDatas());
            }
            page ++;
        } else {
            if (page == 0) {
                showEmptyData();
            }
            mDatabind.includeMineCollectfreshalayout.baseFreshlayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestCollectArticles(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        mDatabind.includeMineCollectfreshalayout.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestCollectArticles(page);
    }
}
