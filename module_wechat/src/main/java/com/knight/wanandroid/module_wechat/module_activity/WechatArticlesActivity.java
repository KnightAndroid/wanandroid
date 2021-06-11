package com.knight.wanandroid.module_wechat.module_activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.InitCustomViewUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.module_wechat.R;
import com.knight.wanandroid.module_wechat.databinding.WechatOfficialAccountBinding;
import com.knight.wanandroid.module_wechat.module_fragment.WechatArticleFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import kotlin.jvm.functions.Function1;

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

    private int selectIndex;

    private List<Fragment> wechatArticleFragments = new ArrayList<>();
    private List<String> titleDatas = new ArrayList<String>();



    @Override
    public int layoutId() {
        return R.layout.wechat_official_account;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        mDatabind.includeWechatToolbar.baseTvTitle.setText(getString(R.string.wechat_public));
        mDatabind.includeWechatToolbar.baseIvBack.setOnClickListener(v -> {
            finish();
        });
        initData();
        mDatabind.wecahtSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchKeywords = mDatabind.wecahtSearchEt.getText().toString().trim();
                    if (TextUtils.isEmpty(searchKeywords)) {
                        ToastUtils.getInstance().showToast("请输入内容再搜索");
                    } else {
                        serachWeChatArticles(searchKeywords);
                    }
                    return true;

                }
                return false;
            }
        });

    }


    @Override
    public void initData(){
        wechatArticleFragments.clear();
        titleDatas.clear();
        for (int i = 0;i < data.size();i++) {
            wechatArticleFragments.add(WechatArticleFragment.newInstance(data.get(i).getId()));
            titleDatas.add(data.get(i).getName());
        }
        ViewSetUtils.setViewPager2Init(this, wechatArticleFragments, mDatabind.wechatViewPager, false);
        InitCustomViewUtils.bindViewPager2(mDatabind.wechatIndicator, mDatabind.wechatViewPager, titleDatas, new Function1() {
            @Override
            public Object invoke(Object o) {
                selectIndex = (int)o;
                return selectIndex;
            }
        });
        mDatabind.wechatViewPager.setCurrentItem(position);
    }


    /**
     *
     * 搜索
     */
    private void serachWeChatArticles(String searchKeywords){
        ((WechatArticleFragment)(wechatArticleFragments.get(selectIndex))).serchArticlesByKeywords(searchKeywords);
    }



}
