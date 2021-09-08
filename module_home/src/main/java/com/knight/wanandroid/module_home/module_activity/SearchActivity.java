package com.knight.wanandroid.module_home.module_activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_base.util.DataBaseUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_util.dialog.DialogUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeSearchActivityBinding;
import com.knight.wanandroid.module_home.module_adapter.HomeHotKeyAdapter;
import com.knight.wanandroid.module_home.module_adapter.SearchRecordAdapter;
import com.knight.wanandroid.module_home.module_contract.SearchContract;
import com.knight.wanandroid.module_home.module_model.SearchModel;
import com.knight.wanandroid.module_home.module_presenter.SearchPresenter;
import com.wanandroid.knight.library_database.repository.HistroyKeywordsRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 15:32
 * @descript:搜索页面
 */

@Route(path = RoutePathActivity.Home.search)
public final class SearchActivity extends BaseActivity<HomeSearchActivityBinding, SearchPresenter, SearchModel> implements SearchContract.SearchView {

    //热词适配器
    private HomeHotKeyAdapter mHomeHotKeyAdapter;
    //搜索记录适配器
    private SearchRecordAdapter mSearchRecordAdapter;
    private String keyword;


    @Override
    public int layoutId() {
        return R.layout.home_search_activity;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        GradientDrawable cursorDrawable = new GradientDrawable();
        cursorDrawable.setShape(GradientDrawable.RECTANGLE);
        cursorDrawable.setColor(themeColor);
        cursorDrawable.setSize(ScreenUtils.dp2px(2),ScreenUtils.dp2px(2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mDatabind.homeSearchEt.setTextCursorDrawable(cursorDrawable);
        } else {
            SystemUtils.setCursorDrawableColor(mDatabind.homeSearchEt,themeColor);
        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        showLoading(mDatabind.homeSearchhotRv);
        mHomeHotKeyAdapter = new HomeHotKeyAdapter(new ArrayList<>());
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        //方向 主轴为水平方向,起点在左端
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //左对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeSearchhotRv,flexboxLayoutManager,mHomeHotKeyAdapter,false);
        SystemUtils.showDelaySoftKeyBoard(mDatabind.homeSearchEt);
        mSearchRecordAdapter = new SearchRecordAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeSearchhistroyKeywordRv,new LinearLayoutManager(this),mSearchRecordAdapter,false);
        mDatabind.homeSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyword = mDatabind.homeSearchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtils.show(R.string.home_input_content_search);
                    } else {
                        AppConfig.SEARCH_KEYWORD = keyword;
                        ARouterUtils.startActivity(RoutePathActivity.Home.searchResult,"keyword",keyword);
                    }
                    return true;

                }
                return false;
            }
        });

        SystemUtils.seteditTextChangeListener(mDatabind.homeSearchEt,mDatabind.homeTvsearchCancel);
        initClickListener();
    }

    @Override
    public void initData(){
        mPresenter.requestSearchHotkey();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void reLoadData(){
        mPresenter.requestSearchHotkey();

    }

    @Override
    public void onResume(){
        super.onResume();
        queryLocalSearchRecord();

    }


    @Override
    public void showError(String errorMsg) {
        showFailure();
        ToastUtils.show(errorMsg);
    }

    @Override
    public void setSearchHotKey(List<SearchHotKeyEntity> searchHotKeyEntities) {
        showSuccess();
        mHomeHotKeyAdapter.setNewInstance(searchHotKeyEntities);

    }

    public class ProcyClick {
        public void searchByKeyword() {
            if (mDatabind.homeTvsearchCancel.getText().toString().equals(getString(R.string.home_cancel))) {
                    finish();
            } else {
                keyword = mDatabind.homeSearchEt.getText().toString().trim();
                AppConfig.SEARCH_KEYWORD = keyword;
                DataBaseUtils.saveSearchKeyword(keyword);
                ARouterUtils.startActivity(RoutePathActivity.Home.searchResult,"keyword",keyword);
            }
        }

        //删除所有数据
        public void clearAllSearchRecord(){
            DialogUtils.getConfirmDialog(SearchActivity.this, getResources().getString(R.string.home_clearall_searchrecord), (dialog, which) -> {
                HistroyKeywordsRepository.getInstance().deleteAllKeywords();
                queryLocalSearchRecord();
            }, (dialog, which) -> {

            });
        }
    }



    /**
     *
     * 查询本地记录
     */
    private void queryLocalSearchRecord() {
        HistroyKeywordsRepository.getInstance().queryHistroyKeywords(mSearchHistroyKeywordEntities -> {
            if (mSearchHistroyKeywordEntities != null && mSearchHistroyKeywordEntities.size() > 0) {
                mDatabind.homeSearchkeywordHeadRl.setVisibility(View.VISIBLE);
                mDatabind.homeSearchhistroyKeywordRv.setVisibility(View.VISIBLE);
                mSearchRecordAdapter.setNewInstance(mSearchHistroyKeywordEntities);
            } else {
                mDatabind.homeSearchkeywordHeadRl.setVisibility(View.GONE);
                mDatabind.homeSearchhistroyKeywordRv.setVisibility(View.GONE);
            }
        });
    }

    private void initClickListener() {
        //适配器点击监听事件
        mSearchRecordAdapter.addChildClickViewIds(R.id.iv_searchkeyword_delete);
        mSearchRecordAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.iv_searchkeyword_delete) {
                HistroyKeywordsRepository.getInstance().deleteHistroyKeyword(mSearchRecordAdapter.getData().get(position).getId());
                mSearchRecordAdapter.getData().remove(position);
                mSearchRecordAdapter.notifyItemRemoved(position);
                if (mSearchRecordAdapter.getData().size() == 0) {
                    mDatabind.homeSearchkeywordHeadRl.setVisibility(View.GONE);
                }
            }
        });

        mSearchRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                keyword = mSearchRecordAdapter.getData().get(position).getName();
                AppConfig.SEARCH_KEYWORD = keyword;
                ARouterUtils.startActivity(RoutePathActivity.Home.searchResult,"keyword",keyword);
            }
        });
        //热词适配器点击事件
        mHomeHotKeyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                keyword = mHomeHotKeyAdapter.getData().get(position).getName();
                AppConfig.SEARCH_KEYWORD = keyword;
                DataBaseUtils.saveSearchKeyword(keyword);
                ARouterUtils.startActivity(RoutePathActivity.Home.searchResult,"keyword",keyword);
            }
        });


    }
}
