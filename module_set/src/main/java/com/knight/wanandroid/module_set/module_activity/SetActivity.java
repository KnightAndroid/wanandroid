package com.knight.wanandroid.module_set.module_activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.dialog.DialogUtils;
import com.knight.wanandroid.library_widget.RippleAnimation;
import com.knight.wanandroid.module_set.R;
import com.knight.wanandroid.module_set.databinding.SetActivityBinding;
import com.knight.wanandroid.module_set.module_annoation.ColorStyle;
import com.knight.wanandroid.module_set.module_contract.SetContract;
import com.knight.wanandroid.module_set.module_dialog.ColorPickerDialog;
import com.knight.wanandroid.module_set.module_model.SetModel;
import com.knight.wanandroid.module_set.module_presenter.SetPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


@Route(path = RoutePathActivity.Set.Set_pager)
public class SetActivity extends BaseActivity<SetActivityBinding, SetPresenter, SetModel> implements SetContract.SetView {

    //状态栏是否着色
    private boolean statusIsWithTheme;


    private List<View> mChildViews;

    @Override
    public int layoutId() {
        return R.layout.set_activity;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        updateTextColor(themeColor);
        setThemeTextColor();
        if (!isDarkMode) {
            updateBgColor(bgColor);
        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.includeSetToobar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeSetToobar.baseTvTitle.setText(getString(R.string.set_app_name));
        if (ModuleConfig.getInstance().user != null) {
            mDatabind.setRlLogout.setVisibility(View.VISIBLE);
        } else {
            mDatabind.setRlLogout.setVisibility(View.GONE);
        }
        statusIsWithTheme = CacheUtils.getInstance().getStatusBarIsWithTheme();
        mDatabind.setCbStatusTheme.setChecked(statusIsWithTheme);

        mDatabind.setCbStatusTheme.setButtonTintList(ColorUtils.createColorStateList(CacheUtils.getInstance().getThemeColor(), ColorUtils.convertToColorInt("a6a6a6")));
        initDarkMode();
        initListener();
        if (CacheUtils.getInstance().getNormalDark()) {
            mDatabind.setRlTheme.setVisibility(View.GONE);
            mDatabind.setRlBg.setVisibility(View.GONE);
            mDatabind.setRlStatustheme.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Override
    public void logoutSuccess() {
        mDatabind.setRlLogout.setVisibility(View.GONE);
        //退出登录成功
        CacheUtils.getInstance().loginOut();
        ModuleConfig.getInstance().user = null;
        EventBus.getDefault().post(new EventBusUtils.LogoutSuccess());
    }


    public class ProxyClick {

        /**
         * 主题颜色
         */
        public void showThemeDialog() {
            new ColorPickerDialog.Builder(SetActivity.this, CacheUtils.getInstance().getThemeColor(), ColorStyle.THEMECOLOR, getString(R.string.set_recover_themecolor))
                    .setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                        @Override
                        public void onColorPicked(int color) {
                            themeColor = color;
                            CacheUtils.getInstance().setThemeColor(themeColor);
                            updateTextColor(color);
                            GradientDrawable gradientThemeDrawable = new GradientDrawable();
                            gradientThemeDrawable.setShape(GradientDrawable.OVAL);
                            gradientThemeDrawable.setColor(CacheUtils.getInstance().getThemeColor());
                            mDatabind.setShowThemecolor.setBackground(gradientThemeDrawable);
                            EventBus.getDefault().post(new EventBusUtils.changeColor());

                        }

                    }).build().show();
        }


        /**
         * 页面背景颜色
         */
        public void showBgThemeDialog() {
            new ColorPickerDialog.Builder(SetActivity.this, CacheUtils.getInstance().getBgThemeColor(), ColorStyle.BGCOLOR, getString(R.string.set_recover_bgcolor))
                    .setOnColorPickedListener(new ColorPickerDialog.OnColorPickedListener() {
                        @Override
                        public void onColorPicked(int color) {
                            bgColor = color;
                            CacheUtils.getInstance().setBgThemeColor(bgColor);
                            //关键代码
                            RippleAnimation.create(mDatabind.tvBgcolorArrow).setDuration(250).start();
                            updateBgColor(color);
                        }
                    }).build().show();
        }

        public void projectRepository() {
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl", "https://github.com/KnightAndroid/wanandroid")
                    .withString("webTitle", getString(R.string.set_project_repository))
                    .navigation();
        }

        public void goOfficialWebsite() {
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl", "https://www.wanandroid.com/")
                    .withString("webTitle", getString(R.string.set_official_website))
                    .navigation();
        }

        public void Logout() {
            DialogUtils.getConfirmDialog(SetActivity.this, getResources().getString(R.string.set_confirm_logout), (dialog, which) -> {
                mPresenter.requestLogout();
            }, (dialog, which) -> {

            });

        }

        public void goAbout() {
            ARouter.getInstance().build(RoutePathActivity.Mine.About_Pager)
                    .navigation();
        }

        public void goSelectDarkMode() {
            ARouter.getInstance().build(RoutePathActivity.Set.Set_DarkMode)
                    .navigation();
        }

    }

    /**
     * 初始化颜色
     */
    private void initDarkMode() {
        if (CacheUtils.getInstance().getFollowSystem()) {
            mDatabind.setDarkmodeStatus.setText(getString(R.string.set_follow_system));
        } else {
            if (CacheUtils.getInstance().getNormalDark()) {
                mDatabind.setDarkmodeStatus.setText(getString(R.string.set_dark_open));
            } else {
                mDatabind.setDarkmodeStatus.setText(getString(R.string.set_dark_close));
            }
        }
    }


    /**
     * 监听事件
     */
    private void initListener() {
        //状态栏着色
        mDatabind.setCbStatusTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                statusIsWithTheme = isChecked;
                CacheUtils.getInstance().statusBarIsWithTheme(isChecked);
                buttonView.setButtonTintList(ColorUtils.createColorStateList(CacheUtils.getInstance().getThemeColor(), ColorUtils.convertToColorInt("a6a6a6")));
                EventBus.getDefault().post(new EventBusUtils.changeStatusThemeColor(isChecked));
            }
        });
    }


    private void setThemeTextColor() {
        //设置主题颜色
        GradientDrawable gradientThemeDrawable = new GradientDrawable();
        gradientThemeDrawable.setShape(GradientDrawable.OVAL);
        gradientThemeDrawable.setColor(CacheUtils.getInstance().getThemeColor());
        mDatabind.setShowThemecolor.setBackground(gradientThemeDrawable);

    }

    /**
     * 更改主题颜色
     *
     * @param color
     */
    private void updateTextColor(int color) {
        mDatabind.setTvBasic.setTextColor(color);
        mDatabind.setTvCommon.setTextColor(color);
        mDatabind.setTvOther.setTextColor(color);


    }

    /**
     * 更改背景颜色
     *
     * @param color
     */
    private void updateBgColor(int color) {
        mDatabind.setRoot.setBackgroundColor(color);

    }


    /**
     * 查找所有子view
     *
     * @param view
     * @return
     */
    private List<View> getAllChildViews(View view, int color) {
        List<View> allchildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View viewchild = vp.getChildAt(i);
                allchildren.add(viewchild);
                if (viewchild instanceof TextView) {
                    if (viewchild.getTag() != null && viewchild.getTag().equals("oneLevelText")) {
                        ((TextView) viewchild).setTextColor(color);
                    }
                }
                allchildren.addAll(getAllChildViews(viewchild, color));
            }
        }
        return allchildren;
    }


}