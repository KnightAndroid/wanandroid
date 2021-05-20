package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.library_widget.slidinglayout.SlidingUpPanelLayout;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityOthershareBinding;
import com.knight.wanandroid.module_mine.module_adapter.OtherShareArticleAdapter;
import com.knight.wanandroid.module_mine.module_contract.OtherArticleContract;
import com.knight.wanandroid.module_mine.module_entity.OtherShareArticleListEntity;
import com.knight.wanandroid.module_mine.module_model.OtherShareArticleModel;
import com.knight.wanandroid.module_mine.module_presenter.OtherShareArticlePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by luguian
 * @organize 车童网
 * @Date 2021/5/20 17:29
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.OtherShareArticle_Pager)
public class OtherShareArticleActivity extends BaseActivity<MineActivityOthershareBinding, OtherShareArticlePresenter, OtherShareArticleModel> implements
        OtherArticleContract.OtherShareArticleView, OnRefreshListener, OnLoadMoreListener {


    private int uid;
    private int page = 1;
    private OtherShareArticleAdapter mOtherShareArticleAdapter;


    @Override
    public int layoutId() {
        return R.layout.mine_activity_othershare;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeOtherSharearticle.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeOtherSharearticle.baseFreshlayout.setOnLoadMoreListener(this);
        uid = getIntent().getIntExtra("uid",0);
        mOtherShareArticleAdapter = new OtherShareArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeOtherSharearticle.baseBodyRv,new LinearLayoutManager(this),mOtherShareArticleAdapter,true);
        mDatabind.mineSlidupPanellayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });

        mDatabind.mineSlidupPanellayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabind.mineSlidupPanellayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });
    }


    @Override
    public void initData(){
        mPresenter.requestOtherShareArticle(uid,page);
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
    }

    @Override
    public void setOtherShareArticle(OtherShareArticleListEntity result) {
        mDatabind.includeOtherSharearticle.baseFreshlayout.finishRefresh();
        mDatabind.includeOtherSharearticle.baseFreshlayout.finishLoadMore();
        if (result.getShareArticles().getDatas().size() > 0) {
            if (page == 1) {
                mOtherShareArticleAdapter.setNewInstance(result.getShareArticles().getDatas());
            } else {
                mOtherShareArticleAdapter.addData(result.getShareArticles().getDatas());
            }
            page ++;
        } else {
            mDatabind.includeOtherSharearticle.baseFreshlayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestOtherShareArticle(uid,page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.requestOtherShareArticle(uid,page);
        mDatabind.includeOtherSharearticle.baseFreshlayout.setEnableLoadMore(true);
    }
}
