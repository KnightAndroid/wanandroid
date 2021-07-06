package com.knight.wanandroid.module_home.module_fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.entity.OfficialAccountEntity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.fragment.BaseFragment;
import com.knight.wanandroid.library_base.fragment.EveryDayPushArticleFragment;
import com.knight.wanandroid.library_base.fragment.UpdateAppDialogFragment;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_common.ApplicationProvider;
import com.knight.wanandroid.library_permiss.OnPermissionCallback;
import com.knight.wanandroid.library_permiss.Permission;
import com.knight.wanandroid.library_permiss.XXPermissions;
import com.knight.wanandroid.library_scan.activity.ScanCodeActivity;
import com.knight.wanandroid.library_scan.annoation.ScanStyle;
import com.knight.wanandroid.library_scan.decode.ScanCodeConfig;
import com.knight.wanandroid.library_util.BlurBuilder;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.DateUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.JsonUtils;
import com.knight.wanandroid.library_util.ScreenUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.library_util.constant.MMkvConstants;
import com.knight.wanandroid.library_util.imageengine.GlideEngineUtils;
import com.knight.wanandroid.library_widget.SetInitCustomView;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentHomeBinding;
import com.knight.wanandroid.module_home.module_activity.HomeArticlesTabActivity;
import com.knight.wanandroid.module_home.module_activity.KnowledgeLabelActivity;
import com.knight.wanandroid.module_home.module_activity.SearchActivity;
import com.knight.wanandroid.module_home.module_adapter.OfficialAccountAdapter;
import com.knight.wanandroid.module_home.module_adapter.OpenSourceAdapter;
import com.knight.wanandroid.module_home.module_adapter.TopArticleAdapter;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.BannerEntity;
import com.knight.wanandroid.module_home.module_entity.EveryDayPushArticlesEntity;
import com.knight.wanandroid.module_home.module_entity.OpenSourceEntity;
import com.knight.wanandroid.module_home.module_entity.TopArticleEntity;
import com.knight.wanandroid.module_home.module_logic.HomeArticleLogic;
import com.knight.wanandroid.module_home.module_model.HomeModel;
import com.knight.wanandroid.module_home.module_presenter.HomePresenter;
import com.knight.wanandroid.module_home.module_utils.CustomViewUtils;
import com.scwang.smart.refresh.header.listener.OnTwoLevelListener;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;
import com.wanandroid.knight.library_database.entity.PushDateEntity;
import com.wanandroid.knight.library_database.repository.PushArticlesDateRepository;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import kotlin.jvm.functions.Function1;

import static android.app.Activity.RESULT_OK;

/**
 * @author created by knight
 * @organize wanandroid
 * @Date 2021/4/1 10:33
 * @descript:首页fragment
 */


@Route(path = RoutePathFragment.Home.Home_Pager)
public class HomeFragment extends BaseFragment<HomeFragmentHomeBinding, HomePresenter, HomeModel> implements HomeContract.HomeView {

    private TopArticleAdapter mTopArticleAdapter;
    private OfficialAccountAdapter mOfficialAccountAdapter;
    private View topArticleFootView;
    private List<TopArticleEntity> mTopArticleEntities;
    private boolean isShowOnlythree = false;
    private boolean openTwoLevel = false;
    List<Fragment> mFragments = new ArrayList<>();
    //开源库
    private OpenSourceAdapter mOpenSourceAdapter;
    private List<EveryDayPushEntity> mEveryDayPushEntities;
    List<String> knowledgeLabelList;



    @Override
    public int layoutId(){
        return R.layout.home_fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDatabind.setClick(new ProcyClick());
        EventBus.getDefault().register(this);
        initUserData();
        mTopArticleAdapter = new TopArticleAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeTopArticleRv,new LinearLayoutManager(getActivity()),mTopArticleAdapter,false);
        initTwoLevel();
        mOfficialAccountAdapter = new OfficialAccountAdapter(new ArrayList<>());
        topArticleFootView = LayoutInflater.from(getActivity()).inflate(R.layout.home_toparticle_foot,null);
        topArticleFootView.findViewById(R.id.home_ll_seemorearticles).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeArticleLogic.getInstance().setArrowAnimate(mTopArticleAdapter,topArticleFootView.findViewById(R.id.home_iv_toparticlearrow),isShowOnlythree);
                isShowOnlythree = !isShowOnlythree;
            }
        });
        initTopAdapterClick();
        initOfficialAccountClick();
        loadLoading(mDatabind.homeRefreshLayout);
        mDatabind.homeRefreshLayout.setOnMultiListener(new SimpleMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
           //     mDatabind.homeIncludeToolbar.toolbar.setAlpha(1 - Math.min(percent, 1));
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //请求顶部文章
                mPresenter.requestTopArticle();
                //请求轮播图
                mPresenter.requestBannerData();
                //请求公众号数据
                mPresenter.requestOfficialAccountData();
            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

                if (oldState == RefreshState.TwoLevel) {
                    mDatabind.homeTwoLevelContent.animate().alpha(0).setDuration(0);
                } else if (oldState == RefreshState.TwoLevelReleased) {
                    openTwoLevel = true;
                    mDatabind.homeIconFab.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.base_icon_bottom));
                } else if (oldState == RefreshState.TwoLevelFinish) {

                    openTwoLevel = false;
                    mDatabind.homeIconFab.setImageDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.base_icon_up));
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

    }


    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData(){
        //请求顶部文章
        mPresenter.requestTopArticle();
        //请求轮播图
        mPresenter.requestBannerData();
        //请求公众号数据
        mPresenter.requestOfficialAccountData();
        //请求每日更新
        mPresenter.requestEveryDayPushArticle();
        //请求更新app
        mPresenter.requestAppUpdateMessage();
        //未读数量请求
        if (ModuleConfig.getInstance().user != null) {
            mPresenter.requestUnreadMessage();
        }

    }

    @Override
    protected void reLoadData() {
        //请求顶部文章
        mPresenter.requestTopArticle();
        //请求轮播图
        mPresenter.requestBannerData();
        //请求公众号数据
        mPresenter.requestOfficialAccountData();
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
                holder.imageView.setOnClickListener(v -> ARouterUtils.startWeb(
                        data.getUrl(),data.getTitle(),data.getId(),
                        false,data.getImagePath(),data.getDesc(),"banner",""));
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
        mDatabind.homeRefreshLayout.finishRefresh();

    }

    @Override
    public void setAppUpdateMessage(AppUpdateEntity appUpdateEntity) {
        //先判断本地版本和远端版本是否一致
        if (!appUpdateEntity.getVersionName().equals(SystemUtils.getAppVersionName(getActivity()))) {
            new UpdateAppDialogFragment(appUpdateEntity).show(getParentFragmentManager(), "dialog_update");
        }
    }

    @Override
    public void setEveryDayPushArticle(EveryDayPushArticlesEntity everyDayPushArticlesEntity) {
        mEveryDayPushEntities = everyDayPushArticlesEntity.getDatas();
        //是否要展示每日推送文章
        if (everyDayPushArticlesEntity.isPushStatus() && DateUtils.isToday(everyDayPushArticlesEntity.getTime())) {
            PushArticlesDateRepository.getInstance().findPushArticlesDate(new PushArticlesDateRepository.OnQueryPushArticlesDateCallBack() {
                @Override
                public void onFindPushArticlesDate(List<PushDateEntity> pushDateEntities) {
                    if (pushDateEntities != null && pushDateEntities.size() > 0) {
                       //更新
                       PushDateEntity pushDateEntity = pushDateEntities.get(0);
                       if (!pushDateEntity.getTime().equals(everyDayPushArticlesEntity.getTime())) {
                           pushDateEntity.setTime(everyDayPushArticlesEntity.getTime());
                           PushArticlesDateRepository.getInstance().updatePushArticlesDate(pushDateEntity);
                           //并且展示
                           new EveryDayPushArticleFragment(mEveryDayPushEntities).show(getParentFragmentManager(), "dialog_everydaypush");
                       }

                    } else {
                        PushDateEntity pushDateEntity = new PushDateEntity();
                        pushDateEntity.setTime(everyDayPushArticlesEntity.getTime());
                        PushArticlesDateRepository.getInstance().insertPushArticlesDate(pushDateEntity);
                        new EveryDayPushArticleFragment(mEveryDayPushEntities).show(getParentFragmentManager(), "dialog_everydaypush");
                    }
                }
            });

//            EveryDayPushArticleRepository.getInstance().findHistoryReadRecords(new EveryDayPushArticleRepository.OnQueryEveryDayArticleCallBack() {
//                @Override
//                public void onFindEveryDayArticle(List<EveryDayPushEntity> everyDayPushEntitys) {
//                    if (everyDayPushEntitys != null && everyDayPushEntitys.size() > 0) {
//
//                        //更新
//                        EveryDayPushEntity entity = everyDayPushEntitys.get(0);
//                        if (!entity.getTime().equals(everyDayPushEntity.getTime())) {
//                            entity.setArticledesc(everyDayPushEntity.getArticledesc());
//                            entity.setArticleLink(everyDayPushEntity.getArticleLink());
//                            entity.setAuthor(everyDayPushEntity.getAuthor());
//                            entity.setPushStatus(everyDayPushEntity.isPushStatus());
//                            entity.setTime(everyDayPushEntity.getTime());
//                            EveryDayPushArticleRepository.getInstance().updateEveryDayPushArticle(entity);
//                            //并且展示
//                            new EveryDayPushArticleFragment(everyDayPushEntity).show(getParentFragmentManager(), "dialog_everydaypush");
//                        }
//
//                    } else {
//                        //插入
//                        EveryDayPushArticleRepository.getInstance().insertEveryDayPushArticle(everyDayPushEntity);
//                        //显示
//                        new EveryDayPushArticleFragment(everyDayPushEntity).show(getParentFragmentManager(), "dialog_everydaypush");
//                    }
//                }
//            });
        }
    }

    @Override
    public void setUnreadMessage(int number) {
        if (number > 0) {
            mDatabind.homeRlMessage.setVisibility(View.VISIBLE);
            String strMsg = "您有<font color=\"#EE7931\"> "+number+"</font> 条未读消息</font>";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mDatabind.homeTvUnreadMessage.setText(Html.fromHtml(strMsg,Html.FROM_HTML_MODE_LEGACY));
            } else {
                mDatabind.homeTvUnreadMessage.setText(Html.fromHtml(strMsg));
            }
        } else {
            mDatabind.homeRlMessage.setVisibility(View.GONE);
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


    public class ProcyClick{

        public void searchArticles(){
            startActivity(new Intent(getActivity(), SearchActivity.class));
        }

        public void goLogin(){
            if (mDatabind.homeIncludeToolbar.homeTvLoginname.getText().toString().equals("登录")) {
                ARouterUtils.startActivity(RoutePathActivity.Mine.Login_Pager);
            }


        }
        
        public void scrollUp(){
            if (openTwoLevel) {
                mDatabind.homeTwoLevelHeader.finishTwoLevel();
            } else {
                scrollTop();
            }
        }

        public void scanCode(){
                    XXPermissions.with(HomeFragment.this)
                            .permission(Permission.CAMERA)
                            .request(new OnPermissionCallback() {
                                @Override
                                public void onGranted(List<String> permissions, boolean all) {
                                    if (all) {
                                        new ScanCodeConfig.Builder()
                                                .setFragment(HomeFragment.this)
                                                .setActivity(getActivity())
                                                .setPlayAudio(true)
                                                .setStyle(ScanStyle.FULL_SCREEN)
                                                .build().start(ScanCodeActivity.class);
                                    }
                                }
                            });
        }


        public void shareArticle(){
            ARouter.getInstance().build(RoutePathActivity.Square.ShareArticle).navigation();
        }


        public void showEveryDayPush(){
            if (mEveryDayPushEntities != null) {
                new EveryDayPushArticleFragment(mEveryDayPushEntities).show(getParentFragmentManager(), "dialog_everydaypush");
            }
            
        }


        public void goMessage() {
            ARouter.getInstance().build(RoutePathActivity.Message.Message_pager).navigation();
        }


        public void goknowledgeLabel() {
//            startActivity(new Intent(getActivity(), KnowledgeLabelActivity.class)
//                    .putExtra("data",(Serializable) knowledgeLabelList));



            Intent intent = new Intent(getActivity(),KnowledgeLabelActivity.class);
            intent.putExtra("data", (Serializable) knowledgeLabelList);
            startActivity(intent);
           // startActivityForResult(intent, KnowledgeLabelActivity.QUESTCODE);

        }

    }

    private void initMagicIndicator() {
        knowledgeLabelList = CacheUtils.getInstance().getDataInfo("knowledgeLabel",new TypeToken<List<String>>(){}.getType());
        //初始化标签
        if (knowledgeLabelList == null || knowledgeLabelList.size() == 0) {
            Type type = new TypeToken<List<String>>() {}.getType();
            String jsonData = JsonUtils.getJson(getActivity(),"searchkeywords.json");
            knowledgeLabelList = GsonUtils.getList(jsonData,type);
        }
        mFragments.clear();
        for (int i = 0;i < knowledgeLabelList.size();i++) {
            mFragments.add(new HomeArticlesFragment());
        }
        ViewSetUtils.setViewPager2Init(getActivity(), mFragments, mDatabind.viewPager, false);
        
        CustomViewUtils.bindViewPager2(mDatabind.magicIndicator, mDatabind.viewPager, knowledgeLabelList, new Function1() {
            @Override
            public Object invoke(Object o) {
                int[] position = new int[2];
                mDatabind.homeLlTop.getLocationOnScreen(position);
                mDatabind.homeCoordinatorsl.fling(mDatabind.homeLlTop.getHeight() + position[1]  - mDatabind.homeIncludeToolbar.toolbar.getHeight());
                mDatabind.homeCoordinatorsl.smoothScrollBy(0,mDatabind.homeLlTop.getHeight() + position[1]  - mDatabind.homeIncludeToolbar.toolbar.getHeight(),600);
                return null;
            }
        });

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
                        mTopArticleAdapter.getData().get(position).getDesc(),mTopArticleAdapter.getData().get(position).getChapterName(),
                        TextUtils.isEmpty(mTopArticleAdapter.getData().get(position).getAuthor()) ? mTopArticleAdapter.getData().get(position).getShareUser() : mTopArticleAdapter.getData().get(position).getAuthor());
            }
        });


        mTopArticleAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                BlurBuilder.snapShotWithoutStatusBar(getActivity());
                startActivity(new Intent(getActivity(), HomeArticlesTabActivity.class)
                .putExtra("toparticles",(Serializable) mTopArticleAdapter.getData()));
                getActivity().overridePendingTransition(R.anim.base_scalealpha_in, R.anim.base_scalealpha_slient);

                return false;
            }
        });
    }



    private void initOfficialAccountClick(){
        mOfficialAccountAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ArrayList<OfficialAccountEntity> arrayList = new ArrayList<>();
                arrayList.addAll(mOfficialAccountAdapter.getData());
                ARouter.getInstance().build(RoutePathActivity.Wechat.Wechat_Pager)
                        .withParcelableArrayList("data",arrayList)
                        .withInt("position",position)
                        .navigation();
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

    private void initTwoLevel(){
        mOpenSourceAdapter = new OpenSourceAdapter(new ArrayList<>());
        SetInitCustomView.initSwipeRecycleview(mDatabind.homeIncludeRecyceview.baseBodyRv,new LinearLayoutManager(getActivity()),mOpenSourceAdapter,true);
        //初始化标签
        Type type = new TypeToken<List<OpenSourceEntity>>() {}.getType();
        String jsonData = JsonUtils.getJson(getActivity(),"opensourceproject.json");
        List<OpenSourceEntity> mDataList = GsonUtils.getList(jsonData,type);
        mOpenSourceAdapter.setNewInstance(mDataList);
        mDatabind.homeIncludeRecyceview.baseFreshlayout.setEnableLoadMore(false);
        mDatabind.homeIncludeRecyceview.baseFreshlayout.setEnableRefresh(false);

        SmartRefreshLayout.LayoutParams params = ( SmartRefreshLayout.LayoutParams) mDatabind.homeIncludeRecyceview.baseBodyRv.getLayoutParams();
        params.setMargins(0,0,0, ScreenUtils.dp2px(64));
        mDatabind.homeIncludeRecyceview.baseBodyRv.setLayoutParams(params);



        mOpenSourceAdapter.addChildClickViewIds(R.id.home_opensource_abroadlink_value,R.id.home_opensource_internallink_value,R.id.home_iv_abroadcopy,R.id.home_iv_internalcopy);
        mOpenSourceAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
    
                if(view.getId() == R.id.home_opensource_abroadlink_value){
                    ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                            .withString("webUrl",mOpenSourceAdapter.getData().get(position).getAbroadlink())
                            .withString("webTitle",mOpenSourceAdapter.getData().get(position).getName())
                            .navigation();
                } else if(view.getId() == R.id.home_opensource_internallink_value){
                    ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                            .withString("webUrl",mOpenSourceAdapter.getData().get(position).getInternallink())
                            .withString("webTitle",mOpenSourceAdapter.getData().get(position).getName())
                            .navigation();
                } else if(view.getId() == R.id.home_iv_abroadcopy){
                    SystemUtils.copyContent(getActivity(),mOpenSourceAdapter.getData().get(position).getAbroadlink());
                    ToastUtils.getInstance().showToast("已成功复制链接");
                } else {
                    SystemUtils.copyContent(getActivity(),mOpenSourceAdapter.getData().get(position).getInternallink());
                    ToastUtils.getInstance().showToast("已成功复制链接");
                }
            }
        });
        mOpenSourceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //跳到webview
                ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                        .withString("webUrl",mOpenSourceAdapter.getData().get(position).getAbroadlink())
                        .withString("webTitle",mOpenSourceAdapter.getData().get(position).getName())
                        .navigation();
            }
        });


    }


    /**
     * 登录成功
     * @param loginInSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess){
        //登录成功
        ModuleConfig.getInstance().user = CacheUtils.getInstance().getDataInfo(MMkvConstants.USER,UserInfoEntity.class);
        mDatabind.homeIncludeToolbar.homeTvLoginname.setText(ModuleConfig.getInstance().user.getUsername());
        //重新请求公众号数据
        mPresenter.requestOfficialAccountData();
        mPresenter.requestUnreadMessage();
    }


    /**
     * 退出登录成功
     * @param logoutSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutSuccess(EventBusUtils.LogoutSuccess logoutSuccess){
        //刷新页面
        mDatabind.homeIncludeToolbar.homeTvLoginname.setText("登录");
        //重新请求公众号数据
        mPresenter.requestOfficialAccountData();
        mDatabind.homeRlMessage.setVisibility(View.GONE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void readAllMessage(EventBusUtils.ReadAllMessage readAllMessage){
        mDatabind.homeRlMessage.setVisibility(View.GONE);
    }


    /**
     *
     * 更改标签
     * @param changeLabel
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeLabel(EventBusUtils.ChangeLabel changeLabel) {
        knowledgeLabelList.clear();
        knowledgeLabelList.addAll(changeLabel.getResults());
        mFragments.clear();
        for (int i = 0;i < knowledgeLabelList.size();i++) {
            mFragments.add(new HomeArticlesFragment());
        }
        ((CommonNavigator)mDatabind.magicIndicator.getNavigator()).notifyDataSetChanged();
        ViewSetUtils.setViewPager2Init(getActivity(), mFragments, mDatabind.viewPager, false);

    }



    public void scrollTop(){
        mDatabind.homeCoordinatorsl.fullScroll(View.FOCUS_UP);

    }



    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收扫码结果
        if (resultCode == RESULT_OK) {
            if (requestCode == ScanCodeConfig.QUESTCODE && data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    String result = extras.getString(ScanCodeConfig.CODE_KEY);
                    //跳到webview
                    ARouter.getInstance().build(RoutePathActivity.Web.Web_Normal)
                            .withString("webUrl", result)
                            .withString("webTitle", "扫码结果")
                            .navigation();
                }

            }
        }

    }

}
