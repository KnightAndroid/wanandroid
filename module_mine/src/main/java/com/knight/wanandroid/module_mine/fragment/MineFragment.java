package com.knight.wanandroid.module_mine.fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_biometric.control.BiometricControl;
import com.knight.wanandroid.library_common.constant.MMkvConstants;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_common.utils.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.imageengine.ImageLoader;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.activity.HistoryRecordActivity;
import com.knight.wanandroid.module_mine.activity.LoginActivity;
import com.knight.wanandroid.module_mine.activity.MyCollectArticleActivity;
import com.knight.wanandroid.module_mine.activity.MyShareArticleActivity;
import com.knight.wanandroid.module_mine.activity.QuickLoginActivity;
import com.knight.wanandroid.module_mine.contract.MineContract;
import com.knight.wanandroid.module_mine.databinding.MineFragmentMineBinding;
import com.knight.wanandroid.library_base.entity.LoginEntity;
import com.knight.wanandroid.module_mine.entity.UserInfoCoinEntity;
import com.knight.wanandroid.module_mine.entity.UserInfoMessageEntity;
import com.knight.wanandroid.module_mine.model.MineModel;
import com.knight.wanandroid.module_mine.presenter.MinePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/14 19:01
 * @descript:我的页面
 */
@Route(path = RoutePathFragment.Mine.Mine_Pager)
public final class MineFragment extends BaseFragment<MineFragmentMineBinding, MinePresenter, MineModel> implements MineContract.MineView {
    @Override
    protected int layoutId() {
        return R.layout.mine_fragment_mine;
    }


    @Override
    protected void setThemeColor(boolean isDarkMode) {
        mDatabind.mineTvUsername.setTextColor(themeColor);
        mDatabind.mineTvLevel.setTextColor(themeColor);
        mDatabind.mineTvRank.setTextColor(themeColor);
        mDatabind.mineCv.setCardBackgroundColor(themeColor);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        EventBus.getDefault().register(this);
        if (ModuleConfig.getInstance().user != null) {
            mDatabind.mineTvUsername.setText(ModuleConfig.getInstance().user.getUsername());
            mDatabind.mineIvMessage.setVisibility(View.VISIBLE);
        } else {
            mDatabind.mineIvMessage.setVisibility(View.GONE);
        }
    }

    /**
     * 懒加载
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
    public void setUserInfoCoin(UserInfoMessageEntity userInfoMessageEntity) {
        showSuccess();
        //设置头像
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(ColorUtils.getRandColorCode());
        mDatabind.mineIvHead.setBackground(gradientDrawable);
        mDatabind.mineTvUserabbr.setText(userInfoMessageEntity.getCoinInfo().getUsername().substring(0, 1));
        mDatabind.mineTvUsername.setText(userInfoMessageEntity.getCoinInfo().getUsername());
        //显示等级 排名 积分
        mDatabind.mineTvLevel.setText(getString(R.string.mine_gradle) + userInfoMessageEntity.getCoinInfo().getLevel());
        mDatabind.mineTvRank.setText(getString(R.string.mine_rank) + userInfoMessageEntity.getCoinInfo().getRank());
        mDatabind.mineTvPoints.setText(userInfoMessageEntity.getCoinInfo().getCoinCount() + "");
        mDatabind.mineTvMyCollectNumber.setText(userInfoMessageEntity.getCollectArticleInfo().getCount()+"");

    }

    @Override
    public void setUserInfo(UserInfoEntity userInfo) {
        mPresenter.requestUserInfoCoin();
        mDatabind.mineIvMessage.setVisibility(View.VISIBLE);
        //保存用户信息
        CacheUtils.saveDataInfo(MMkvConstants.USER, userInfo);
        //登录成功发送事件
        EventBus.getDefault().post(new EventBusUtils.LoginInSuccess());
    }


    public class ProcyClick {
        public void gotoLogin() {
            if (ModuleConfig.getInstance().user == null) {
                if (CacheUtils.getGestureLogin()) {
                    startActivity(new Intent(getActivity(), QuickLoginActivity.class));
                } else if (CacheUtils.getFingerLogin()){
                    loginBlomtric();
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        }

        @LoginCheck
        public void goUserCoin() {
            ARouter.getInstance().build(RoutePathActivity.Mine.UserCoin_pager)
                    .withString("userCoin", mDatabind.mineTvPoints.getText().toString().equals("") ? "0" : mDatabind.mineTvPoints.getText().toString()).navigation();
        }

        public void goCoinRank() {
            ARouter.getInstance().build(RoutePathActivity.Mine.UserCoinRank_Pager).navigation();
        }

        @LoginCheck
        public void goMyShareArticles() {
            startActivity(new Intent(getActivity(), MyShareArticleActivity.class));
        }

        public void goHistoryRecords() {
            startActivity(new Intent(getActivity(), HistoryRecordActivity.class));
        }


        @LoginCheck
        public void goMyCollectArticles() {
            startActivity(new Intent(getActivity(), MyCollectArticleActivity.class));
        }


        public void goMessage() {
            ARouter.getInstance().build(RoutePathActivity.Message.Message_pager).navigation();
        }

        public void goSet() {
            ARouter.getInstance().build(RoutePathActivity.Set.Set_pager).navigation();
        }

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.show(errorMsg);
        //失败了也要显示ui 避免特殊情况导致设置不了其他功能
        showSuccess();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess) {
        mPresenter.requestUserInfoCoin();
        mDatabind.mineIvMessage.setVisibility(View.VISIBLE);
    }


    /**
     * 退出登录成功
     *
     * @param logoutSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutSuccess(EventBusUtils.LogoutSuccess logoutSuccess) {
        mDatabind.mineTvUserabbr.setText("");
        mDatabind.mineTvUsername.setText(getString(R.string.mine_please_login));
        mDatabind.mineTvLevel.setText(getString(R.string.mine_nodata_gradle));
        mDatabind.mineTvRank.setText(getString(R.string.mine_nodata_rank));
        mDatabind.mineTvPoints.setText("");
        mDatabind.mineIvMessage.setVisibility(View.GONE);
        mDatabind.mineIvHead.setBackground(null);
        mDatabind.mineTvMyCollectNumber.setText("-");
        ImageLoader.loadCircleIntLocalPhoto(getActivity(), R.drawable.mine_iv_default_head, mDatabind.mineIvHead);
    }


    /**
     * 指纹登录
     */
    private void loginBlomtric() {
        BiometricControl.loginBlomtric(getActivity(), new BiometricControl.BiometricStatusCallback() {
            @Override
            public void onUsePassword() {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

            @Override
            public void onVerifySuccess(Cipher cipher) {
                try {
                    String text = CacheUtils.getEncryptLoginMessage();
                    byte[] input = Base64.decode(text, Base64.URL_SAFE);
                    byte[] bytes = cipher.doFinal(input);
                    /**
                     * 然后这里用原密码(当然是加密过的)调登录接口
                     */
                    LoginEntity loginEntity = GsonUtils.get(new String(bytes), LoginEntity.class);
                    byte[] iv = cipher.getIV();
                    mPresenter.requestUserInfo(loginEntity.getLoginName(),loginEntity.getLoginPassword());
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed() {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

            @Override
            public void error(int code, String reason) {
                ToastUtils.show(code + "," + reason);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }

            @Override
            public void onCancel() {
                ToastUtils.show(R.string.base_permission_denied);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
