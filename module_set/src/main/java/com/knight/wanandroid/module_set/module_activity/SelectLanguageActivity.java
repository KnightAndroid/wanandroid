package com.knight.wanandroid.module_set.module_activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.JsonUtils;
import com.knight.wanandroid.library_util.LanguageFontSizeUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetLanguageActivityBinding;
import com.knight.wanandroid.module_set.module_adapter.SelectLanguageAdapter;
import com.knight.wanandroid.module_set.module_entity.LanguageSelectEntity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/10 15:11
 * @descript:多语言选择界面
 */
@Route(path = RoutePathActivity.Set.Set_Language)
public final class SelectLanguageActivity extends BaseDBActivity<SetLanguageActivityBinding> {

    private List<LanguageSelectEntity> mLanguageSelectEntities;
    private String currentLanguage;
    private SelectLanguageAdapter mSelectLanguageAdapter;
    private String currentSelectLanguage;
    @Override
    public int layoutId() {
        return R.layout.set_language_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeSelectlanguageToolbar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeSelectlanguageToolbar.baseTvTitle.setText(getString(R.string.set_more_language));
        mDatabind.includeSelectlanguageToolbar.baseTvRight.setVisibility(View.VISIBLE);
        mDatabind.includeSelectlanguageToolbar.baseTvRight.setText(getString(R.string.base_save));
        mDatabind.includeSelectlanguageToolbar.baseTvRight.setOnClickListener(v -> saveLanguage());
        mSelectLanguageAdapter = new SelectLanguageAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.setRvLanguageSelect,new LinearLayoutManager(this),mSelectLanguageAdapter,true);
        getLanguageData();
        initListener();
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    /**
     *
     * 初始化数据
     */
    private void getLanguageData() {
        currentLanguage = CacheUtils.getLanguageMode();
        //初始化数据
        Type type = new TypeToken<List<LanguageSelectEntity>>() {}.getType();
        String jsonData = JsonUtils.getJson(this,"languageselect.json");
        mLanguageSelectEntities = GsonUtils.getList(jsonData,type);
        for (int i = 0;i < mLanguageSelectEntities.size(); i++) {
            if (currentLanguage.equals(mLanguageSelectEntities.get(i).getEnglishName())) {
                mLanguageSelectEntities.get(i).setSelect(true);
                break;
            }
        }
        mSelectLanguageAdapter.setNewInstance(mLanguageSelectEntities);

    }


    /**
     *
     * 适配器点击事件
     *
     */
    private void initListener() {
        mSelectLanguageAdapter.setOnItemClickListener((adapter, view, position) -> {
            for (int i = 0; i < mLanguageSelectEntities.size();i++) {
                mLanguageSelectEntities.get(i).setSelect(false);
            }
            mLanguageSelectEntities.get(position).setSelect(true);
            mSelectLanguageAdapter.notifyDataSetChanged();
            currentSelectLanguage = mLanguageSelectEntities.get(position).getEnglishName();

        });

    }

    /**
     *
     * 保存语言模式
     */
    private void saveLanguage() {
        CacheUtils.setLanguageType(currentSelectLanguage);
        LanguageFontSizeUtils.setAppLanguage(this);
        SystemUtils.restartApp(this);
    }



}
