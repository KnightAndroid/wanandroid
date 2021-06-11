package com.knight.wanandroid.module_wechat.module_fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_wechat.R;
import com.knight.wanandroid.module_wechat.databinding.WechatOfficialaccountViewpagerBinding;
import com.knight.wanandroid.module_wechat.module_adapter.WechatArticleAdapter;
import com.knight.wanandroid.module_wechat.module_contract.WechatArticleContract;
import com.knight.wanandroid.module_wechat.module_entity.WechatArticleListEntity;
import com.knight.wanandroid.module_wechat.module_model.WechatArticleModel;
import com.knight.wanandroid.module_wechat.module_presenter.WechatPresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/5/14 14:04
 * @descript:
 */
public class WechatArticleFragment extends BaseFragment<WechatOfficialaccountViewpagerBinding, WechatPresenter, WechatArticleModel> implements WechatArticleContract.WechatArticleView, OnRefreshListener, OnLoadMoreListener {



    private int cid;
    private int page = 1;
    private WechatArticleAdapter mWechatArticleAdapter;
    private String keyWords = "";

    public static WechatArticleFragment newInstance(int cid){
        WechatArticleFragment wechatArticleFragment = new WechatArticleFragment();
        Bundle args = new Bundle();
        args.putInt("cid",cid);
        wechatArticleFragment.setArguments(args);
        return wechatArticleFragment;
    }



    @Override
    protected int layoutId() {
        return R.layout.wechat_officialaccount_viewpager;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        cid = getArguments().getInt("cid");
        mDatabind.includeWechatArticles.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeWechatArticles.baseFreshlayout.setOnLoadMoreListener(this);
        mWechatArticleAdapter = new WechatArticleAdapter(new ArrayList<>());
        initListener();
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeWechatArticles.baseBodyRv,new LinearLayoutManager(getActivity()),mWechatArticleAdapter,false);
    }

    @Override
    public void lazyLoadData(){
        loadLoading(mDatabind.includeWechatArticles.baseFreshlayout);
        mPresenter.requestWechatArticle(page,cid);
    }

    @Override
    protected void reLoadData() {
        mPresenter.requestWechatArticle(page,cid);
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
    public void setWechatArticle(WechatArticleListEntity result) {
        setData(result);
    }

    @Override
    public void collectArticleSuccess(int position) {
        mWechatArticleAdapter.getData().get(position).setCollect(true);
        mWechatArticleAdapter.notifyItemChanged(position);
    }

    @Override
    public void cancelArticleSuccess(int position) {
        mWechatArticleAdapter.getData().get(position).setCollect(false);
        mWechatArticleAdapter.notifyItemChanged(position);
    }

    @Override
    public void setSearchArticlesByKeyword(WechatArticleListEntity result) {
        setData(result);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (!TextUtils.isEmpty(keyWords)) {
            mPresenter.requestArticlesByKeywords(page,cid,keyWords);
        } else {
            mPresenter.requestWechatArticle(page,cid);
        }

    }

    /**
     *
     * 下拉重新刷新
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mDatabind.includeWechatArticles.baseFreshlayout.setEnableLoadMore(true);
        mPresenter.requestWechatArticle(page,cid);
    }


    private void initListener() {
        mWechatArticleAdapter.addChildClickViewIds(R.id.base_icon_collect);
        mWechatArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.base_icon_collect) {
                    if (mWechatArticleAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mWechatArticleAdapter.getData().get(position).getId(), position);
                    } else {
                        mPresenter.requestCollectArticle(mWechatArticleAdapter.getData().get(position).getId(), position);
                    }
                }
            }
        });


        mWechatArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mWechatArticleAdapter.getData().get(position).getLink(),
                        mWechatArticleAdapter.getData().get(position).getTitle(),
                        mWechatArticleAdapter.getData().get(position).getId(),
                        mWechatArticleAdapter.getData().get(position).isCollect(),
                        mWechatArticleAdapter.getData().get(position).getEnvelopePic(),
                        mWechatArticleAdapter.getData().get(position).getDesc(),
                        mWechatArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mWechatArticleAdapter.getData().get(position).getAuthor())? mWechatArticleAdapter.getData().get(position).getShareUser() : mWechatArticleAdapter.getData().get(position).getAuthor());
            }
        });
    }

    private void setData(WechatArticleListEntity result){
        showSuccess();
        mDatabind.includeWechatArticles.baseFreshlayout.finishRefresh();
        mDatabind.includeWechatArticles.baseFreshlayout.finishLoadMore();
        if (page == 1) {
            if (result.getDatas().size() > 0) {
                mWechatArticleAdapter.setNewInstance(result.getDatas());
            } else {
                showEmptyData();
            }
        } else {
            mWechatArticleAdapter.addData(result.getDatas());
        }
        if (result.getDatas().size() < 10) {
            mDatabind.includeWechatArticles.baseFreshlayout.setEnableLoadMore(false);
        } else {
            page ++;
        }

    }

    /**
     *
     * 搜索
     */
    public void serchArticlesByKeywords(String keyWords){
        //重新设为1开始
        page = 1;
        this.keyWords = keyWords;
        mPresenter.requestArticlesByKeywords(page,cid,keyWords);
        mDatabind.includeWechatArticles.baseFreshlayout.autoRefresh();

    }


    public class ProcyClick{

        public void scrollTop(){
            mDatabind.includeWechatArticles.baseBodyRv.smoothScrollToPosition(0);
        }
    }
}
