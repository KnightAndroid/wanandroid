package com.knight.wanandroid.module_square.module_activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.databinding.SquareActivitySharearticleBinding;
import com.knight.wanandroid.module_square.module_contract.SquareShareArticleContact;
import com.knight.wanandroid.module_square.module_model.SquareShareArticleModel;
import com.knight.wanandroid.module_square.module_presenter.SquareShareArticlePresenter;

import org.greenrobot.eventbus.EventBus;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/7 16:02
 * @descript:
 */

@Route(path = RoutePathActivity.Square.ShareArticle)
public class SquareShareArticleActivity extends BaseActivity<SquareActivitySharearticleBinding, SquareShareArticlePresenter, SquareShareArticleModel> implements SquareShareArticleContact.SquareShareArticleView {

    String title = "";
    String link = "";
    @Override
    public int layoutId() {
        return R.layout.square_activity_sharearticle;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(CacheUtils.getInstance().getThemeColor());
        gradientDrawable.setCornerRadius(ScreenUtils.dp2px(45));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDatabind.squareTvArticle.setBackground(gradientDrawable);
        } else {
            mDatabind.squareTvArticle.setBackgroundDrawable(gradientDrawable);
        }

        GradientDrawable cursorDrawable = new GradientDrawable();
        cursorDrawable.setShape(GradientDrawable.RECTANGLE);
        cursorDrawable.setColor(themeColor);
        cursorDrawable.setSize(ScreenUtils.dp2px(2),ScreenUtils.dp2px(2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            mDatabind.squareSharearticleEt.setTextCursorDrawable(cursorDrawable);
            mDatabind.squareSharearticleLink.setTextCursorDrawable(cursorDrawable);
        } else {
            SystemUtils.setCursorDrawableColor(mDatabind.squareSharearticleEt,themeColor);
            SystemUtils.setCursorDrawableColor(mDatabind.squareSharearticleLink,themeColor);
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        mDatabind.squareSharearticleToolbar.baseTvTitle.setText("分享");
        mDatabind.squareSharearticleToolbar.baseIvBack.setOnClickListener(v -> finish());

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
    public void successShareArticle() {
        ToastUtils.getInstance().showToast("分享成功");
        EventBus.getDefault().post(new EventBusUtils.ShareArticleSuccess());
        finish();
    }


    public class ProcyClick{
        public void shareArticle(){
            if (validateArticleMessage()) {
                mPresenter.requestShareArticle(title,link);
            }
        }
    }

    /**
     *
     * 校验文章信息和链接
     * @return
     */
    private boolean validateArticleMessage(){
        boolean validFlag = true;
        title = mDatabind.squareSharearticleEt.getText().toString().trim();
        link = mDatabind.squareSharearticleLink.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.getInstance().showToast("文章标题不能为空");
            validFlag = false;
        } else if (TextUtils.isEmpty(link)) {
            ToastUtils.getInstance().showToast("文章链接不能为空");
            validFlag = false;
        } else if (!link.startsWith("http://") && !link.startsWith("https://")) {
            ToastUtils.getInstance().showToast("文章链接必须是http或者是https开头");
            validFlag = false;
        }

        return validFlag;
    }
}
