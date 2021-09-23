package com.knight.wanandroid.module_set.module_activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_widget.ChangeSizeView;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetChangetextsizeActivityBinding;

import me.jessyan.autosize.AutoSize;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/20 17:02
 * @descript:
 */
@Route(path = RoutePathActivity.Set.Set_ChangeTextSize)
public final class ChangeTextSizeActivity extends BaseDBActivity<SetChangetextsizeActivityBinding> {

    /**
     *
     * 字体缩放因子
     */
    private float fontSizeScale;

    private int position;

    @Override
    public int layoutId() {
        return R.layout.set_changetextsize_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeChangetextToolbar.baseTvRight.setVisibility(View.VISIBLE);
        mDatabind.includeChangetextToolbar.baseTvRight.setText(R.string.base_save);
        fontSizeScale = CacheUtils.getSystemFontSize();
        position = (int)((fontSizeScale - 0.875) / 0.125);
        mDatabind.setCsvFontSize.setDefaultPosition(position);
        changeSize(position);
        mDatabind.includeChangetextToolbar.baseTvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.setSystemFontSize(fontSizeScale);
                if (fontSizeScale == 1.0f && !AutoSize.checkInit()) {
                    AutoSize.checkAndInit(ChangeTextSizeActivity.this.getApplication());
                }
                SystemUtils.restartApp(ChangeTextSizeActivity.this);
            }
        });
        mDatabind.setCsvFontSize.setChangeCallbackListener(new ChangeSizeView.OnChangeCallbackListener() {
            @Override
            public void onChangeListener(int position) {
                changeSize(position);
                changeTitleSize();
            }
        });
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }


    /**
     *
     * 改变字体大小
     * @param position 位置
     */
    private void changeSize(int position) {
        int dimension = getResources().getDimensionPixelSize(R.dimen.base_dimen_16);
        //根据position 获取字体倍数
        fontSizeScale = (float) (0.875 + 0.125 * position);
        //放大后或者缩小的sp单位
        int resultTextSize = (int)(fontSizeScale * (int) ScreenUtils.px2sp(dimension));
        mDatabind.setTvSample.setTextSize(resultTextSize);

    }

    /**
     *
     * 改变标题字体大小
     */
    private void changeTitleSize() {
        int titleBaseDimension = getResources().getDimensionPixelSize(R.dimen.base_dimen_18);
        int titleRightDimension = getResources().getDimensionPixelSize(R.dimen.base_dimen_15);
        int titleCenterTextSize = (int)(fontSizeScale * (int) ScreenUtils.px2sp(titleBaseDimension));
        int titleRightTextSize = (int)(fontSizeScale * (int) ScreenUtils.px2sp(titleRightDimension));
        mDatabind.includeChangetextToolbar.baseTvTitle.setTextSize(titleCenterTextSize);
        mDatabind.includeChangetextToolbar.baseTvRight.setTextSize(titleRightTextSize);
    }


    /**
     * 用这个方法 为了初始化选中字体缩放因子 保证基准是以1 调整字体大小 不然基准是以缩放因子
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res != null) {
            Configuration config = res.getConfiguration();
            if (config != null && config.fontScale != 1.0f) {
                config.fontScale = 1.0f;
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
        }
        return res;
    }

}
