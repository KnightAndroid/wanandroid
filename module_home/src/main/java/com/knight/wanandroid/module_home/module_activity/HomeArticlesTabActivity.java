package com.knight.wanandroid.module_home.module_activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_util.BlurBuilder;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.library_widget.pagertransformer.CustPagerTransformer;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeArticlesTabActivityBinding;
import com.knight.wanandroid.module_home.module_adapter.TopArticleAroundAdapter;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;
import com.knight.wanandroid.module_home.module_fragment.HomeTopTabsFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/21 13:52
 * @descript:
 */
@Route(path = RoutePathActivity.Home.HomeTopArticle)
public class HomeArticlesTabActivity extends BaseDBActivity<HomeArticlesTabActivityBinding> {


    private List<Fragment> mHomeTopTabsFragments = new ArrayList<>();

    private List<TopArticleEntity> datas = new ArrayList<>();

    private TopArticleAroundAdapter mTopArticleAroundAdapter;

    private  CompositePageTransformer mCompositePageTransformer;



    @Override
    public int layoutId() {
        return R.layout.home_articles_tab_activity;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        datas = (List<TopArticleEntity>) getIntent().getSerializableExtra("toparticles");
        mDatabind.homeIv.setImageBitmap(BlurBuilder.blur(mDatabind.homeIv));
        if(BlurBuilder.isBlurFlag()){
            mDatabind.homeIv.setVisibility(View.VISIBLE);
        }
        for(int i = 0;i< datas.size();i++) {
            mHomeTopTabsFragments.add(HomeTopTabsFragment.newInstance(datas.get(i)));
        }
        mTopArticleAroundAdapter = new TopArticleAroundAdapter(new ArrayList<>());
        mTopArticleAroundAdapter.setNewInstance(datas);
        mTopArticleAroundAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                mTopArticleAroundAdapter.setSelectItem(position);
                mTopArticleAroundAdapter.notifyDataSetChanged();
                mDatabind.homeArticleViewpager.setCurrentItem(position);
            }
        });
        mCompositePageTransformer = ViewSetUtils.setViewPage2(mDatabind.homeArticleViewpager, ScreenUtils.dp2px(30),ScreenUtils.dp2px(0));
        mCompositePageTransformer.addTransformer(new CustPagerTransformer(0.85f));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeRvToparticles,linearLayoutManager,mTopArticleAroundAdapter,true);
        ViewSetUtils.setViewPager2Init(this, mHomeTopTabsFragments, mDatabind.homeArticleViewpager, true);

        mDatabind.homeArticleViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);


            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mTopArticleAroundAdapter.setSelectItem(position);
                mTopArticleAroundAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });


    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }


    @Override
    //安卓重写返回键事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.base_scalealpha_out, R.anim.base_scalealpha_slient);
        }
        return true;
    }

    @Override
    public void finish(){
        super.finish();
        BlurBuilder.recycle();
    }

    public class ProxyClick{
        public void backUpActivity(){
            finish();
            overridePendingTransition(R.anim.base_scalealpha_out, R.anim.base_scalealpha_slient);
        }
    }



}
