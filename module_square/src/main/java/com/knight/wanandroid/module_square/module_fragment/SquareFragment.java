package com.knight.wanandroid.module_square.module_fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.databinding.SquareFragmentSquareBinding;
import com.knight.wanandroid.module_square.module_contact.SquareContact;
import com.knight.wanandroid.module_square.module_model.SquareModel;
import com.knight.wanandroid.module_square.module_presenter.SquarePresenter;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:46
 * @descript:
 */
@Route(path = RoutePathFragment.Square.Square_pager)
public class SquareFragment extends BaseFragment<SquareFragmentSquareBinding, SquarePresenter, SquareModel> implements SquareContact.SquareView {
    @Override
    protected int layoutId() {
        return R.layout.square_fragment_square;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
    }

    @Override
    protected void reLoadData() {

    }

    @Override
    public void setNewShareData() {

    }

    @Override
    public void setShareArticle() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    public class ProcyClick{

        public void squareClick(){

            //点击事件
            Toast.makeText(getActivity(),"广场界面",Toast.LENGTH_SHORT).show();
        }

    }
}
