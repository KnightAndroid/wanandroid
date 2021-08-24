package com.knight.wanandroid.module_home.module_fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentArticleBinding;
import com.knight.wanandroid.module_home.module_adapter.HomeArticleAdapter;
import com.knight.wanandroid.module_home.module_constants.HomeConstants;
import com.knight.wanandroid.module_home.module_contract.HomeArticleContract;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_model.HomeArticleModel;
import com.knight.wanandroid.module_home.module_presenter.HomeArticlePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/12 19:14
 * @descript:
 */
public final class HomeArticlesFragment extends BaseFragment<HomeFragmentArticleBinding, HomeArticlePresenter, HomeArticleModel> implements HomeArticleContract.HomeArticleView, OnLoadMoreListener, OnRefreshListener {

    private HomeArticleAdapter mHomeArticleAdapter;
    //页码从0开始
    private int currentPage = 0;

    @Override
    protected int layoutId() {
        return R.layout.home_fragment_article;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mHomeArticleAdapter = new HomeArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeArticleBody, new LinearLayoutManager(getActivity()), mHomeArticleAdapter, true);
        mDatabind.homeArticleBody.setAdapter(mHomeArticleAdapter);
        mDatabind.homeArticleFreshlayout.setOnLoadMoreListener(this);
        mDatabind.homeArticleFreshlayout.setOnRefreshListener(this);
        mHomeArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mHomeArticleAdapter.getData().get(position).getLink(),
                        mHomeArticleAdapter.getData().get(position).getTitle(),
                        mHomeArticleAdapter.getData().get(position).getId(),
                        mHomeArticleAdapter.getData().get(position).isCollect(),
                        mHomeArticleAdapter.getData().get(position).getEnvelopePic(),
                        mHomeArticleAdapter.getData().get(position).getDesc(),
                        mHomeArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mHomeArticleAdapter.getData().get(position).getAuthor()) ? mHomeArticleAdapter.getData().get(position).getShareUser() : mHomeArticleAdapter.getData().get(position).getAuthor());
            }
        });

        mHomeArticleAdapter.addChildClickViewIds(R.id.home_icon_collect);
        mHomeArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.home_icon_collect) {
                    if (mHomeArticleAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mHomeArticleAdapter.getData().get(position).getId(), position);
                    } else {
                        mPresenter.requestCollectArticle(mHomeArticleAdapter.getData().get(position).getId(), position);
                    }

                }
            }
        });


        loadLoading(mDatabind.llHome);


    }

    /**
     * 懒加载
     */
    @Override
    protected void lazyLoadData() {
        currentPage = 0;
        mPresenter.requestSearchArticle(currentPage, HomeConstants.ARTICLE_TYPE);
    }

    @Override
    protected void reLoadData() {
        lazyLoadData();
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
    public void setSearchArticle(HomeArticleListEntity result) {

        loadArticleData(result);
    }

    @Override
    public void collectArticleSuccess(int position) {
        mHomeArticleAdapter.getData().get(position).setCollect(true);
        mHomeArticleAdapter.notifyItemChanged(position);

    }

    @Override
    public void cancelArticleSuccess(int position) {
        mHomeArticleAdapter.getData().get(position).setCollect(false);
        mHomeArticleAdapter.notifyItemChanged(position);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void oncollectSuccess(EventBusUtils.CollectSuccess collectSuccess) {
        //刷新重新请求公众号数据
        currentPage = 0;
        mPresenter.requestSearchArticle(currentPage, HomeConstants.ARTICLE_TYPE);

    }


    /**
     * 加载文章列表数据
     *
     * @param result
     */
    private void loadArticleData(HomeArticleListEntity result) {
        showSuccess();
        currentPage = result.getCurPage();
        mDatabind.homeArticleFreshlayout.finishLoadMore();
        mDatabind.homeArticleFreshlayout.finishRefresh();
        // dismissLoadingHud();
        if (currentPage > 1) {
            if (result.getDatas().size() > 0) {
                mHomeArticleAdapter.addData(result.getDatas());
            } else {
                mDatabind.homeArticleFreshlayout.setEnableLoadMore(false);
            }
        } else {
            mHomeArticleAdapter.setNewInstance(result.getDatas());
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestSearchArticle(currentPage, HomeConstants.ARTICLE_TYPE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        lazyLoadData();
    }
}
