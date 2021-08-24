package com.knight.wanandroid.module_square.module_fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.AppConfig;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.SearchHotKeyEntity;
import com.knight.wanandroid.library_base.loadsir.LoadCallBack;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_base.util.DataBaseUtils;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.library_widget.lottie.RightLottieAnimation;
import com.knight.wanandroid.library_widget.lottie.RightLottieListener;
import com.knight.wanandroid.module_square.R;
import com.knight.wanandroid.module_square.databinding.SquareFragmentSquareBinding;
import com.knight.wanandroid.module_square.module_activity.SquareShareArticleActivity;
import com.knight.wanandroid.module_square.module_adapter.HotKeyAdapter;
import com.knight.wanandroid.module_square.module_adapter.SquareArticleAdapter;
import com.knight.wanandroid.module_square.module_adapter.SquareQuestionAdapter;
import com.knight.wanandroid.module_square.module_contract.SquareContact;
import com.knight.wanandroid.module_square.module_entity.SquareArticleEntity;
import com.knight.wanandroid.module_square.module_entity.SquareArticleListEntity;
import com.knight.wanandroid.module_square.module_entity.SquareQuestionListEntity;
import com.knight.wanandroid.module_square.module_model.SquareModel;
import com.knight.wanandroid.module_square.module_presenter.SquarePresenter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

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
public final class SquareFragment extends BaseFragment<SquareFragmentSquareBinding, SquarePresenter, SquareModel> implements SquareContact.SquareView, OnLoadMoreListener, OnRefreshListener {

    private HotKeyAdapter mHotKeyAdapter;
    private SquareArticleAdapter mSquareArticleAdapter;
    private int page;
    private int questionPage = 1;

    private SquareQuestionAdapter mSquareQuestionAdapter;
    private SwipeRecyclerView baserecycleview;
    private RightLottieAnimation mRightLottieAnimation;
    private static final long RIPPLE_DURATION = 150;

    public LoadService mViewLoadService;
    SmartRefreshLayout smartRefreshLayout;

    private TextView tv_question_title;



    @Override
    protected int layoutId() {
        return R.layout.square_fragment_square;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(CacheUtils.getInstance().getThemeColor());
        gradientDrawable.setCornerRadius(4f);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mDatabind.squareTvGoshare.setBackground(gradientDrawable);
        } else {
            mDatabind.squareTvGoshare.setBackgroundDrawable(gradientDrawable);
        }
        mDatabind.squareFabUp.setBackgroundTintList(ColorUtils.createColorStateList(CacheUtils.getInstance().getThemeColor(),CacheUtils.getInstance().getThemeColor()));


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

        View QuestionMenu = LayoutInflater.from(getActivity()).inflate(R.layout.square_question_activity,null);
        mSquareQuestionAdapter = new SquareQuestionAdapter(new ArrayList<>());
        initAdapterListener();
        baserecycleview = QuestionMenu.findViewById(R.id.base_body_rv);
        smartRefreshLayout = (SmartRefreshLayout) QuestionMenu.findViewById(R.id.include_square_question);
        tv_question_title = ((TextView)QuestionMenu.findViewById(R.id.square_question_tv_title));
        tv_question_title.setText(getString(R.string.square_question));
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            questionPage = 1;
            mPresenter.requestSquareQuestion(questionPage);
        });
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.requestSquareQuestion(questionPage);
            }
        });

        SetInitCustomView.initSwipeRecycleview(baserecycleview,new LinearLayoutManager(getActivity()),mSquareQuestionAdapter,true);
        mDatabind.squareRoot.addView(QuestionMenu);


        LoadSir loadSir =  LoadSir.getDefault();
        mViewLoadService = loadSir.register(QuestionMenu.findViewById(R.id.include_square_question), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mViewLoadService.showCallback(LoadCallBack.class);
            }
        });
        mViewLoadService.showCallback(LoadCallBack.class);
        mRightLottieAnimation = new RightLottieAnimation.GuillotineBuilder(QuestionMenu,QuestionMenu.findViewById(R.id.square_question_lefticon),mDatabind.squareIvQuestion,mSquareQuestionAdapter)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(mDatabind.squareToolbar)
                .setClosedOnStart(true)
                .setGuillotineListener(new RightLottieListener() {
                    @Override
                    public void onRightLottieOpened() {
                        mDatabind.squareFabUp.setVisibility(View.GONE);
                        mViewLoadService.showCallback(LoadCallBack.class);
                        mPresenter.requestSquareQuestion(questionPage);
                    }

                    @Override
                    public void onRightLottieClosed() {
                        questionPage = 1;
                        mDatabind.squareFabUp.setVisibility(View.VISIBLE);
                    }
                })
                .build();
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
                ARouterUtils.startWeb(mSquareArticleAdapter.getData().get(position).getLink(),
                        mSquareArticleAdapter.getData().get(position).getTitle(),
                        mSquareArticleAdapter.getData().get(position).getId(),
                        mSquareArticleAdapter.getData().get(position).isCollect(),
                        mSquareArticleAdapter.getData().get(position).getEnvelopePic(),
                        mSquareArticleAdapter.getData().get(position).getDesc(),mSquareArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mSquareArticleAdapter.getData().get(position).getAuthor()) ? mSquareArticleAdapter.getData().get(position).getShareUser() : mSquareArticleAdapter.getData().get(position).getAuthor());
            }
        });

        mSquareArticleAdapter.addChildClickViewIds(R.id.square_icon_collect);
        mSquareArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.square_icon_collect) {
                    if (mSquareArticleAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mSquareArticleAdapter.getData().get(position).getId(),false,position);
                    } else {
                        mPresenter.requestCollectArticle(mSquareArticleAdapter.getData().get(position).getId(),false,position);
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
    public void collectArticleSuccess(int position,boolean question) {
        if(question){
            mSquareQuestionAdapter.getData().get(position).setCollect(true);
            mSquareQuestionAdapter.notifyItemChanged(position);
        } else {
            mSquareArticleAdapter.getData().get(position).setCollect(true);
            mSquareArticleAdapter.notifyItemChanged(position);
        }

    }

    @Override
    public void cancelArticleSuccess(int position,boolean question) {
        if (question) {
            mSquareQuestionAdapter.getData().get(position).setCollect(false);
            mSquareQuestionAdapter.notifyItemChanged(position);
        } else {
            mSquareArticleAdapter.getData().get(position).setCollect(false);
            mSquareArticleAdapter.notifyItemChanged(position);
        }

    }

    @Override
    public void setSquareQuestionData(SquareQuestionListEntity squareQuestionListEntity) {
        if (mViewLoadService != null) {
            mViewLoadService.showSuccess();
        }
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
        if (squareQuestionListEntity.getDatas().size() > 0) {
            if(questionPage == 1){
                mSquareQuestionAdapter.setNewInstance(squareQuestionListEntity.getDatas());
            } else {
                mSquareQuestionAdapter.addData(squareQuestionListEntity.getDatas());
            }
            questionPage++;
        } else {
            smartRefreshLayout.setEnableLoadMore(false);
        }
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

        public void scrollToUp(){
            mDatabind.squareNestedsv.fullScroll(View.FOCUS_UP);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ShareArticleSuccess(EventBusUtils.ShareArticleSuccess shareArticleSuccess){
        onRefresh(mDatabind.squareSharearticleFreshlayout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void oncollectSuccess(EventBusUtils.CollectSuccess collectSuccess){
        onRefresh(mDatabind.squareSharearticleFreshlayout);
    }

    private void initAdapterListener(){
        mSquareQuestionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(
                        mSquareQuestionAdapter.getData().get(position).getLink(),
                        mSquareQuestionAdapter.getData().get(position).getTitle(),
                        mSquareQuestionAdapter.getData().get(position).getId(),
                        mSquareQuestionAdapter.getData().get(position).isCollect(),
                        mSquareArticleAdapter.getData().get(position).getEnvelopePic(),
                        mSquareArticleAdapter.getData().get(position).getDesc(),
                        mSquareArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mSquareArticleAdapter.getData().get(position).getAuthor()) ? mSquareArticleAdapter.getData().get(position).getShareUser() : mSquareArticleAdapter.getData().get(position).getAuthor());
            }
        });

        mSquareQuestionAdapter.addChildClickViewIds(R.id.base_icon_collect);
        mSquareQuestionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.base_icon_collect) {
                    if (mSquareQuestionAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mSquareQuestionAdapter.getData().get(position).getId(),true,position);
                    } else {
                        mPresenter.requestCollectArticle(mSquareQuestionAdapter.getData().get(position).getId(),true,position);
                    }
                }
            }
        });



        mSquareArticleAdapter.addChildClickViewIds(R.id.square_icon_collect);
        mSquareArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.square_icon_collect) {
                    if (mSquareArticleAdapter.getData().get(position).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mSquareArticleAdapter.getData().get(position).getId(),false,position);
                    } else {
                        mPresenter.requestCollectArticle(mSquareArticleAdapter.getData().get(position).getId(),false,position);
                    }
                }
            }
        });
    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
