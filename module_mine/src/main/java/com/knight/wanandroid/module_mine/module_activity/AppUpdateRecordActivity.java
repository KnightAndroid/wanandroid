package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityUpdaterecordBinding;
import com.knight.wanandroid.module_mine.module_adapter.VersionRecordAdapter;
import com.knight.wanandroid.module_mine.module_contract.AppUpdateRecordContract;
import com.knight.wanandroid.module_mine.module_entity.AppUpdateRecordListEntity;
import com.knight.wanandroid.module_mine.module_model.AppUpdateRecordModel;
import com.knight.wanandroid.module_mine.module_presenter.AppUpdateRecordPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/1 15:16
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.AppUpdate_Pager)
public final class AppUpdateRecordActivity extends BaseActivity<MineActivityUpdaterecordBinding, AppUpdateRecordPresenter, AppUpdateRecordModel> implements AppUpdateRecordContract.AppUpdateRecordView,OnRefreshListener {

    private VersionRecordAdapter mVersionRecordAdapter;
    @Override
    public int layoutId() {
        return R.layout.mine_activity_updaterecord;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeUpdaterecordToolbar.baseTvTitle.setText(getString(R.string.mine_update_record));
        mDatabind.includeUpdaterecordToolbar.baseIvBack.setOnClickListener(v -> finish());
        mVersionRecordAdapter = new VersionRecordAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeAppUpdaterecord.baseBodyRv,new LinearLayoutManager(this),mVersionRecordAdapter,true);
        mDatabind.includeAppUpdaterecord.baseFreshlayout.setEnableLoadMore(false);
        mDatabind.includeAppUpdaterecord.baseFreshlayout.setOnRefreshListener(this);
        showLoading(mDatabind.includeAppUpdaterecord.baseFreshlayout);
    }



    @Override
    public void initData(){
        mPresenter.requestAppVersion();

    }


    @Override
    public void reLoadData(){
        super.reLoadData();
        initData();
    }





    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        initData();
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
    public void getAppRecordVersion(AppUpdateRecordListEntity appUpdateRecordListEntity) {
        showSuccess();
        if (appUpdateRecordListEntity.getDatas() == null || appUpdateRecordListEntity.getDatas().size() == 0) {
            showEmptyData();
        } else {
            mVersionRecordAdapter.setNewInstance(appUpdateRecordListEntity.getDatas());
            mDatabind.includeAppUpdaterecord.baseFreshlayout.finishRefresh();
        }

    }
}
