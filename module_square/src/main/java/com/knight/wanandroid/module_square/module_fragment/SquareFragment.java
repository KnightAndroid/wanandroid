package com.knight.wanandroid.module_square.module_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_base.util.DataBaseUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.databinding.SquareFragmentSquareBinding;
import com.knight.wanandroid.module_square.module_activity.SquareShareArticleActivity;
import com.knight.wanandroid.module_square.module_adapter.HotKeyAdapter;
import com.knight.wanandroid.module_square.module_adapter.SquareArticleAdapter;
import com.knight.wanandroid.module_square.module_contract.SquareContact;
import com.knight.wanandroid.module_square.module_entity.SquareArticleEntity;
import com.knight.wanandroid.module_square.module_entity.SquareArticleListEntity;
import com.knight.wanandroid.module_square.module_model.SquareModel;
import com.knight.wanandroid.module_square.module_presenter.SquarePresenter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 16:46
 * @descript:广场页面
 */
@Route(path = RoutePathFragment.Square.Square_Pager)
public class SquareFragment extends BaseFragment<SquareFragmentSquareBinding, SquarePresenter, SquareModel> implements SquareContact.SquareView, OnLoadMoreListener, OnRefreshListener {

    private HotKeyAdapter mHotKeyAdapter;
    private SquareArticleAdapter mSquareArticleAdapter;
    private int page;



    @Override
    protected int layoutId() {
        return R.layout.square_fragment_square;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        loadLoading(mDatabind.squareSharearticleFreshlayout);
        mHotKeyAdapter = new HotKeyAdapter(new ArrayList<SearchHotKeyEntity>());
        mSquareArticleAdapter = new SquareArticleAdapter(new ArrayList<SquareArticleEntity>());
        initListener();
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getActivity());
        //方向 主轴为水平方向,起点在左端
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //左对齐
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);
      //  flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        SetInitCustomView.initSwipeRecycleview(mDatabind.squareSearchhotRv,flexboxLayoutManager,mHotKeyAdapter,false);
        SetInitCustomView.initSwipeRecycleview(mDatabind.squareArticleRv,new LinearLayoutManager(getActivity()),mSquareArticleAdapter,true);
        mDatabind.squareSharearticleFreshlayout.setOnRefreshListener(this);
        mDatabind.squareSharearticleFreshlayout.setOnLoadMoreListener(this);
        EventBus.getDefault().register(this);


    }

    @Override
    protected void reLoadData() {
        mPresenter.requestHotKey();
        mPresenter.requestShareData(page);
    }


    private void initListener(){
        mHotKeyAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AppConfig.SEARCH_KEYWORD = mHotKeyAdapter.getData().get(position).getName();
                DataBaseUtils.saveSearchKeyword(AppConfig.SEARCH_KEYWORD);
                ARouterUtils.startActivity(RoutePathActivity.Home.searchResult,"keyword",AppConfig.SEARCH_KEYWORD);
            }
        });

        mSquareArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mSquareArticleAdapter.getData().get(position).getLink(),mSquareArticleAdapter.getData().get(position).getTitle(),mSquareArticleAdapter.getData().get(position).getId());
            }
        });

        mSquareArticleAdapter.addChildClickViewIds(R.id.square_icon_collect);
        mSquareArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.square_icon_collect) {
                    if (mSquareArticleAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mSquareArticleAdapter.getData().get(position).getId(),position);
                    } else {
                        mPresenter.requestCollectArticle(mSquareArticleAdapter.getData().get(position).getId(),position);
                    }
                }
            }
        });








    }

    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData() {
        mPresenter.requestHotKey();
        mPresenter.requestShareData(page);

    }

    @Override
    public void setHotKey(List<SearchHotKeyEntity> searchHotKeyEntities) {
        mHotKeyAdapter.setNewInstance(searchHotKeyEntities);
    }



    @Override
    public void setShareArticles(SquareArticleListEntity result) {
        showSuccess();
        mDatabind.squareSharearticleFreshlayout.finishLoadMore();
        mDatabind.squareSharearticleFreshlayout.finishRefresh();
        if (result.getDatas().size() > 0) {
            if(page == 0){
                mSquareArticleAdapter.setNewInstance(result.getDatas());
            } else {
                mSquareArticleAdapter.addData(result.getDatas());
            }
            page++;
        } else {
            mDatabind.squareSharearticleFreshlayout.setEnableLoadMore(false);
        }

    }

    @Override
    public void collectArticleSuccess(int position) {
        mSquareArticleAdapter.getData().get(position).setCollect(true);
        mSquareArticleAdapter.notifyItemChanged(position);
    }

    @Override
    public void cancelArticleSuccess(int position) {
        mSquareArticleAdapter.getData().get(position).setCollect(false);
        mSquareArticleAdapter.notifyItemChanged(position);
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
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestShareData(page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        mDatabind.squareSharearticleFreshlayout.setEnableLoadMore(true);
        mPresenter.requestHotKey();
        mPresenter.requestShareData(page);
    }


    public class ProcyClick{
        @LoginCheck
        public void goShareArticle(){
            startActivity(new Intent(getActivity(), SquareShareArticleActivity.class));
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ShareArticleSuccess(EventBusUtils.ShareArticleSuccess shareArticleSuccess){
        onRefresh(mDatabind.squareSharearticleFreshlayout);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
