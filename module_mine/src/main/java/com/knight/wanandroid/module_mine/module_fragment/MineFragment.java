package com.knight.wanandroid.module_mine.module_fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineFragmentMineBinding;
import com.knight.wanandroid.module_mine.module_activity.LoginActivity;
import com.knight.wanandroid.module_mine.module_contract.MineContract;
import com.knight.wanandroid.module_mine.module_entity.UserInfoCoinEntity;
import com.knight.wanandroid.module_mine.module_model.MineModel;
import com.knight.wanandroid.module_mine.module_presenter.MinePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:01
 * @descript:我的页面
 */
@Route(path = RoutePathFragment.Mine.Mine_Pager)
public class MineFragment extends BaseFragment<MineFragmentMineBinding, MinePresenter, MineModel> implements MineContract.MineView {
    @Override
    protected int layoutId() {
        return R.layout.mine_fragment_mine;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        EventBus.getDefault().register(this);
        if (ModuleConfig.getInstance().user != null) {
            mDatabind.mineTvUsername.setText(ModuleConfig.getInstance().user.getUsername());
            mDatabind.mineRlLogout.setVisibility(View.VISIBLE);
        } else {
            mDatabind.mineRlLogout.setVisibility(View.GONE);
        }
    }


    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData() {
        if (ModuleConfig.getInstance().user != null) {
            mPresenter.requestUserInfoCoin((BaseDBActivity) getActivity());
        }

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
    public void setUserInfoCoin(UserInfoCoinEntity userInfoCoinEntity) {
        //设置头像
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(ColorUtils.getRandColorCode());
        mDatabind.mineIvHead.setBackground(gradientDrawable);
        mDatabind.mineTvUserabbr.setText(userInfoCoinEntity.getUsername().substring(0,1));
        //显示等级 排名 积分
        mDatabind.mineTvLevel.setText("等级 "+userInfoCoinEntity.getLevel());
        mDatabind.mineTvRank.setText("排名第 "+userInfoCoinEntity.getRank());
        mDatabind.mineTvPoints.setText(userInfoCoinEntity.getCoinCount()+"");


    }


    public class ProcyClick{
        public void gotoLogin(){
            if (ModuleConfig.getInstance().user == null) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }
    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess){
        mPresenter.requestUserInfoCoin((BaseDBActivity) getActivity());
        mDatabind.mineRlLogout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
