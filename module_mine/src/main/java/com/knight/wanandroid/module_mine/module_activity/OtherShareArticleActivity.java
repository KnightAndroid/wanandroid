package com.knight.wanandroid.module_mine.module_activity;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_base.baseactivity.BaseActivity;
import com.knight.wanandroid.library_base.loadsir.EmptyCallBack;
import com.knight.wanandroid.library_base.loadsir.ErrorCallBack;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_common.utils.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.library_widget.slidinglayout.SlidingUpPanelLayout;
import com.knight.wanandroid.module_mine.R;
import com.knight.wanandroid.module_mine.databinding.MineActivityOthershareBinding;
import com.knight.wanandroid.module_mine.module_adapter.OtherShareArticleAdapter;
import com.knight.wanandroid.module_mine.module_contract.OtherArticleContract;
import com.knight.wanandroid.module_mine.module_entity.OtherShareArticleListEntity;
import com.knight.wanandroid.module_mine.module_model.OtherShareArticleModel;
import com.knight.wanandroid.module_mine.module_presenter.OtherShareArticlePresenter;
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
 * @Date 2021/5/20 17:29
 * @descript:
 */
@Route(path = RoutePathActivity.Mine.OtherShareArticle_Pager)
public final class OtherShareArticleActivity extends BaseActivity<MineActivityOthershareBinding, OtherShareArticlePresenter, OtherShareArticleModel> implements
        OtherArticleContract.OtherShareArticleView, OnRefreshListener, OnLoadMoreListener {


    private int uid;
    private int page = 1;
    private OtherShareArticleAdapter mOtherShareArticleAdapter;
    public LoadService mViewLoadService;

    @Override
    public int layoutId() {
        return R.layout.mine_activity_othershare;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        mDatabind.mineTvUsername.setTextColor(themeColor);
        mDatabind.mineOtherCoincount.setTextColor(themeColor);
        mDatabind.mineOtherTvLevel.setTextColor(themeColor);
        mDatabind.mineOtherTvRank.setTextColor(themeColor);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        mDatabind.inculeOthermessageToolbar.baseIvBack.setOnClickListener(v -> finish());
        mDatabind.includeOtherSharearticle.baseFreshlayout.setOnRefreshListener(this);
        mDatabind.includeOtherSharearticle.baseFreshlayout.setOnLoadMoreListener(this);
        uid = getIntent().getIntExtra("uid",0);
        mOtherShareArticleAdapter = new OtherShareArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.includeOtherSharearticle.baseBodyRv,new LinearLayoutManager(this),mOtherShareArticleAdapter,true);
        mDatabind.mineSlidupPanellayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

            }
        });



        mDatabind.mineSlidupPanellayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabind.mineSlidupPanellayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        showLoading(mDatabind.mineSlidupPanellayout);
        mDatabind.inculeOthermessageToolbar.baseTvTitle.setText(getString(R.string.mine_other_shareArticles));
        initListener();


        LoadSir loadSir =  LoadSir.getDefault();
        mViewLoadService = loadSir.register(mDatabind.includeOtherSharearticle.baseFreshlayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.requestOtherShareArticle(uid,page);
                mViewLoadService.showCallback(LoadCallBack.class);
            }
        });
        mViewLoadService.showCallback(LoadCallBack.class);




    }



    @Override
    public void initData(){
        mPresenter.requestOtherShareArticle(uid,page);
    }

    @Override
    public void reLoadData(){
        mPresenter.requestOtherShareArticle(uid,page);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {
        ToastUtils.show(errorMsg);
        showFailure();
        mViewLoadService.showCallback(ErrorCallBack.class);
    }

    @Override
    public void setOtherShareArticle(OtherShareArticleListEntity result) {
        showSuccess();
        mViewLoadService.showSuccess();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.OVAL);
        gradientDrawable.setColor(ColorUtils.getRandColorCode());
        mDatabind.mineOtherIvHead.setBackground(gradientDrawable);
        mDatabind.mineOtherTvUserabbr.setText(result.getCoinInfo().getUsername().substring(0,1));
        //用户名
        mDatabind.mineTvUsername.setText(result.getCoinInfo().getNickname());
        //积分
        mDatabind.mineOtherCoincount.setText(getString(R.string.mine_integral)+result.getCoinInfo().getCoinCount());
        mDatabind.mineOtherTvLevel.setText(getString(R.string.mine_gradle)+result.getCoinInfo().getLevel());
        mDatabind.mineOtherTvRank.setText(getString(R.string.mine_rank)+result.getCoinInfo().getRank());
        mDatabind.includeOtherSharearticle.baseFreshlayout.finishRefresh();
        mDatabind.includeOtherSharearticle.baseFreshlayout.finishLoadMore();
        if (result.getShareArticles().getDatas().size() > 0) {
            if (page == 1) {
                mOtherShareArticleAdapter.setNewInstance(result.getShareArticles().getDatas());
            } else {
                mOtherShareArticleAdapter.addData(result.getShareArticles().getDatas());
            }
            page ++;
        } else {
            mViewLoadService.showCallback(EmptyCallBack.class);
            mDatabind.includeOtherSharearticle.baseFreshlayout.setEnableLoadMore(false);
        }

    }

    @Override
    public void collectArticleSuccess(int position) {
        mOtherShareArticleAdapter.getData().get(position).setCollect(true);
        mOtherShareArticleAdapter.notifyItemChanged(position);
    }

    @Override
    public void cancelArticleSuccess(int position) {
        mOtherShareArticleAdapter.getData().get(position).setCollect(false);
        mOtherShareArticleAdapter.notifyItemChanged(position);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mPresenter.requestOtherShareArticle(uid,page);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        mPresenter.requestOtherShareArticle(uid,page);
        mDatabind.includeOtherSharearticle.baseFreshlayout.setEnableLoadMore(true);
    }

    private void initListener(){
        mOtherShareArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mOtherShareArticleAdapter.getData().get(position).getLink(),
                        mOtherShareArticleAdapter.getData().get(position).getTitle(),
                        mOtherShareArticleAdapter.getData().get(position).getId(),
                        mOtherShareArticleAdapter.getData().get(position).isCollect(),
                        mOtherShareArticleAdapter.getData().get(position).getEnvelopePic(),
                        mOtherShareArticleAdapter.getData().get(position).getDesc(),
                        mOtherShareArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mOtherShareArticleAdapter.getData().get(position).getAuthor()) ? mOtherShareArticleAdapter.getData().get(position).getShareUser() : mOtherShareArticleAdapter.getData().get(position).getAuthor());
            }
        });

        mOtherShareArticleAdapter.addChildClickViewIds(R.id.base_icon_collect);
        mOtherShareArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.base_icon_collect) {
                    if (mOtherShareArticleAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mOtherShareArticleAdapter.getData().get(position).getId(),position);
                    } else {
                        mPresenter.requestCollectArticle(mOtherShareArticleAdapter.getData().get(position).getId(),position);
                    }

                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void oncollectSuccess(EventBusUtils.CollectSuccess collectSuccess){
        page = 1;
        mPresenter.requestOtherShareArticle(uid,page);
    }


    public class ProxyClick {
        public void refresh(){
            mDatabind.includeOtherSharearticle.baseFreshlayout.autoRefresh();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }




}
