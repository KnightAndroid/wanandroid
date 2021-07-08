package com.knight.wanandroid.module_square.module_contract;

import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.listener.MvpListener;
import com.knight.wanandroid.library_base.model.BaseModel;
import com.knight.wanandroid.library_base.presenter.BasePresenter;
import com.knight.wanandroid.library_base.view.BaseView;
import com.knight.wanandroid.module_square.module_entity.SquareQuestionListEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/17 14:35
 * @descript:
 */
public interface SquareQuestionContract {
    interface SquareQuestionView extends BaseView {
        void setSquareQuestionData(SquareQuestionListEntity squareQuestionListEntity);
    }

    interface SquareQuestionModel extends BaseModel {
        void requestQuestions(BaseFragment fragment, int page, MvpListener mvpListener);
    }

    abstract class SquareQuestionDataPresenter extends BasePresenter<SquareQuestionModel, SquareQuestionView> {

        public abstract void requestSquareQuestion(int page);
    }







}
