package com.knight.wanandroid.library_base.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.knight.wanandroid.library_base.R;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/7 11:38
 * @descript:错误加载页面
 */
public class ErrorCallBack extends Callback {

    @Override
    protected int onCreateView () {
        return R.layout.base_layout_error;
    }

}
