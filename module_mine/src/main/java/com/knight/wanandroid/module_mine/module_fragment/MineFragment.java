package com.knight.wanandroid.module_mine.module_fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.dialog.DialogUtils;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineFragmentMineBinding;
import com.knight.wanandroid.module_mine.module_activity.AboutActivity;
import com.knight.wanandroid.module_mine.module_activity.HistoryRecordActivity;
import com.knight.wanandroid.module_mine.module_activity.LoginActivity;
import com.knight.wanandroid.module_mine.module_activity.MyCollectArticleActivity;
import com.knight.wanandroid.module_mine.module_activity.MyShareArticleActivity;
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
            mDatabind.mineIvMessage.setVisibility(View.VISIBLE);
        } else {
            mDatabind.mineRlLogout.setVisibility(View.GONE);
            mDatabind.mineIvMessage.setVisibility(View.GONE);
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
            loadLoading(mDatabind.mineReboundlayoutRoot);
            mPresenter.requestUserInfoCoin();
        }

    }

    @Override
    protected void reLoadData() {
        loadLoading(mDatabind.mineReboundlayoutRoot);
        if (ModuleConfig.getInstance().user != null) {
            mPresenter.requestUserInfoCoin();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setUserInfoCoin(UserInfoCoinEntity userInfoCoinEntity) {
        showSuccess();
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

    @Override
    public void logoutSuccess() {
        //退出登录成功
        CacheUtils.getInstance().loginOut();
        ModuleConfig.getInstance().user = null;
        mDatabind.mineTvUserabbr.setText("");
        mDatabind.mineTvUsername.setText("请登录");
        mDatabind.mineTvLevel.setText("等级 -");
        mDatabind.mineTvRank.setText("排名第 -");
        mDatabind.mineTvPoints.setText("-");
        mDatabind.mineRlLogout.setVisibility(View.GONE);
        mDatabind.mineIvMessage.setVisibility(View.GONE);
        mDatabind.mineIvHead.setBackground(null);
        GlideEngineUtils.getInstance().loadCircleIntLocalPhoto(getActivity(),R.drawable.mine_iv_default_head,mDatabind.mineIvHead);
        EventBus.getDefault().post(new EventBusUtils.LogoutSuccess());

    }


    public class ProcyClick{
        public void gotoLogin(){
            if (ModuleConfig.getInstance().user == null) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        }

        @LoginCheck
        public void goUserCoin(){
            ARouter.getInstance().build(RoutePathActivity.Mine.UserCoin_pager)
                    .withString("userCoin",mDatabind.mineTvPoints.getText().toString()).navigation();
        }

        public void goCoinRank(){
            ARouter.getInstance().build(RoutePathActivity.Mine.UserCoinRank_Pager).navigation();
        }

        @LoginCheck
        public void goMyShareArticles() {
            startActivity(new Intent(getActivity(), MyShareArticleActivity.class));
        }

        public void goHistoryRecords(){
            startActivity(new Intent(getActivity(), HistoryRecordActivity.class));
        }




        @LoginCheck
        public void goMyCollectArticles(){
            startActivity(new Intent(getActivity(), MyCollectArticleActivity.class));
        }

        public void goAbout(){
            startActivity(new Intent(getActivity(), AboutActivity.class));
        }

        public void projectRepository(){
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl","https://github.com/KnightAndroid/wanandroid")
                    .withString("webTitle",getString(R.string.mine_project_repository))
                    .navigation();
        }

        public void goOfficialWebsite(){
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl","https://www.wanandroid.com/")
                    .withString("webTitle",getString(R.string.mine_official_website))
                    .navigation();
        }
        public void Logout(){
            DialogUtils.getConfirmDialog(getActivity(), getResources().getString(R.string.mine_confirm_logout), (dialog, which) -> {
                mPresenter.requestLogout();
            }, (dialog, which) -> {

            }).show();

        }

        public void goMessage() {
            ARouter.getInstance().build(RoutePathActivity.Message.Message_pager).navigation();
        }



    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
        showloadFailure();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess){
        mPresenter.requestUserInfoCoin();
        mDatabind.mineRlLogout.setVisibility(View.VISIBLE);
        mDatabind.mineIvMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
