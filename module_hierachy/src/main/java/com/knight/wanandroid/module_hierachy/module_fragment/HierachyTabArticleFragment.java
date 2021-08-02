package com.knight.wanandroid.module_hierachy.module_fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_hierachy.R;
import com.knight.wanandroid.module_hierachy.databinding.HierachyFragmentTabarticleBinding;
import com.knight.wanandroid.module_hierachy.module_adapter.HierachyTabAdapter;
import com.knight.wanandroid.module_hierachy.module_contract.HierachyTabContract;
import com.knight.wanandroid.module_hierachy.module_entity.HierachyTabArticleListEntity;
import com.knight.wanandroid.module_hierachy.module_model.HierachyTabArticleModel;
import com.knight.wanandroid.module_hierachy.module_presenter.HierachyTabArticlePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/12 18:27
 * @descript:体系切换列表界面
 */
@Route(path = RoutePathFragment.Hierachy.Hierachy_TabArticle_Pager)
public class HierachyTabArticleFragment extends BaseFragment<HierachyFragmentTabarticleBinding, HierachyTabArticlePresenter, HierachyTabArticleModel> implements HierachyTabContract.HierachyTabView, OnLoadMoreListener, OnRefreshListener {

    private int cid;
    private int page = 0;

    private HierachyTabAdapter mHierachyTabAdapter;

    public static HierachyTabArticleFragment newInstance(int cid){
        HierachyTabArticleFragment hierachyTabArticleFragment = new HierachyTabArticleFragment();
        Bundle args = new Bundle();
        args.putInt("cid",cid);
        hierachyTabArticleFragment.setArguments(args);
        return hierachyTabArticleFragment;
    }
    @Override
    protected int layoutId() {
        return R.layout.hierachy_fragment_tabarticle;
    }

    @Override
    protected void setThemeColor() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        cid = getArguments().getInt("cid");
        mHierachyTabAdapter = new HierachyTabAdapter();
        mDatabind.includeTabarticle.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeTabarticle.baseFreshlayout.setOnLoadMoreListener(this);
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeTabarticle.baseBodyRv,new LinearLayoutManager(getActivity()),mHierachyTabAdapter,false);
        initListener();
    }


    @Override
    protected void lazyLoadData() {
        loadLoading(mDatabind.llTabarticle);
        mPresenter.requestHierachyTabArticles(page,cid);

    }

    @Override
    public void initData(){

    }

    @Override
    protected void reLoadData() {
        page = 0;
        mPresenter.requestHierachyTabArticles(page,cid);
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
    public void setHierachyTabArticles(HierachyTabArticleListEntity data) {
        showSuccess();
        mDatabind.includeTabarticle.baseFreshlayout.finishRefresh();
        mDatabind.includeTabarticle.baseFreshlayout.finishLoadMore();
        if (data.getDatas().size() > 0) {
            if (page == 0) {
                mHierachyTabAdapter.setNewInstance(data.getDatas());
            } else {
                mHierachyTabAdapter.addData(data.getDatas());
            }
            
            if (data.getDatas().size() < 10) {
                mDatabind.includeTabarticle.baseFreshlayout.setEnableLoadMore(false);
            } else {
                page ++;
            }

        } else {
            mDatabind.includeTabarticle.baseFreshlayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void collectArticleSuccess(int position) {
        mHierachyTabAdapter.getData().get(position).setCollect(true);
        mHierachyTabAdapter.notifyItemChanged(position);
    }

    @Override
    public void cancelArticleSuccess(int position) {
        mHierachyTabAdapter.getData().get(position).setCollect(false);
        mHierachyTabAdapter.notifyItemChanged(position);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestHierachyTabArticles(page,cid);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        mDatabind.includeTabarticle.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestHierachyTabArticles(page,cid);
    }

    private void initListener(){


        mHierachyTabAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mHierachyTabAdapter.getData().get(position).getLink(),
                        mHierachyTabAdapter.getData().get(position).getTitle(),
                        mHierachyTabAdapter.getData().get(position).getId(),
                        mHierachyTabAdapter.getData().get(position).isCollect(),
                        mHierachyTabAdapter.getData().get(position).getEnvelopePic(),
                        mHierachyTabAdapter.getData().get(position).getDesc(),
                        mHierachyTabAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mHierachyTabAdapter.getData().get(position).getAuthor()) ? mHierachyTabAdapter.getData().get(position).getShareUser() : mHierachyTabAdapter.getData().get(position).getAuthor());
            }
        });


        mHierachyTabAdapter.addChildClickViewIds(R.id.base_icon_collect,R.id.base_article_collect);
        mHierachyTabAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if(view.getId() == R.id.base_icon_collect || view.getId() == R.id.base_article_collect) {
                    if (mHierachyTabAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mHierachyTabAdapter.getData().get(position).getId(),position);
                    } else {
                        mPresenter.requestCollectArticle(mHierachyTabAdapter.getData().get(position).getId(),position);
                    }
                }
            }
        });

    }
}
