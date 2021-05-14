package com.knight.wanandroid.module_hierachy.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.InitCustomViewUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.databinding.HierachyActivityTabBinding;
import com.knight.wanandroid.module_hierachy.module_fragment.HierachyTabArticleFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 16:58
 * @descript:
 */
@Route(path = RoutePathActivity.Hierachy.HierachyTab)
public class HierachyTabActivity extends BaseDBActivity<HierachyActivityTabBinding> {


    @Autowired(name = "childrenNames")
    ArrayList<String> childrenNames;

    @Autowired(name = "cids")
    ArrayList<Integer> cids;

    @Autowired(name="titleName")
    String titleName = "";

    List<Fragment> hierachyTabFragments = new ArrayList<>();
    //顶部导航栏
    List<String> titleDatas = new ArrayList<>();
    @Override
    public int layoutId() {
        return R.layout.hierachy_activity_tab;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.includeTabarticelToolbar.baseTvTitle.setText(titleName);
        mDatabind.includeTabarticelToolbar.baseIvBack.setOnClickListener(v -> finish());
        hierachyTabFragments.clear();
        titleDatas.clear();
        for (int i = 0;i < childrenNames.size();i++) {
            hierachyTabFragments.add(HierachyTabArticleFragment.newInstance(cids.get(i)));
            titleDatas.add(childrenNames.get(i));
        }
        ViewSetUtils.setViewPager2Init(this, hierachyTabFragments, mDatabind.hierachyViewPager, false);
        InitCustomViewUtils.bindViewPager2(mDatabind.hierachyIndicator, mDatabind.hierachyViewPager, titleDatas);
    }

    @Override
    public void onSucceed(Object result) {

    }

    @Override
    public void onFail(Exception e) {

    }
}
