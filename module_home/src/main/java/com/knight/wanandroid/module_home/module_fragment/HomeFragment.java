package com.knight.wanandroid.module_home.module_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.knight.wanandroid.library_base.activity.BaseDBActivity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentHomeBinding;
import com.knight.wanandroid.module_home.module_adapter.TopArticleAdapter;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerModel;
import com.knight.wanandroid.module_home.module_entity.TopArticleModel;
import com.knight.wanandroid.module_home.module_logic.HomeArticleLogic;
import com.knight.wanandroid.module_home.module_model.HomeModel;
import com.knight.wanandroid.module_home.module_presenter.HomePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 10:33
 * @descript:首页fragment
 */


@Route(path = RoutePathFragment.Home.Home_Pager)
public class HomeFragment extends BaseFragment<HomeFragmentHomeBinding, HomePresenter, HomeModel> implements HomeContract.HomeView, OnRefreshListener, OnLoadMoreListener {

    private TopArticleAdapter mTopArticleAdapter;
    private View topArticleFootView;
    private List<TopArticleModel> topArticleModels;
    private boolean isShowOnlythree = false;



    @Override
    public int layoutId(){
        return R.layout.home_fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeTopArticleRv,new LinearLayoutManager(getActivity()),mTopArticleAdapter,false);
        mTopArticleAdapter = new TopArticleAdapter(new ArrayList<>());
        mTopArticleAdapter.setAnimation();
        mDatabind.homeTopArticleRv.setAdapter(mTopArticleAdapter);
        mDatabind.homeRefreshLayout.setOnRefreshListener(this);
        mDatabind.homeRefreshLayout.setOnRefreshListener(this);
        topArticleFootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_toparticle_foot,null);
        topArticleFootView.findViewById(R.id.home_tv_seemorearticles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTopArticleAdapter.setShowOnlyThree(isShowOnlythree);
                isShowOnlythree = !isShowOnlythree;
            }
        });
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
    }


    @Override
    public void setTopArticle(List<TopArticleModel> topArticleModelList) {
        topArticleModels = topArticleModelList;
        mLoadService.showSuccess();
        mTopArticleAdapter.setNewInstance(topArticleModelList);
        if (topArticleModels.size() > 3) {
            mTopArticleAdapter.setShowOnlyThree(true);
        }
        mDatabind.homeRefreshLayout.finishRefresh();
        if(mDatabind.homeTopArticleRv.getFooterCount() == 0){
            mDatabind.homeTopArticleRv.addFooterView(topArticleFootView);
        }
    }

    @Override
    public void setBannerData(List<BannerModel> result) {

    }

    @Override
    public void setListData() {

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

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //请求顶部文章
        mPresenter.requestTopArticle((BaseDBActivity) getActivity());
        //请求轮播图
        mPresenter.requestBannerData((BaseDBActivity) getActivity());
    }

    public class ProcyClick{

        public void searchArticles(){
            mTopArticleAdapter.setShowOnlyThree(isShowOnlythree);
            isShowOnlythree = !isShowOnlythree;
        }

    }



}
