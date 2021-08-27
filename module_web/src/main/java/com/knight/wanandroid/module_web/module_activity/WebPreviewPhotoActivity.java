package com.knight.wanandroid.module_web.module_activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.module_web.R;
import com.knight.wanandroid.module_web.databinding.WebPreviewphoroActivityBinding;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/8/26 14:57
 * @descript:预览大图界面
 */

@Route(path = RoutePathActivity.Web.Web_PreviewPhoto)
public final class WebPreviewPhotoActivity extends BaseDBActivity<WebPreviewphoroActivityBinding> {


    /**
     *
     * 文章里的图片
     */
    @Autowired(name = "photoUri")
    String photoUri = "";
    @Override
    public int layoutId() {
        return R.layout.web_previewphoro_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        ARouter.getInstance().inject(this);
        mDatabind.webPreviewIv.setZoomEnabled(true);
        mDatabind.webPreviewIv.displayImage(photoUri);
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    public class ProxyClick{
        public void returnUpActivity(){
            finish();
            overridePendingTransition(R.anim.web_fade_out_anim, R.anim.web_fade_in_anim);
        }
    }
}
