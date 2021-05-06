package com.knight.wanandroid.module_home.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeSearchresultActivityBinding;
import com.knight.wanandroid.module_home.module_adapter.SearchResultAdapter;
import com.knight.wanandroid.module_home.module_contract.SearchResultContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_model.SearchResultModel;
import com.knight.wanandroid.module_home.module_presenter.SearchResultPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 15:35
 * @descript:结果搜索界面
 */
@Route(path = RoutePathActivity.Home.searchResult)
public class SearchResultActivity extends BaseActivity<HomeSearchresultActivityBinding, SearchResultPresenter, SearchResultModel> implements SearchResultContract.SearchResultView,
        OnRefreshListener, OnLoadMoreListener {
    private SearchResultAdapter mSearchResultAdapter;
    private String keyword;
    private int page = 0;


    @Override
    public int layoutId() {
        return R.layout.home_searchresult_activity;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        showLoading(mDatabind.includeSearchresult.baseFreshlayout);
        keyword = getIntent().getStringExtra("keyword");
        mSearchResultAdapter = new SearchResultAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeSearchresult.baseBodyRv,new LinearLayoutManager(this),mSearchResultAdapter,true);
        mDatabind.includeSearchresult.baseFreshlayout.setOnLoadMoreListener(this);
        mDatabind.includeSearchresult.baseFreshlayout.setOnRefreshListener(this);
    }

    @Override
    public void initData(){
        mPresenter.requestSearchResult(this,page,keyword);
    }

    @Override
    public void reLoadData(){
        mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestSearchResult(this,page,keyword);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        showFailure();
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void setSearchResultData(HomeArticleListEntity result) {
        showSuccess();
        mDatabind.includeSearchresult.baseFreshlayout.finishLoadMore();
        mDatabind.includeSearchresult.baseFreshlayout.finishRefresh();
        if (result.getDatas().size() > 0) {

            if (page == 0) {
                mSearchResultAdapter.setNewInstance(result.getDatas());
            } else {
                mSearchResultAdapter.addData(result.getDatas());
            }
            page ++ ;
        } else {
            if(page == 0) {
                showEmptyData();
            }
            mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(false);
        }

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestSearchResult(this,page,keyword);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestSearchResult(this,page,keyword);
    }
}
