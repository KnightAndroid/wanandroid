package com.knight.wanandroid.module_home.module_fragment;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.reflect.TypeToken;
import com.knight.wanandroid.library_base.basefragment.BaseFragment;
import com.knight.wanandroid.library_base.entity.AppUpdateEntity;
import com.knight.wanandroid.library_base.entity.UserInfoEntity;
import com.knight.wanandroid.library_base.fragment.EveryDayPushArticleFragment;
import com.knight.wanandroid.library_base.fragment.UpdateAppDialogFragment;
import com.knight.wanandroid.library_base.initconfig.ModuleConfig;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_base.route.RoutePathFragment;
import com.knight.wanandroid.library_base.util.ARouterUtils;
import com.knight.wanandroid.library_permiss.OnPermissionCallback;
import com.knight.wanandroid.library_permiss.Permission;
import com.knight.wanandroid.library_permiss.XXPermissions;
import com.knight.wanandroid.library_scan.activity.ScanCodeActivity;
import com.knight.wanandroid.library_scan.annoation.ScanStyle;
import com.knight.wanandroid.library_scan.decode.ScanCodeConfig;
import com.knight.wanandroid.library_util.CacheUtils;
import com.knight.wanandroid.library_util.DateUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.GsonUtils;
import com.knight.wanandroid.library_util.JsonUtils;
import com.knight.wanandroid.library_util.SystemUtils;
import com.knight.wanandroid.library_util.ToastUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.library_util.constant.MMkvConstants;
import com.knight.wanandroid.module_home.R;
import com.knight.wanandroid.module_home.databinding.HomeFragmentHomeBinding;
import com.knight.wanandroid.module_home.module_activity.KnowledgeLabelActivity;
import com.knight.wanandroid.module_home.module_activity.SearchActivity;
import com.knight.wanandroid.module_home.module_contract.HomeContract;
import com.knight.wanandroid.module_home.module_entity.EveryDayPushArticlesEntity;
import com.knight.wanandroid.module_home.module_model.HomeModel;
import com.knight.wanandroid.module_home.module_presenter.HomePresenter;
import com.knight.wanandroid.module_home.module_utils.CustomViewUtils;
import com.wanandroid.knight.library_database.entity.EveryDayPushEntity;
import com.wanandroid.knight.library_database.entity.PushDateEntity;
import com.wanandroid.knight.library_database.repository.PushArticlesDateRepository;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    List<Fragment> mFragments = new ArrayList<>();


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
        initMagicIndicator();
    }

    /**
     *
     * 懒加载
     *
     */
    @Override
    protected void lazyLoadData(){
        //请求每日更新
        mPresenter.requestEveryDayPushArticle();
        //请求更新app
        mPresenter.requestAppUpdateMessage();


    }

    @Override
    protected void reLoadData() {
       lazyLoadData();
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
        


        public void scanCode() {
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
            Intent intent = new Intent(getActivity(),KnowledgeLabelActivity.class);
            intent.putExtra("data", (Serializable) knowledgeLabelList);
            startActivity(intent);
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
            if (i == 0) {
                mFragments.add(new HomeRecommendFragment());
            } else {
                mFragments.add(new HomeArticlesFragment());
            }

        }
        ViewSetUtils.setViewPager2Init(getActivity(), mFragments, mDatabind.viewPager, false);
        
//        CustomViewUtils.bindViewPager2(mDatabind.magicIndicator, mDatabind.viewPager, knowledgeLabelList, new Function1() {
//            @Override
//            public Object invoke(Object o) {
//                int[] position = new int[2];
//                mDatabind.homeLlTop.getLocationOnScreen(position);
//                mDatabind.homeCoordinatorsl.fling(mDatabind.homeLlTop.getHeight() + position[1]  - mDatabind.homeIncludeToolbar.toolbar.getHeight());
//                mDatabind.homeCoordinatorsl.smoothScrollBy(0,mDatabind.homeLlTop.getHeight() + position[1]  - mDatabind.homeIncludeToolbar.toolbar.getHeight(),600);
//                return null;
//            }
//        });
        CustomViewUtils.bindViewPager2(mDatabind.magicIndicator, mDatabind.viewPager, knowledgeLabelList, new Function1() {
            @Override
            public Object invoke(Object o) {
                return null;
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



    /**
     * 登录成功
     * @param loginInSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginInSuccess(EventBusUtils.LoginInSuccess loginInSuccess){
        //登录成功
        ModuleConfig.getInstance().user = CacheUtils.getInstance().getDataInfo(MMkvConstants.USER,UserInfoEntity.class);
        mDatabind.homeIncludeToolbar.homeTvLoginname.setText(ModuleConfig.getInstance().user.getUsername());

    }


    /**
     * 退出登录成功
     * @param logoutSuccess
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogoutSuccess(EventBusUtils.LogoutSuccess logoutSuccess){
        //刷新页面
        mDatabind.homeIncludeToolbar.homeTvLoginname.setText("登录");
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
