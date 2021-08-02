package com.knight.wanandroid.module_set.module_activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.JsonUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetDarkmodeActivityBinding;
import com.knight.wanandroid.module_set.module_adapter.SelectDarkModeAdapter;
import com.knight.wanandroid.module_set.module_entity.DarkSelectEntity;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/19 14:21
 * @descript:深色模式选择界面
 */
@Route(path = RoutePathActivity.Set.Set_DarkMode)
public class DarkModeActivity extends BaseDBActivity<SetDarkmodeActivityBinding> {

    private boolean isDark = false;
    private boolean isFollowSystem = false;
    private SelectDarkModeAdapter mSelectDarkModeAdater;
    private List<DarkSelectEntity> mDataList;


    @Override
    public int layoutId() {
        return R.layout.set_darkmode_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.includeDarkmodeToolbar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeDarkmodeToolbar.baseTvTitle.setText(getString(R.string.set_dark_mode));
        mDatabind.includeDarkmodeToolbar.baseTvRight.setVisibility(View.VISIBLE);
        mDatabind.includeDarkmodeToolbar.baseTvRight.setText(getString(R.string.base_save));
        mDatabind.includeDarkmodeToolbar.baseTvRight.setOnClickListener(v -> confim());
        mSelectDarkModeAdater = new SelectDarkModeAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.setRvDarkmodelSelect,new LinearLayoutManager(this),mSelectDarkModeAdater,true);
        getDarkModeData();
        initListener();
    }



    private void getDarkModeData() {
        mDatabind.setCbSelectSystem.setChecked(CacheUtils.getInstance().getFollowSystem());
        if (CacheUtils.getInstance().getFollowSystem()) {
            mDatabind.setRlManualSelect.setVisibility(View.GONE);
            mDatabind.setTvManualSystem.setVisibility(View.GONE);
        } else {
            mDatabind.setRlManualSelect.setVisibility(View.VISIBLE);
            mDatabind.setTvManualSystem.setVisibility(View.VISIBLE);
        }
        //初始化标签
        Type type = new TypeToken<List<DarkSelectEntity>>() {}.getType();
        String jsonData = JsonUtils.getJson(this,"darkselect.json");
        mDataList = GsonUtils.getList(jsonData,type);
        //如果是普通模式
        if (CacheUtils.getInstance().getNormalDark()) {
            mDataList.get(0).setSelect(false);
            mDataList.get(1).setSelect(true);
        } else {
            mDataList.get(0).setSelect(true);
            mDataList.get(1).setSelect(false);
        }

        mSelectDarkModeAdater.setNewInstance(mDataList);

    }

    private void initListener() {
//        mDatabind.setCbSelectSystem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    mDatabind.setRlManualSelect.setVisibility(View.GONE);
//                    mDatabind.setTvManualSystem.setVisibility(View.GONE);
//                } else {
//                    mDatabind.setRlManualSelect.setVisibility(View.VISIBLE);
//                    mDatabind.setTvManualSystem.setVisibility(View.VISIBLE);
//                }
//                isFollowSystem = isChecked;
//            }
//        });


        mSelectDarkModeAdater.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                for (int i = 0; i < mDataList.size(); i++) {
                    mDataList.get(i).setSelect(false);
                }
                mDataList.get(position).setSelect(true);
                //保存所选模式
                isDark = mDataList.get(position).isDark();
                mSelectDarkModeAdater.notifyDataSetChanged();
            }
        });
    }



    private void confim() {
        //保存是否跟随系统
        CacheUtils.getInstance().setFollowSystem(isFollowSystem);
        if (isFollowSystem) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            if (Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                //深色模式
                CacheUtils.getInstance().setNormalDark(true);
            } else {
                CacheUtils.getInstance().setNormalDark(false);
            }
            
        } else {
            CacheUtils.getInstance().setNormalDark(isDark);
            if (CacheUtils.getInstance().getNormalDark()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
        recreate();
        EventBus.getDefault().post(new EventBusUtils.changeColor());



    }


    public class ProxyClick {
        private CompoundButton.OnCheckedChangeListener onCheckedChangeAllowSystem = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mDatabind.setRlManualSelect.setVisibility(View.GONE);
                    mDatabind.setTvManualSystem.setVisibility(View.GONE);
                } else {
                    mDatabind.setRlManualSelect.setVisibility(View.VISIBLE);
                    mDatabind.setTvManualSystem.setVisibility(View.VISIBLE);
                }
                isFollowSystem = isChecked;
            }
        };

        @NotNull
        public final CompoundButton.OnCheckedChangeListener getOnCheckedChangeAllowSystem() {
            return onCheckedChangeAllowSystem;
        }

        public final void setOnCheckedChangeAllowSystem(@NotNull CompoundButton.OnCheckedChangeListener var1) {
            this.onCheckedChangeAllowSystem = var1;
        }

    }



}
