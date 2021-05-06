package com.knight.wanandroid.module_home.module_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.knight.wanandroid.library_base.activity.BaseActivity;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeSearchActivityBinding;
import com.knight.wanandroid.module_home.module_adapter.HomeHotKeyAdapter;
import com.knight.wanandroid.module_home.module_constants.HomeConstants;
import com.knight.wanandroid.module_home.module_contract.SearchContract;
import com.knight.wanandroid.module_home.module_model.SearchModel;
import com.knight.wanandroid.module_home.module_presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/6 15:32
 * @descript:搜索页面
 */

@Route(path = RoutePathActivity.Home.search)
public class SearchActivity extends BaseActivity<HomeSearchActivityBinding, SearchPresenter, SearchModel> implements SearchContract.SearchView {

    private HomeHotKeyAdapter mHomeHotKeyAdapter;

    @Override
    public int layoutId() {
        return R.layout.home_search_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showLoading(mDatabind.homeSearchhotRv);
        mHomeHotKeyAdapter = new HomeHotKeyAdapter(new ArrayList<>());
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        //方向 主轴为水平方向,起点在左端
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //左对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeSearchhotRv,flexboxLayoutManager,mHomeHotKeyAdapter,false);
        SystemUtils.showDelaySoftKeyBoard(mDatabind.homeSearchEt);
        mDatabind.homeSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = mDatabind.homeSearchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(keyword)) {
                        ToastUtils.getInstance().showToast("请输入内容再搜索");
                    } else {
                        HomeConstants.SEARCH_KEYWORD = keyword;
                        startActivity(new Intent(SearchActivity.this,SearchResultActivity.class).putExtra("keyword",keyword));
                    }
                    return true;

                }
                return false;
            }
        });
    }

    @Override
    public void initData(){
        mPresenter.requestSearchHotkey(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void reLoadData(){
        mPresenter.requestSearchHotkey(this);

    }

    @Override
    public void showError(String errorMsg) {
        showFailure();
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void setSearchHotKey(List<SearchHotKeyEntity> searchHotKeyEntities) {
        showSuccess();
        mHomeHotKeyAdapter.setNewInstance(searchHotKeyEntities);

    }
}
