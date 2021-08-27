package com.knight.wanandroid.module_home.module_fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_aop.loginintercept.LoginCheck;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_common.provider.ApplicationProvider;
import com.knight.wanandroid.library_util.BlurBuilder;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.ColorUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.JsonUtils;
import com.knight.wanandroid.library_util.LanguageFontSizeUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentRecommendBinding;
import com.knight.wanandroid.module_home.module_activity.HomeArticlesTabActivity;
import com.knight.wanandroid.module_home.module_adapter.HomeArticleAdapter;
import com.knight.wanandroid.module_home.module_adapter.OfficialAccountAdapter;
import com.knight.wanandroid.module_home.module_adapter.OpenSourceAdapter;
import com.knight.wanandroid.module_home.module_adapter.TopArticleAdapter;
import com.knight.wanandroid.module_home.module_contract.RecommendContract;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.HomeArticleListEntity;
import com.knight.wanandroid.module_home.module_entity.OpenSourceEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;
import com.knight.wanandroid.module_home.module_logic.HomeArticleLogic;
import com.knight.wanandroid.module_home.module_model.RecommendModel;
import com.knight.wanandroid.module_home.module_presenter.RecommendPresenter;
import com.scwang.smart.refresh.header.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/7/21 14:44
 * @descript:
 */

@Route(path = RoutePathFragment.Home.Home_Recommend_Pager)
public final class HomeRecommendFragment extends BaseFragment<HomeFragmentRecommendBinding, RecommendPresenter, RecommendModel> implements RecommendContract.RecommendView, OnRefreshListener, OnLoadMoreListener {

    private View topArticleFootView;
    private TopArticleAdapter mTopArticleAdapter;
    private OfficialAccountAdapter mOfficialAccountAdapter;
    //开源库
    private OpenSourceAdapter mOpenSourceAdapter;
    private boolean isShowOnlythree = false;
    private boolean openTwoLevel = false;
    private List<TopArticleEntity> mTopArticleEntities;
    private int currentPage = 0;
    private HomeArticleAdapter mHomeArticleAdapter;

    private View recommendHeadView;
    private SwipeRecyclerView home_top_article_rv;
    private Banner home_banner;
    private SwipeRecyclerView home_rv_official_account;
    private RelativeLayout home_rl_message;
    private TextView home_tv_unread_message;


    @Override
    protected int layoutId() {
        return R.layout.home_fragment_recommend;
    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {


    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProxyClick());
        EventBus.getDefault().register(this);
        bindHeadView();
        mTopArticleAdapter = new TopArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(home_top_article_rv, new LinearLayoutManager(getActivity()), mTopArticleAdapter, false);
        mOfficialAccountAdapter = new OfficialAccountAdapter(new ArrayList<>());
        mHomeArticleAdapter = new HomeArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeRecommendArticleBody, new LinearLayoutManager(getActivity()), mHomeArticleAdapter, true);

        topArticleFootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_toparticle_foot, null);
        topArticleFootView.findViewById(R.id.home_ll_seemorearticles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeArticleLogic.getInstance().setArrowAnimate(mTopArticleAdapter, topArticleFootView.findViewById(R.id.home_iv_toparticlearrow), isShowOnlythree);
                isShowOnlythree = !isShowOnlythree;
            }
        });
        mDatabind.homeIconFab.setBackgroundTintList(ColorUtils.createColorStateList(CacheUtils.getInstance().getThemeColor(), CacheUtils.getInstance().getThemeColor()));
        initTopAdapterClick();
        initOfficialAccountClick();
        initArticleLinstener();
        initTwoLevel();
        loadLoading(mDatabind.flTest);

        mDatabind.homeRefreshLayout.setOnMultiListener(new SimpleMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                lazyLoadData();
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

                if (oldState == RefreshState.TwoLevel) {
                    mDatabind.homeTwoLevelContent.animate().alpha(0).setDuration(0);
                } else if (oldState == RefreshState.TwoLevelReleased) {
                    openTwoLevel = true;
                    mDatabind.homeIconFab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.base_icon_bottom));
                } else if (oldState == RefreshState.TwoLevelFinish) {

                    openTwoLevel = false;
                    mDatabind.homeIconFab.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.base_icon_up));
                }

            }

        });
        mDatabind.homeTwoLevelHeader.setOnTwoLevelListener(new OnTwoLevelListener() {
            @Override
            public boolean onTwoLevel(@NonNull RefreshLayout refreshLayout) {
                mDatabind.homeTwoLevelContent.animate().alpha(1).setDuration(1000);
                return true;
            }
        });

        mDatabind.homeTwoLevelHeader.setEnablePullToCloseTwoLevel(false);
        mDatabind.homeRefreshLayout.setEnableLoadMore(true);
        mDatabind.homeRefreshLayout.setOnLoadMoreListener(this);
    }

    private void bindHeadView() {
        recommendHeadView = LayoutInflater.from(getActivity()).inflate(R.layout.home_recommend_head, null);
        home_rl_message = recommendHeadView.findViewById(R.id.home_rl_message);
        home_tv_unread_message = recommendHeadView.findViewById(R.id.home_tv_unread_message);
        home_top_article_rv = recommendHeadView.findViewById(R.id.home_top_article_rv);
        home_banner = recommendHeadView.findViewById(R.id.home_banner);
        home_rv_official_account = recommendHeadView.findViewById(R.id.home_rv_official_account);
        home_rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RoutePathActivity.Message.Message_pager).navigation();
            }
        });

    }

    /**
     * 懒加载
     */
    @Override
    protected void lazyLoadData() {
        currentPage = 0;
        //未读数量请求
        if (ModuleConfig.getInstance().user != null) {
            mPresenter.requestUnreadMessage();
        }
        //请求顶部文章
        mPresenter.requestTopArticle();
        //请求轮播图
        mPresenter.requestBannerData();
        //请求公众号数据
        mPresenter.requestOfficialAccountData();

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
        showloadFailure();
    }

    @Override
    public void setTopArticle(List<TopArticleEntity> topArticleEntityList) {
        mTopArticleEntities = topArticleEntityList;
        mTopArticleAdapter.setNewInstance(topArticleEntityList);
        if (mTopArticleEntities.size() > 3) {
            mTopArticleAdapter.setShowOnlyThree(true);
        } else {
            mTopArticleAdapter.setShowOnlyThree(false);
        }
        if (home_top_article_rv.getFooterCount() == 0 && topArticleEntityList.size() > 3) {
            home_top_article_rv.addFooterView(topArticleFootView);
        }
    }

    @Override
    public void setBannerData(List<BannerEntity> result) {
        home_banner.setAdapter(new BannerImageAdapter<BannerEntity>(result) {
            @Override
            public void onBindView(BannerImageHolder holder, BannerEntity data, int position, int size) {
                GlideEngineUtils.getInstance().loadStringPhoto(ApplicationProvider.getInstance().getApplication(), data.getImagePath(), holder.imageView);
                holder.imageView.setOnClickListener(v -> ARouterUtils.startWeb(
                        data.getUrl(), data.getTitle(), data.getId(),
                        false, data.getImagePath(), data.getDesc(), "banner", ""));
            }
        })
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(getActivity()));
        //请求全部数据
        mPresenter.requestAllHomeArticle(currentPage);
    }

    @Override
    public void setOfficialAccountData(List<OfficialAccountEntity> officialAccountModels) {
        //设置公众号数据
        SetInitCustomView.initSwipeRecycleview(home_rv_official_account, new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL), mOfficialAccountAdapter, true);
        mOfficialAccountAdapter.setNewInstance(officialAccountModels);

    }

    @Override
    public void setAllHomeArticle(HomeArticleListEntity result) {
        showSuccess();
        //后台返回自动加1
        currentPage = result.getCurPage();
        mDatabind.homeRefreshLayout.finishLoadMore();
        mDatabind.homeRefreshLayout.finishRefresh();
        if (currentPage > 1) {
            mHomeArticleAdapter.addData(result.getDatas());
        } else {
            mHomeArticleAdapter.setNewInstance(result.getDatas());
            if (mDatabind.homeRecommendArticleBody.getHeaderCount() == 0) {
                mDatabind.homeRecommendArticleBody.addHeaderView(recommendHeadView);
            }
        }

        if (result.getDatas().size() == 0) {
            mDatabind.homeRefreshLayout.setEnableLoadMore(false);
        }
    }

    @Override
    public void setUnreadMessage(int number) {
        if (number > 0) {
            home_rl_message.setVisibility(View.VISIBLE);
            String strMsg = "";
            if (LanguageFontSizeUtils.isChinese()) {
                strMsg = "您有<font color=\"#EE7931\"> " + number + "</font> 条未读消息</font>";
            } else {
                strMsg = "You have <font color=\"#EE7931\"> " + number + "</font> Unread messages</font>";
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                home_tv_unread_message.setText(Html.fromHtml(strMsg, Html.FROM_HTML_MODE_LEGACY));
            } else {
                home_tv_unread_message.setText(Html.fromHtml(strMsg));
            }
        } else {
            home_rl_message.setVisibility(View.GONE);
        }
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


    private void initTopAdapterClick() {
        mTopArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mTopArticleAdapter.getData().get(position).getLink(),
                        mTopArticleAdapter.getData().get(position).getTitle(),
                        mTopArticleAdapter.getData().get(position).getId(),
                        mTopArticleAdapter.getData().get(position).isCollect(),
                        mTopArticleAdapter.getData().get(position).getEnvelopePic(),
                        mTopArticleAdapter.getData().get(position).getDesc(), mTopArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mTopArticleAdapter.getData().get(position).getAuthor()) ? mTopArticleAdapter.getData().get(position).getShareUser() : mTopArticleAdapter.getData().get(position).getAuthor());
            }
        });


        mTopArticleAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                BlurBuilder.snapShotWithoutStatusBar(getActivity());
                startActivity(new Intent(getActivity(), HomeArticlesTabActivity.class)
                        .putExtra("toparticles", (Serializable) mTopArticleAdapter.getData()));
                getActivity().overridePendingTransition(R.anim.base_scalealpha_in, R.anim.base_scalealpha_slient);

                return false;
            }
        });
    }


    private void initOfficialAccountClick() {
        mOfficialAccountAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ArrayList<OfficialAccountEntity> arrayList = new ArrayList<>();
                arrayList.addAll(mOfficialAccountAdapter.getData());
                ARouter.getInstance().build(RoutePathActivity.Wechat.Wechat_Pager)
                        .withParcelableArrayList("data", arrayList)
                        .withInt("position", position)
                        .navigation();
            }
        });
    }

    private void initArticleLinstener() {
        mHomeArticleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ARouterUtils.startWeb(mHomeArticleAdapter.getData().get(position - 1).getLink(),
                        mHomeArticleAdapter.getData().get(position - 1).getTitle(),
                        mHomeArticleAdapter.getData().get(position - 1).getId(),
                        mHomeArticleAdapter.getData().get(position - 1).isCollect(),
                        mHomeArticleAdapter.getData().get(position - 1).getEnvelopePic(),
                        mHomeArticleAdapter.getData().get(position - 1).getDesc(),
                        mHomeArticleAdapter.getData().get(position - 1).getChapterName(),
                        TextUtils.isEmpty(mHomeArticleAdapter.getData().get(position - 1).getAuthor()) ? mHomeArticleAdapter.getData().get(position - 1).getShareUser() : mHomeArticleAdapter.getData().get(position - 1).getAuthor());
            }
        });

        mHomeArticleAdapter.addChildClickViewIds(R.id.home_icon_collect);
        mHomeArticleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @LoginCheck
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                if (view.getId() == R.id.home_icon_collect) {
                    if (mHomeArticleAdapter.getData().get(position - 1).isCollect()) {
                        mPresenter.requestCancelCollectArticle(mHomeArticleAdapter.getData().get(position - 1).getId(), position - 1);
                    } else {
                        mPresenter.requestCollectArticle(mHomeArticleAdapter.getData().get(position - 1).getId(), position - 1);
                    }

                }
            }
        });


    }

    /**
     * 初始化二楼
     */
    private void initTwoLevel() {
        mOpenSourceAdapter = new OpenSourceAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.secondOpenframeRv, new LinearLayoutManager(getActivity()), mOpenSourceAdapter, true);
        //初始化标签
        Type type = new TypeToken<List<OpenSourceEntity>>() {
        }.getType();
        String jsonData = JsonUtils.getJson(getActivity(), "opensourceproject.json");
        List<OpenSourceEntity> mDataList = GsonUtils.getList(jsonData, type);
        mOpenSourceAdapter.setNewInstance(mDataList);

        mOpenSourceAdapter.addChildClickViewIds(R.id.home_opensource_abroadlink_value, R.id.home_opensource_internallink_value, R.id.home_iv_abroadcopy, R.id.home_iv_internalcopy);
        mOpenSourceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

                if (view.getId() == R.id.home_opensource_abroadlink_value) {
                    ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                            .withString("webUrl", mOpenSourceAdapter.getData().get(position).getAbroadlink())
                            .withString("webTitle", mOpenSourceAdapter.getData().get(position).getName())
                            .navigation();
                } else if (view.getId() == R.id.home_opensource_internallink_value) {
                    ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                            .withString("webUrl", mOpenSourceAdapter.getData().get(position).getInternallink())
                            .withString("webTitle", mOpenSourceAdapter.getData().get(position).getName())
                            .navigation();
                } else if (view.getId() == R.id.home_iv_abroadcopy) {
                    SystemUtils.copyContent(getActivity(), mOpenSourceAdapter.getData().get(position).getAbroadlink());
                    ToastUtils.getInstance().showToast(getString(R.string.base_success_copylink));
                } else {
                    SystemUtils.copyContent(getActivity(), mOpenSourceAdapter.getData().get(position).getInternallink());
                    ToastUtils.getInstance().showToast(getString(R.string.base_success_copylink));
                }
            }
        });
        mOpenSourceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //跳到webview
                ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                        .withString("webUrl", mOpenSourceAdapter.getData().get(position).getAbroadlink())
                        .withString("webTitle", mOpenSourceAdapter.getData().get(position).getName())
                        .navigation();
            }
        });


    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        //请求全部数据
        mPresenter.requestAllHomeArticle(currentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        lazyLoadData();
    }


    public class ProxyClick {
        public void scrollUp() {
            if (openTwoLevel) {
                mDatabind.homeTwoLevelHeader.finishTwoLevel();
            } else {
                scrollTop();
            }
        }


    }

    public void scrollTop() {
        mDatabind.homeRecommendArticleBody.smoothScrollToPosition(0);
    }

    /**
     * 登录成功
     *
     * @param loginInSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess) {
        mPresenter.requestUnreadMessage();
    }

    /**
     * 退出登录成功
     *
     * @param logoutSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutSuccess(EventBusUtils.LogoutSuccess logoutSuccess) {
        //刷新页面
        home_rl_message.setVisibility(View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void readAllMessage(EventBusUtils.ReadAllMessage readAllMessage) {
        home_rl_message.setVisibility(View.GONE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
