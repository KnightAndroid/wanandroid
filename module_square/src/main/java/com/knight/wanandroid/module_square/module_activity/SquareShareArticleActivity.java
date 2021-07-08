package com.knight.wanandroid.module_square.module_activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.EventBusUtils;
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
