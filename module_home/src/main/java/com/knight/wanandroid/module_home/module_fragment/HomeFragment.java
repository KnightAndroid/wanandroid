package com.knight.wanandroid.module_home.module_fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_network.EasyLog;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentHomeBinding;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerModel;
import com.knight.wanandroid.module_home.module_model.HomeModel;
import com.knight.wanandroid.module_home.module_presenter.HomePresenter;

import java.util.List;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 10:33
 * @descript:首页fragment
 */


@Route(path = RoutePathFragment.Home.Home_Pager)
public class HomeFragment extends BaseFragment<HomeFragmentHomeBinding, HomePresenter, HomeModel> implements HomeContract.HomeView {
    @Override
    public int layoutId(){
        return R.layout.home_fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
         mDatabind.setClick(new ProcyClick());

    }


    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData(){
        mPresenter.requestBannerData((BaseDBActivity) getActivity());
    }


    @Override
    public void setBannerData(List<BannerModel> result) {
        mLoadService.showSuccess();
        EasyLog.print(result.get(0).getImagePath() +"1111212");
    }

    @Override
    public void setListData() {

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

    public class ProcyClick{

        public void testClick(){

        }

    }



}
