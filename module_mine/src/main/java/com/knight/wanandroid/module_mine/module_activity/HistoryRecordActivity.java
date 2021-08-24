package com.knight.wanandroid.module_mine.module_activity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_util.dialog.DialogUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.library_widget.floatmenu.FloatMenu;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityHistoryrecordBinding;
import com.knight.wanandroid.module_mine.module_adapter.HistoryRecordAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.wanandroid.knight.library_database.entity.HistoryReadRecordsEntity;
import com.wanandroid.knight.library_database.repository.HistoryReadRecordsRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/8 11:30
 * @descript:
 */

@Route(path = RoutePathActivity.Mine.HistoryRecord_Pager)
public final class HistoryRecordActivity extends BaseDBActivity<MineActivityHistoryrecordBinding> implements OnLoadMoreListener, OnRefreshListener {
    private int endStation = 10;
    private int startPosition = 0;
    private HistoryRecordAdapter mHistoryRecordAdapter;
    private Point point = new Point();
    @Override
    public int layoutId() {
        return R.layout.mine_activity_historyrecord;
    }



    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeHistoryRecord.baseTvTitle.setText(getString(R.string.mine_historyrecord));
        mDatabind.includeHistoryRecord.baseIvBack.setOnClickListener(v->finish());
        mDatabind.includeHistoryRecord.baseTvRight.setVisibility(View.VISIBLE);
        mDatabind.includeHistoryRecord.baseTvRight.setText(getString(R.string.mine_cleanall));
        mHistoryRecordAdapter = new HistoryRecordAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.mineHistoryrecordRv,new LinearLayoutManager(this),mHistoryRecordAdapter,false);
        mDatabind.mineHistoryrecordFreshlayout.setOnRefreshListener(this);
        mDatabind.mineHistoryrecordFreshlayout.setOnLoadMoreListener(this);
        showLoading(mDatabind.mineHistoryrecordFreshlayout);
        initData();
        initListener();

    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
    }


    @Override
    public void initData(){
        startPosition = 0;
        endStation = 10;
        loadData(startPosition,endStation);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        loadData(startPosition,startPosition + 10);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        startPosition = 0;
        endStation = 10;
        loadData(startPosition,endStation);
    }

    @Override
    public void reLoadData(){
        onRefresh(mDatabind.mineHistoryrecordFreshlayout);
    }

    /**
     *
     * 查询本地数据库数据
     * @param start
     * @param end
     */
    private void loadData(int start,int end){
        HistoryReadRecordsRepository.getInstance().queryPartHistoryRecords(start, end, ModuleConfig.getInstance().user == null ? 0 :ModuleConfig.getInstance().user.getId(),new HistoryReadRecordsRepository.OnQueryRecordsSuccessCallBack() {
            @Override
            public void onQueryRecordsSuccessCallBack(List<HistoryReadRecordsEntity> historyReadRecordsEntities) {
                mDatabind.mineHistoryrecordFreshlayout.finishRefresh();
                mDatabind.mineHistoryrecordFreshlayout.finishLoadMore();
                showSuccess();
                if (start == 0) {
                    if (historyReadRecordsEntities == null || historyReadRecordsEntities.size() == 0) {
                        showEmptyData();
                    } else {
                        mHistoryRecordAdapter.setNewInstance(historyReadRecordsEntities);
                        if (historyReadRecordsEntities.size() >= 10) {
                            mDatabind.mineHistoryrecordFreshlayout.setEnableLoadMore(true);
                            startPosition = end;
                        } else {
                            mDatabind.mineHistoryrecordFreshlayout.setEnableLoadMore(false);
                        }
                    }
                } else {
                    mHistoryRecordAdapter.addData(historyReadRecordsEntities);
                    if (historyReadRecordsEntities.size() >= 10) {
                        mDatabind.mineHistoryrecordFreshlayout.setEnableLoadMore(true);
                        startPosition = end;
                    } else {
                        mDatabind.mineHistoryrecordFreshlayout.setEnableLoadMore(false);
                    }
                }

            }
        });
    }


    private void initListener(){
        mHistoryRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mHistoryRecordAdapter.getData().get(position).getWebUrl(),
                        mHistoryRecordAdapter.getData().get(position).getTitle(),
                        mHistoryRecordAdapter.getData().get(position).getUserId(),
                        mHistoryRecordAdapter.getData().get(position).isCollect(),
                        mHistoryRecordAdapter.getData().get(position).getEnvelopePic(),
                        mHistoryRecordAdapter.getData().get(position).getArticledesc(),
                        mHistoryRecordAdapter.getData().get(position).getChapterName(),
                        mHistoryRecordAdapter.getData().get(position).getAuthor());
            }
        });

        mHistoryRecordAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                final FloatMenu floatMenu = new FloatMenu(HistoryRecordActivity.this,view);
                floatMenu.items("删除");
                floatMenu.show(point);
                floatMenu.setOnItemClickListener(new FloatMenu.OnItemClickListener() {
                    @Override
                    public void onClick(View v, int itemPosition) {
                        HistoryReadRecordsRepository.getInstance().deleteHistroyRecord(mHistoryRecordAdapter.getData().get(position).getId());
                        mHistoryRecordAdapter.getData().remove(position);
                        mHistoryRecordAdapter.notifyItemRemoved(position);
                    }
                });
                return false;
            }
        });

        mDatabind.includeHistoryRecord.baseTvRight.setOnClickListener(v -> {
            DialogUtils.getConfirmDialog(HistoryRecordActivity.this, getResources().getString(R.string.mine_cancel_all_historyrecord), (dialog, which) -> {
                HistoryReadRecordsRepository.getInstance().deleteAllHistroyRecord();
                reLoadData();
            }, (dialog, which) -> {

            });
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            point.x = (int) ev.getRawX();
            point.y = (int) ev.getRawY();
        }
        return super.dispatchTouchEvent(ev);
    }

}
