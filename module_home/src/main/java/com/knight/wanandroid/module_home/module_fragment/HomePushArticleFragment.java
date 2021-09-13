package com.knight.wanandroid.module_home.module_fragment;

import android.view.Gravity;

import com.knight.wanandroid.library_base.basefragment.BaseDBDialogFragment;
import com.knight.wanandroid.library_base.util.InitCustomViewUtils;
import com.knight.wanandroid.library_base.view.CardTransformer;
import com.knight.wanandroid.library_base.view.UtilShowAnim;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomePusharticleFragmentBinding;
import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/6/16 16:11
 * @descript:
 */
public class HomePushArticleFragment extends BaseDBDialogFragment<HomePusharticleFragmentBinding> {



    private List<EveryDayPushEntity> mEveryDayPushEntities;

    private List<Fragment> mPushCardFragments = new ArrayList<>();
    /**
     * 工具类 用于控制出场动画
     */
    private UtilShowAnim mUtilAnim;
    /**
     * 切换动画
     */
    private CardTransformer mTransformer;


    public HomePushArticleFragment(List<EveryDayPushEntity> mEveryDayPushEntities){
        this.mEveryDayPushEntities = mEveryDayPushEntities;
    }

    @Override
    protected int layoutId() {
        return R.layout.home_pusharticle_fragment;
    }

    @Override
    protected void initView() {
        mDatabind.setClick(new ProxyClick());
        initViewPager();
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }


    /**
     *
     * 初始化viewPager
     */
    private void initViewPager() {
        mPushCardFragments.clear();
        mUtilAnim = new UtilShowAnim(mDatabind.baseVp);
        for (EveryDayPushEntity everyDayPushEntity : mEveryDayPushEntities) {
            mPushCardFragments.add(HomePushCardFragment.newInstance(everyDayPushEntity.getArticlePicture(),everyDayPushEntity.getArticleTitle(),everyDayPushEntity.getArticledesc(),everyDayPushEntity.getAuthor(),everyDayPushEntity.getArticleLink()));

        }

        // 实例化ViewPager切换动画类
        mTransformer = new CardTransformer();
        mDatabind.baseVp.setPageTransformer(mTransformer);
        // 设置切换动画为 风车，并获取预加载数量
        int offscreen = mTransformer.setTransformerType(CardTransformer.ANIM_TYPE_WINDMILL);
        mDatabind.baseVp.setOffscreenPageLimit(offscreen);
        InitCustomViewUtils.setViewPager2InitFragment(this,mPushCardFragments,mDatabind.baseVp,true);


    }

    public class ProxyClick {
        public void dimissDialog(){
            dismiss();
        }
    }
}
