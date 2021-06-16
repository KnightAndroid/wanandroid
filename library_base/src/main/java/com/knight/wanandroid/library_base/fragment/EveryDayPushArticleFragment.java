package com.knight.wanandroid.library_base.fragment;

import android.view.Gravity;

import com.alibaba.android.arouter.launcher.ARouter;
import com.knight.wanandroid.library_base.R;
import com.knight.wanandroid.library_base.databinding.BaseEverydaypushDialogBinding;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/16 16:11
 * @descript:
 */
public class EveryDayPushArticleFragment extends BaseDBDialogFragment<BaseEverydaypushDialogBinding> {

    private EveryDayPushEntity mEveryDayPushEntity;
    public EveryDayPushArticleFragment(EveryDayPushEntity mEveryDayPushEntity){
        this.mEveryDayPushEntity = mEveryDayPushEntity;
    }
    @Override
    protected int layoutId() {
        return R.layout.base_everydaypush_dialog;
    }

    @Override
    protected void initView() {
        mDatabind.setClick(new ProxyClick());
        //设置图像
        GlideEngineUtils.getInstance().loadStringPhoto(getActivity(),mEveryDayPushEntity.getArticlePicture(),mDatabind.ivEverydayPushpicture);
        //设置标题
        mDatabind.tvArticleTitle.setText(mEveryDayPushEntity.getArticleTitle());
        //设置副标题
        mDatabind.tvArticleDesc.setText(mEveryDayPushEntity.getArticledesc());

    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }


    public class ProxyClick {
        public void goWebActivity(){
            ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                    .withString("webUrl",mEveryDayPushEntity.getArticleLink())
                    .withString("webTitle",mEveryDayPushEntity.getArticleTitle())
                    .navigation();
            dismiss();
        }

        public void dimissDialog(){
            dismiss();
        }
    }
}
