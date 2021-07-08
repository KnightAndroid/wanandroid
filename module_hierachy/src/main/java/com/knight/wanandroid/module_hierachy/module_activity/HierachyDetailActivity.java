package com.knight.wanandroid.module_hierachy.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.databinding.HierachyDetailActivityBinding;
import com.knight.wanandroid.module_hierachy.module_fragment.HierachyTabArticleFragment;

import androidx.fragment.app.FragmentTransaction;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/17 9:12
 * @descript:
 */
@Route(path = RoutePathActivity.Hierachy.HierachyDetail)
public class HierachyDetailActivity extends BaseDBActivity<HierachyDetailActivityBinding> {



    @Autowired(name = "cid")
    int cid;
    private HierachyTabArticleFragment mHierachyTabArticleFragment;

    @Autowired(name = "titleName")
    String titleName;

    @Override
    public int layoutId() {
        return R.layout.hierachy_detail_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.includeHierachydetailToolbar.baseTvTitle.setText(titleName);
        mDatabind.includeHierachydetailToolbar.baseIvBack.setOnClickListener(v -> finish());
        createFragment();

    }


    public void createFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mHierachyTabArticleFragment = HierachyTabArticleFragment.newInstance(cid);
        fragmentTransaction.add(R.id.hierachy_detail_fr,mHierachyTabArticleFragment);
        fragmentTransaction.commit();

    }
}
