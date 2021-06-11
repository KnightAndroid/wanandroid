package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.JsonUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityUpdaterecordBinding;
import com.knight.wanandroid.module_mine.module_adapter.VersionRecordAdapter;
import com.knight.wanandroid.module_mine.module_entity.VersionRecordEntity;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/1 15:16
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.AppUpdate_Pager)
public class AppUpdateRecordActivity extends BaseDBActivity<MineActivityUpdaterecordBinding> implements OnRefreshListener {

    private VersionRecordAdapter mVersionRecordAdapter;
    @Override
    public int layoutId() {
        return R.layout.mine_activity_updaterecord;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeUpdaterecordToolbar.baseTvTitle.setText(getString(R.string.mine_update_record));
        mDatabind.includeUpdaterecordToolbar.baseIvBack.setOnClickListener(v -> finish());
        mVersionRecordAdapter = new VersionRecordAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeAppUpdaterecord.baseBodyRv,new LinearLayoutManager(this),mVersionRecordAdapter,true);
        mDatabind.includeAppUpdaterecord.baseFreshlayout.setEnableLoadMore(false);
        mDatabind.includeAppUpdaterecord.baseFreshlayout.setOnRefreshListener(this);
        initData();
    }



    @Override
    public void initData(){
        Type type = new TypeToken<List<VersionRecordEntity>>() {}.getType();
        String jsonData = JsonUtils.getJson(this,"versionrecord.json");
        List<VersionRecordEntity> mDataList = GsonUtils.getList(jsonData,type);
        mVersionRecordAdapter.setNewInstance(mDataList);
        mDatabind.includeAppUpdaterecord.baseFreshlayout.finishRefresh();

    }



    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        initData();
    }
}
