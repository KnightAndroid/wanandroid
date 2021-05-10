package com.knight.wanandroid.module_home.module_activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeSearchresultActivityBinding;
import com.knight.wanandroid.module_home.module_adapter.SearchResultAdapter;
import com.knight.wanandroid.module_home.module_constants.HomeConstants;
import com.knight.wanandroid.module_home.module_contract.SearchResultContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_model.SearchResultModel;
import com.knight.wanandroid.module_home.module_presenter.SearchResultPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wanandroid.knight.library_database.entity.SearchHistroyKeywordEntity;
import com.wanandroid.knight.library_database.repository.HistroyKeywordsRepository;

import java.util.List;

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
        mDatabind.setClick(new ProcyClick());
        showLoading(mDatabind.includeSearchresult.baseFreshlayout);
        keyword = getIntent().getStringExtra("keyword");
        mDatabind.searchresultEt.setText(keyword);
        mSearchResultAdapter = new SearchResultAdapter();
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeSearchresult.baseBodyRv,new LinearLayoutManager(this),mSearchResultAdapter,true);
        mDatabind.includeSearchresult.baseFreshlayout.setOnLoadMoreListener(this);
        mDatabind.includeSearchresult.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.searchresultIvBack.setOnClickListener(v -> finish());
        SystemUtils.seteditTextChangeListener(mDatabind.searchresultEt,mDatabind.searchresultTvCancel);
        mDatabind.searchresultEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = mDatabind.searchresultEt.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtils.getInstance().showToast("请输入内容再搜索");
                    } else {
                        searchNewKeywords();
                    }
                    return true;

                }
                return false;
            }
        });

    }

    @Override
    public void initData(){
        mPresenter.requestSearchResult(page,keyword);
    }

    @Override
    public void reLoadData(){
        mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestSearchResult(page,keyword);
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
        mPresenter.requestSearchResult(page,keyword);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestSearchResult(page,keyword);
    }

    public class ProcyClick {
        public void searchByKeyword(){
            if (mDatabind.searchresultTvCancel.getText().toString().equals("取消")){
                finish();
            } else {
                searchNewKeywords();
            }
        }
    }

    private void searchNewKeywords(){
        mDatabind.includeSearchresult.baseFreshlayout.autoRefresh();
        page = 0;
        keyword = mDatabind.searchresultEt.getText().toString();
        saveSearchKeyword(keyword);
        HomeConstants.SEARCH_KEYWORD = keyword;
        mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestSearchResult(page,keyword);
    }

    /**
     *
     * 保存搜索关键词
     * @param keyword
     */
    private void saveSearchKeyword(String keyword) {
        HistroyKeywordsRepository.getInstance().queryHistroyKeywords(new HistroyKeywordsRepository.OnQuerySuccessCallBack() {
            @Override
            public void onQuerySuccessCallBack(List<SearchHistroyKeywordEntity> mSearchHistroyKeywordEntities) {
                long id = 0;
                for (SearchHistroyKeywordEntity searchHistroyKeywordEntity : mSearchHistroyKeywordEntities) {
                    if (TextUtils.equals(searchHistroyKeywordEntity.getName(), keyword)) {
                        id = searchHistroyKeywordEntity.getId();
                        break;
                    }
                }
                if (id != 0) {
                    HistroyKeywordsRepository.getInstance().deleteHistroyKeyword(id);
                }
                HistroyKeywordsRepository.getInstance().insertHistroyKeyword(new SearchHistroyKeywordEntity(keyword));
            }
        });
    }


}
