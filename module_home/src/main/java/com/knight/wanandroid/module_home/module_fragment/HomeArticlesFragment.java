package com.knight.wanandroid.module_home.module_fragment;

import android.os.Bundle;
import android.os.Handler;

import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentArticleBinding;
import com.knight.wanandroid.module_home.module_adapter.HomeArticleAdapter;
import com.knight.wanandroid.module_home.module_contract.HomeArticleContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListModel;
import com.knight.wanandroid.module_home.module_model.HomeArticleModel;
import com.knight.wanandroid.module_home.module_presenter.HomeArticlePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:14
 * @descript:
 */
public class HomeArticlesFragment extends BaseFragment<HomeFragmentArticleBinding, HomeArticlePresenter, HomeArticleModel> implements HomeArticleContract.HomeArticleView, OnLoadMoreListener {

    private HomeArticleAdapter mHomeArticleAdapter;


    @Override
    protected int layoutId() {
        return R.layout.home_fragment_article;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mHomeArticleAdapter = new HomeArticleAdapter(new ArrayList<>());
        mDatabind.homeArticlerefreshLayout.setOnLoadMoreListener(this);
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeArticleBody,new LinearLayoutManager(getActivity()),mHomeArticleAdapter,true);
        mDatabind.homeArticleBody.setAdapter(mHomeArticleAdapter);
    }

    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.requestHomeArticle((BaseDBActivity) getActivity());
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatabind.homeArticlerefreshLayout.finishLoadMore();
            }
        },2000);
    }

    @Override
    public void setHomeArticle(HomeArticleListModel result) {
        mHomeArticleAdapter.setNewInstance(result.getDatas());
    }
}
