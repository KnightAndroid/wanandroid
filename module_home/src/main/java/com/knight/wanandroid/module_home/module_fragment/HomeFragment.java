package com.knight.wanandroid.module_home.module_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_common.ApplicationProvider;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.JsonUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.constant.MMkvConstants;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentHomeBinding;
import com.knight.wanandroid.module_home.module_adapter.OfficialAccountAdapter;
import com.knight.wanandroid.module_home.module_adapter.TopArticleAdapter;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.OfficialAccountEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;
import com.knight.wanandroid.module_home.module_logic.HomeArticleLogic;
import com.knight.wanandroid.module_home.module_model.HomeModel;
import com.knight.wanandroid.module_home.module_presenter.HomePresenter;
import com.knight.wanandroid.module_home.module_utils.CustomViewUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 10:33
 * @descript:首页fragment
 */


@Route(path = RoutePathFragment.Home.Home_Pager)
public class HomeFragment extends BaseFragment<HomeFragmentHomeBinding, HomePresenter, HomeModel> implements HomeContract.HomeView, OnRefreshListener {

    private TopArticleAdapter mTopArticleAdapter;
    private OfficialAccountAdapter mOfficialAccountAdapter;
    private View topArticleFootView;
    private List<TopArticleEntity> mTopArticleEntities;
    private boolean isShowOnlythree = false;
    List<HomeArticlesFragment> mFragments = new ArrayList<>();



    @Override
    public int layoutId(){
        return R.layout.home_fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        EventBus.getDefault().register(this);
        initUserData();
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeTopArticleRv,new LinearLayoutManager(getActivity()),mTopArticleAdapter,false);
        mTopArticleAdapter = new TopArticleAdapter(new ArrayList<>());
        mOfficialAccountAdapter = new OfficialAccountAdapter(new ArrayList<>());
        mDatabind.homeTopArticleRv.setAdapter(mTopArticleAdapter);
        mDatabind.homeRefreshLayout.setOnRefreshListener(this);
        topArticleFootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_toparticle_foot,null);
        topArticleFootView.findViewById(R.id.home_ll_seemorearticles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeArticleLogic.getInstance().setArrowAnimate(mTopArticleAdapter,topArticleFootView.findViewById(R.id.home_iv_toparticlearrow),isShowOnlythree);
                isShowOnlythree = !isShowOnlythree;
            }
        });
        iniTopAdapterClick();
        loadLoading(mDatabind.homeRefreshLayout);



    }


    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData(){
        //请求顶部文章
        mPresenter.requestTopArticle((BaseDBActivity) getActivity());
        //请求轮播图
        mPresenter.requestBannerData((BaseDBActivity) getActivity());
        //请求公众号数据
        mPresenter.requestOfficialAccountData((BaseDBActivity) getActivity());
    }

    @Override
    protected void reLoadData() {
        //请求顶部文章
        mPresenter.requestTopArticle((BaseDBActivity) getActivity());
        //请求轮播图
        mPresenter.requestBannerData((BaseDBActivity) getActivity());
        //请求公众号数据
        mPresenter.requestOfficialAccountData((BaseDBActivity) getActivity());
    }


    @Override
    public void setTopArticle(List<TopArticleEntity> topArticleEntityList) {
        mTopArticleEntities = topArticleEntityList;
        showSuccess();
        mTopArticleAdapter.setNewInstance(topArticleEntityList);
        if (mTopArticleEntities.size() > 3) {
            mTopArticleAdapter.setShowOnlyThree(true);
        } else {
            mTopArticleAdapter.setShowOnlyThree(false);
        }
        mDatabind.homeRefreshLayout.finishRefresh();
        if(mDatabind.homeTopArticleRv.getFooterCount() == 0 && topArticleEntityList.size() > 3){
            mDatabind.homeTopArticleRv.addFooterView(topArticleFootView);
        }
    }

    @Override
    public void setBannerData(List<BannerEntity> result) {
        mDatabind.homeBanner.setAdapter(new BannerImageAdapter<BannerEntity>(result) {
            @Override
            public void onBindView(BannerImageHolder holder, BannerEntity data, int position, int size) {
                GlideEngineUtils.getInstance().loadStringPhoto(ApplicationProvider.getInstance().getApplication(),data.getImagePath(),holder.imageView);
            }
        })
        .addBannerLifecycleObserver(this)
        .setIndicator(new CircleIndicator(getActivity()));
    }

    @Override
    public void setOfficialAccountData(List<OfficialAccountEntity> officialAccountModels) {
        //设置公众号数据
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeRvOfficialAccount,new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL),mOfficialAccountAdapter,true);
        mOfficialAccountAdapter.setNewInstance(officialAccountModels);
        initMagicIndicator();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.getInstance().showToast(errorMsg);
        showloadFailure();

    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //请求顶部文章
        mPresenter.requestTopArticle((BaseDBActivity) getActivity());
        //请求轮播图
        mPresenter.requestBannerData((BaseDBActivity) getActivity());
        //请求公众号数据
        mPresenter.requestOfficialAccountData((BaseDBActivity) getActivity());
    }

    public class ProcyClick{

        public void searchArticles(){
            mTopArticleAdapter.setShowOnlyThree(isShowOnlythree);
            isShowOnlythree = !isShowOnlythree;
        }

    }

    private void initMagicIndicator() {
        //初始化标签
        Type type = new TypeToken<List<String>>() {}.getType();
        String jsonData = JsonUtils.getJson(getActivity(),"searchkeywords.json");
        List<String> mDataList = GsonUtils.getList(jsonData,type);
        mFragments.clear();
        for (int i = 0;i < mDataList.size();i++) {
            mFragments.add(new HomeArticlesFragment());
        }
        CustomViewUtils.setViewPager2Init(getActivity(), mFragments, mDatabind.viewPager, false);
        CustomViewUtils.bindViewPager2(mDatabind.magicIndicator, mDatabind.viewPager, mDataList);

    }


    private void iniTopAdapterClick() {
        mTopArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @LoginCheck
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ToastUtils.getInstance().showToast("dfdfdfdf");
            }
        });
    }

    private void initUserData() {
        if (ModuleConfig.getInstance().user != null) {
            mDatabind.homeIncludeToolbar.homeTvLoginname.setText(ModuleConfig.getInstance().user.getUsername());
        } else {
            mDatabind.homeIncludeToolbar.homeTvLoginname.setText("登录");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess){
        //登录成功
        ModuleConfig.getInstance().user = CacheUtils.getInstance().getDataInfo(MMkvConstants.USER,UserInfoEntity.class);
        mDatabind.homeIncludeToolbar.homeTvLoginname.setText(ModuleConfig.getInstance().user.getUsername());
        //重新请求公众号数据
        mPresenter.requestOfficialAccountData((BaseDBActivity) getActivity());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutSuccess(EventBusUtils.LogoutSuccess logoutSuccess){
        //刷新页面
        mDatabind.homeIncludeToolbar.homeTvLoginname.setText("登录");
        //重新请求公众号数据
        mPresenter.requestOfficialAccountData((BaseDBActivity) getActivity());
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }







}
