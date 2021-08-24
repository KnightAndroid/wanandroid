package com.knight.wanandroid.module_home.module_activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_base.util.DataBaseUtils;
import com.knight.wanandroid.library_util.SystemUtils;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 15:35
 * @descript:结果搜索界面
 */
@Route(path = RoutePathActivity.Home.searchResult)
public final class SearchResultActivity extends BaseActivity<HomeSearchresultActivityBinding, SearchResultPresenter, SearchResultModel> implements SearchResultContract.SearchResultView,
        OnRefreshListener, OnLoadMoreListener {
    private SearchResultAdapter mSearchResultAdapter;

    private int page = 0;


    @Autowired(name = "keyword")
    String keyword = "";


    @Override
    public int layoutId() {
        return R.layout.home_searchresult_activity;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.setClick(new ProcyClick());
        showLoading(mDatabind.includeSearchresult.baseFreshlayout);
        mDatabind.searchresultEt.setText(keyword);
        mSearchResultAdapter = new SearchResultAdapter();
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeSearchresult.baseBodyRv,new LinearLayoutManager(this),mSearchResultAdapter,true);
        mDatabind.includeSearchresult.baseFreshlayout.setOnLoadMoreListener(this);
        mDatabind.includeSearchresult.baseFreshlayout.setOnRefreshListener(this);
        initListener();
        mDatabind.searchresultIvBack.setOnClickListener(v -> finish());
        SystemUtils.seteditTextChangeListener(mDatabind.searchresultEt,mDatabind.searchresultTvCancel);
        mDatabind.searchresultEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = mDatabind.searchresultEt.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtils.getInstance().showToast(getString(R.string.home_input_content_search));
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
        if (page == 0) {
            mSearchResultAdapter.setNewInstance(result.getDatas());
            if (result.getDatas().size() == 0) {
                showEmptyData();
            }
            if (result.getDatas().size() < 10) {
                mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(false);
            } else {
                page++;
            }
        } else {
            mSearchResultAdapter.addData(result.getDatas());
            if (result.getDatas().size() < 10) {
                mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(false);
            } else {
                page++;
            }
        }

    }

    @Override
    public void collectArticleSuccess(int position) {
        mSearchResultAdapter.getData().get(position).setCollect(true);
        mSearchResultAdapter.notifyItemChanged(position);
    }

    @Override
    public void cancelArticleSuccess(int position) {
        mSearchResultAdapter.getData().get(position).setCollect(false);
        mSearchResultAdapter.notifyItemChanged(position);
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
            if (mDatabind.searchresultTvCancel.getText().toString().equals(getString(R.string.home_cancel))){
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
        DataBaseUtils.saveSearchKeyword(keyword);
        AppConfig.SEARCH_KEYWORD = keyword;
        mDatabind.includeSearchresult.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestSearchResult(page,keyword);
    }


    private void initListener(){
        mSearchResultAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mSearchResultAdapter.getData().get(position).getLink(),
                        mSearchResultAdapter.getData().get(position).getTitle(),
                        mSearchResultAdapter.getData().get(position).getId(),
                        mSearchResultAdapter.getData().get(position).isCollect(),
                        mSearchResultAdapter.getData().get(position).getEnvelopePic(),
                        mSearchResultAdapter.getData().get(position).getDesc(),
                        mSearchResultAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mSearchResultAdapter.getData().get(position).getAuthor()) ?  mSearchResultAdapter.getData().get(position).getShareUser() : mSearchResultAdapter.getData().get(position).getAuthor());
            }
        });


        mSearchResultAdapter.addChildClickViewIds(R.id.base_icon_collect,R.id.base_article_collect);
        mSearchResultAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if(view.getId() == R.id.base_icon_collect || view.getId() == R.id.base_article_collect) {
                    if (mSearchResultAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mSearchResultAdapter.getData().get(position).getId(),position);
                    } else {
                        mPresenter.requestCollectArticle(mSearchResultAdapter.getData().get(position).getId(),position);
                    }
                }
            }
        });

    }

}
