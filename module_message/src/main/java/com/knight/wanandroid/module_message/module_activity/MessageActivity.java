package com.knight.wanandroid.module_message.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.InitCustomViewUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.module_message.R;
import com.knight.wanandroid.module_message.databinding.MessageActivityBinding;
import com.knight.wanandroid.module_message.module_fragment.MessageFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

@Route(path = RoutePathActivity.Message.Message_pager)
public class MessageActivity extends BaseDBActivity<MessageActivityBinding> {


    private List<Fragment> messageFragment = new ArrayList<>();
    private List<String> titleDatas = new ArrayList<String>();
    @Override
    public int layoutId() {
        return R.layout.message_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.includeMessageToolbar.baseTvTitle.setText(R.string.message_center);
        mDatabind.includeMessageToolbar.baseIvBack.setOnClickListener(v -> finish());
        initData();

    }


    @Override
    public void initData(){
        super.initData();
        messageFragment.clear();
        titleDatas.clear();
        messageFragment.add(MessageFragment.newInstance(true));
        messageFragment.add(MessageFragment.newInstance(false));
        titleDatas.add(getString(R.string.message_readed));
        titleDatas.add(getString(R.string.message_unread));
        ViewSetUtils.setViewPager2Init(this, messageFragment, mDatabind.messageViewPager, false);
        InitCustomViewUtils.bindViewPager2(mDatabind.messageIndicator, mDatabind.messageViewPager, titleDatas, null);
        mDatabind.messageViewPager.setCurrentItem(0);
    }

}