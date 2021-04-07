package com.knight.wanandroid.library_base.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.knight.wanandroid.library_base.R;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/7 11:43
 * @descript:空数据
 */
public class EmptyCallBack extends Callback{

    @Override
    protected int onCreateView () {
        return R.layout.base_layout_empty;
    }
}
