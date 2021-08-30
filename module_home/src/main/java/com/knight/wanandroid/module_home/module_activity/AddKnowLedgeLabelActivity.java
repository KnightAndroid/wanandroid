package com.knight.wanandroid.module_home.module_activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeAddlabelActivityBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/6 15:02
 * @descript:
 */
@Route(path = RoutePathActivity.Home.AddKnowledgeLabel)
public final class AddKnowLedgeLabelActivity extends BaseDBActivity<HomeAddlabelActivityBinding> {

    public static final int QUESTCODE = 0x003;
    public static final String LABEL_DATA = "label_data";

    @Override
    public int layoutId() {
        return R.layout.home_addlabel_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeAddLabel.baseTvTitle.setText(getString(R.string.home_addlabel));
        mDatabind.includeAddLabel.baseIvBack.setOnClickListener(v -> {
            finish();
        });
        mDatabind.homeBottomView.setBackgroundColor(CacheUtils.getInstance().getThemeColor());
        mDatabind.includeAddLabel.baseTvRight.setVisibility(View.VISIBLE);
        mDatabind.includeAddLabel.baseTvRight.setText(getString(R.string.base_save));
        mDatabind.includeAddLabel.baseTvRight.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(LABEL_DATA, mDatabind.homeInputlabelEt.getText().toString().trim());
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        ViewSetUtils.setLinstenerInputNumber(mDatabind.homeInputlabelEt, mDatabind.homeTvLabelnumber);
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        GradientDrawable cursorDrawable = new GradientDrawable();
        cursorDrawable.setShape(GradientDrawable.RECTANGLE);
        cursorDrawable.setColor(themeColor);
        cursorDrawable.setSize(ScreenUtils.dp2px(2),ScreenUtils.dp2px(2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mDatabind.homeInputlabelEt.setTextCursorDrawable(cursorDrawable);
        } else {
            SystemUtils.setCursorDrawableColor(mDatabind.homeInputlabelEt,themeColor);
        }

    }

}
