package com.knight.wanandroid.module_mine.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityAboutBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/27 18:57
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.About_Pager)
public class AboutActivity extends BaseDBActivity<MineActivityAboutBinding> {
    @Override
    public int layoutId() {
        return R.layout.mine_activity_about;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }
}
