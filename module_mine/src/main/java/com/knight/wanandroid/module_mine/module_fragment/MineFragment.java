package com.knight.wanandroid.module_mine.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineFragmentMineBinding;
import com.knight.wanandroid.module_mine.module_contract.MineContract;
import com.knight.wanandroid.module_mine.module_model.MineModel;
import com.knight.wanandroid.module_mine.module_presenter.MinePresenter;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:01
 * @descript:我的页面
 */
@Route(path = RoutePathFragment.Mine.Mine_pager)
public class MineFragment extends BaseFragment<MineFragmentMineBinding, MinePresenter, MineModel> implements MineContract.MineView {
    @Override
    protected int layoutId() {
        return R.layout.mine_fragment_mine;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void reLoadData() {

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
}
