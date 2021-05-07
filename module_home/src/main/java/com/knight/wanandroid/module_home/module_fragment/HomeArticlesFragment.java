package com.knight.wanandroid.module_home.module_fragment;

import android.os.Bundle;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentArticleBinding;
import com.knight.wanandroid.module_home.module_adapter.HomeArticleAdapter;
import com.knight.wanandroid.module_home.module_constants.HomeConstants;
import com.knight.wanandroid.module_home.module_contract.HomeArticleContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
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
    //页码从0开始
    private int currentPage = 0;


    @Override
    protected int layoutId() {
        return R.layout.home_fragment_article;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mHomeArticleAdapter = new HomeArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeArticleBody,new LinearLayoutManager(getActivity()),mHomeArticleAdapter,true);
        mDatabind.homeArticleBody.setAdapter(mHomeArticleAdapter);
        mDatabind.homeArticleFreshlayout.setOnLoadMoreListener(this);

    }

    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData() {
       // showLoadingHud("请求中...");
        if (HomeConstants.ARTICLE_TYPE.equals("全部")) {
            mPresenter.requestAllHomeArticle(currentPage);
        } else {
            mPresenter.requestSearchArticle(currentPage,HomeConstants.ARTICLE_TYPE);
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
        showloadFailure();
    }



    @Override
    public void setAllHomeArticle(HomeArticleListEntity result) {
        loadArticleData(result);
    }

    @Override
    public void setSearchArticle(HomeArticleListEntity result) {
        loadArticleData(result);
    }


    /**
     *
     * 加载文章列表数据
     * @param result
     */
    private void loadArticleData(HomeArticleListEntity result) {
        currentPage = result.getCurPage();
        mDatabind.homeArticleFreshlayout.finishLoadMore();
        // dismissLoadingHud();
        if (currentPage > 1) {
            if (result.getDatas().size() > 0) {
                mHomeArticleAdapter.addData(result.getDatas());
            } else {
                mDatabind.homeArticleFreshlayout.setEnableLoadMore(false);
            }
        } else {
            mHomeArticleAdapter.setNewInstance(result.getDatas());
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestAllHomeArticle(currentPage);
    }
}
