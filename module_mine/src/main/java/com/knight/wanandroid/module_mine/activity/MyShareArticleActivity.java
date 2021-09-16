package com.knight.wanandroid.module_mine.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_util.dialog.DialogUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityMyshareBinding;
import com.knight.wanandroid.module_mine.adapter.MyShareArticleAdapter;
import com.knight.wanandroid.module_mine.contract.MyShareArticleContract;
import com.knight.wanandroid.module_mine.entity.MyShareEntity;
import com.knight.wanandroid.module_mine.model.MyShareArticleModel;
import com.knight.wanandroid.module_mine.presenter.MyShareArticlePresenter;
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
public final class MyShareArticleActivity extends BaseActivity<MineActivityMyshareBinding, MyShareArticlePresenter, MyShareArticleModel> implements MyShareArticleContract.MyShareArticleView, OnLoadMoreListener, OnRefreshListener {

    private int page = 1;
    private MyShareArticleAdapter mMyShareArticleAdapter;
    @Override
    public int layoutId() {
        return R.layout.mine_activity_myshare;

    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeMineshareToolbar.baseTvTitle.setText(getString(R.string.mine_me_shareArticles));
        mDatabind.includeMineshareToolbar.baseIvBack.setOnClickListener(v -> finish());
        showLoading(mDatabind.includeMineSharerefreshalayout.baseFreshlayout);
        mMyShareArticleAdapter = new MyShareArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeMineSharerefreshalayout.baseBodyRv,new LinearLayoutManager(this),mMyShareArticleAdapter,false);
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeMineSharerefreshalayout.baseFreshlayout.setOnLoadMoreListener(this);
        initListener();
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
        ToastUtils.show(errorMsg);
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
    public void deleteArticleSuccess(int position) {
        mMyShareArticleAdapter.getData().remove(position);
        mMyShareArticleAdapter.notifyItemRemoved(position);
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


    private void initListener(){
        mMyShareArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mMyShareArticleAdapter.getData().get(position).getLink(),
                        mMyShareArticleAdapter.getData().get(position).getTitle(),
                        mMyShareArticleAdapter.getData().get(position).getId(),
                        mMyShareArticleAdapter.getData().get(position).isCollect(),
                        mMyShareArticleAdapter.getData().get(position).getEnvelopePic(),
                        mMyShareArticleAdapter.getData().get(position).getDesc(),
                        mMyShareArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mMyShareArticleAdapter.getData().get(position).getAuthor()) ? mMyShareArticleAdapter.getData().get(position).getShareUser() : mMyShareArticleAdapter.getData().get(position).getAuthor());
            }
        });

        mMyShareArticleAdapter.addChildClickViewIds(R.id.mine_iv_delete);
        mMyShareArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.mine_iv_delete) {
                    DialogUtils.getConfirmDialog(MyShareArticleActivity.this, getResources().getString(R.string.mine_confirm_deletearticle), (dialog, which) -> {
                        mPresenter.requestDeleteCollectArticle(mMyShareArticleAdapter.getData().get(position).getId(),position);
                    }, (dialog, which) -> {

                    });

                }
            }
        });


    }
}
