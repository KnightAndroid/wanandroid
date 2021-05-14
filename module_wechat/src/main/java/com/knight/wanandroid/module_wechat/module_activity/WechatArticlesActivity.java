package com.knight.wanandroid.module_wechat.module_activity;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.InitCustomViewUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.module_wechat.R;
import com.knight.wanandroid.module_wechat.databinding.WechatOfficialAccountBinding;
import com.knight.wanandroid.module_wechat.module_fragment.WechatArticleFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 14:14
 * @descript:
 */
@Route(path = RoutePathActivity.Wechat.Wechat_Pager)
public class WechatArticlesActivity extends BaseDBActivity<WechatOfficialAccountBinding> {

    @Autowired(name = "data")
    ArrayList<OfficialAccountEntity> data;

    @Autowired(name="position")
    int position = 0;

    private List<Fragment> wechatArticleFragments = new ArrayList<>();
    private List<String> titleDatas = new ArrayList<String>();



    @Override
    public int layoutId() {
        return R.layout.wechat_official_account;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        wechatArticleFragments.clear();
        titleDatas.clear();
        for (int i = 0;i < data.size();i++) {
            wechatArticleFragments.add(WechatArticleFragment.newInstance(data.get(i).getId()));
            titleDatas.add(data.get(i).getName());
        }
        ViewSetUtils.setViewPager2Init(this, wechatArticleFragments, mDatabind.wechatViewPager, false);
        InitCustomViewUtils.bindViewPager2(mDatabind.wechatIndicator, mDatabind.wechatViewPager, titleDatas);
        mDatabind.wechatViewPager.setCurrentItem(position);
    }



}
