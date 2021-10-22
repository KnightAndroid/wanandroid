package com.knight.wanandroid.module_feedback.dialog;

import android.os.Bundle;
import android.view.Gravity;

import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.basefragment.BaseDialogFragment;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.module_feedback.R;
import com.knight.wanandroid.module_feedback.contract.FeedBackContract;
import com.knight.wanandroid.module_feedback.databinding.FeedbackDialogBinding;
import com.knight.wanandroid.module_feedback.module.FeedBackModel;
import com.knight.wanandroid.module_feedback.presenter.FeedBackPresenter;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/10/21 16:16
 * @descript:反馈弹窗
 */
public final class FeedBackDialog extends BaseDialogFragment<FeedbackDialogBinding, FeedBackPresenter, FeedBackModel> implements FeedBackContract.FeedBackView {


    private int errorArticleId;

    public FeedBackDialog() {

    }

    public static FeedBackDialog newInstance(int errorArticleId) {
        FeedBackDialog feedBackDialog = new FeedBackDialog();
        Bundle args = new Bundle();
        args.putInt("errorArticleId", errorArticleId);
        feedBackDialog.setArguments(args);
        return feedBackDialog;
    }

    @Override
    protected int layoutId() {
        return R.layout.feedback_dialog;
    }

    @Override
    protected void initView() {
        mDatabind.setClick(new ProcyClick());
        errorArticleId = getArguments().getInt("errorArticleId");
        mDatabind.tvFeedbackConfim.setTextColor(CacheUtils.getThemeColor());
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    public class ProcyClick {
        /**
         * 反馈报错链接
         */
        @LoginCheck
        public void feedbackArticle() {

            mPresenter.requestfeedBackError(errorArticleId);
        }


        public void cancelFeedback() {
            dismiss();
        }
    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.show(errorMsg);
    }

    @Override
    public void feedBackErrorSuccess() {
        dismiss();
        ToastUtils.show(R.string.base_feedback_success);
    }
}
