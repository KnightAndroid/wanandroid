package com.knight.wanandroid.module_square.module_presenter;

import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.module_square.module_contract.SquareQuestionContract;
import com.knight.wanandroid.module_square.module_entity.SquareQuestionListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/17 15:02
 * @descript:
 */
public class SquareQuestionPresenter extends SquareQuestionContract.SquareQuestionDataPresenter {
    @Override
    public void requestSquareQuestion(int page) {
        final SquareQuestionContract.SquareQuestionView mView = getView();
        if (mView == null) {
            return;
        }

        mModel.requestQuestions((BaseFragment) mView, page, new MvpListener<SquareQuestionListEntity>() {
            @Override
            public void onSuccess(SquareQuestionListEntity data) {
                mView.setSquareQuestionData(data);
            }

            @Override
            public void onError(String errorMsg) {
                mView.showError(errorMsg);
            }
        });
    }
}
