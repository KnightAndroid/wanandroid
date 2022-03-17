package com.knight.wanandroid.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.knight.wanandroid.R;
import com.knight.wanandroid.databinding.ActivityMainBinding;
import com.knight.wanandroid.library_base.baseactivity.BaseDBActivity;
import com.knight.wanandroid.library_base.route.RoutePathActivity;
import com.knight.wanandroid.library_common.utils.CacheUtils;
import com.knight.wanandroid.library_common.utils.ColorUtils;
import com.knight.wanandroid.library_network.NetWorkUtils;
import com.knight.wanandroid.library_util.ActivityManagerUtils;
import com.knight.wanandroid.library_util.EventBusUtils;
import com.knight.wanandroid.library_util.ViewSetUtils;
import com.knight.wanandroid.library_util.imageengine.ImageLoader;
import com.knight.wanandroid.library_util.toast.ToastUtils;
import com.knight.wanandroid.library_widget.BombView;
import com.knight.wanandroid.module_hierachy.module_fragment.HierachyNavigateMainFragment;
import com.knight.wanandroid.module_home.module_fragment.HomeFragment;
import com.knight.wanandroid.module_mine.fragment.MineFragment;
import com.knight.wanandroid.module_project.module_fragment.ProjectFragment;
import com.knight.wanandroid.module_square.module_fragment.SquareFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


@Route(path = RoutePathActivity.Main.MainPager)
public class MainActivity extends BaseDBActivity<ActivityMainBinding> {

    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
    private long mExitAppTime;
    private HomeFragment mHomeFragment;
    private SquareFragment mSquareFragment;
    private ProjectFragment mProjectFragment;
    private HierachyNavigateMainFragment mHierachyNavigateMainFragment;
    private MineFragment mMineFragment;

    @Override
    public int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        initFragment();
//        NetWorkUtils.postDelayed(()->{
//            mDatabind.bomb.setBombStatusListener(new BombView.BombStatusListener() {
//                @Override
//                public void onAnimationStart() {
//
//                }
//
//                @Override
//                public void onAnimationEnd() {
//                  NetWorkUtils.post(()->{
//                      ImageLoader.loadGif(MainActivity.this,R.drawable.ic_newyear,mDatabind.ivGif);
//                  });
//
//                }
//            }).startBomb();
//        },1000);



    }

    @Override
    protected void setThemeColor(boolean isDarkMode) {

    }

    private void initFragment() {
        fragments.add(mHomeFragment = new HomeFragment());
        fragments.add(mSquareFragment = new SquareFragment());
        fragments.add(mProjectFragment = new ProjectFragment());
        fragments.add(mHierachyNavigateMainFragment = new HierachyNavigateMainFragment());
        fragments.add(mMineFragment = new MineFragment());
        ViewSetUtils.setIsUserInputEnable(this, mDatabind.mainViewpager, fragments, false);
        mDatabind.btnNav.setItemTextColor(ColorUtils.createColorStateList(CacheUtils.getThemeColor(), ColorUtils.convertToColorInt("a6a6a6")));
        mDatabind.btnNav.setItemIconTintList(ColorUtils.createColorStateList(CacheUtils.getThemeColor(), ColorUtils.convertToColorInt("a6a6a6")));
        mDatabind.btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.homeFragment) {
                    mDatabind.mainViewpager.setCurrentItem(0, false);
                } else if (item.getItemId() == R.id.squareFragment) {
                    mDatabind.mainViewpager.setCurrentItem(1, false);
                } else if (item.getItemId() == R.id.projectFragment) {
                    mDatabind.mainViewpager.setCurrentItem(2, false);
                } else if (item.getItemId() == R.id.navigateFragment) {
                    mDatabind.mainViewpager.setCurrentItem(3, false);
                } else if (item.getItemId() == R.id.mineFragment) {
                    mDatabind.mainViewpager.setCurrentItem(4, false);
                }
                return true;
            }
        });

    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mExitAppTime <= 2000) {
                ActivityManagerUtils.getInstance().finishAllActivity();
                System.exit(0);
            } else {
                mExitAppTime = System.currentTimeMillis();
                ToastUtils.show(R.string.app_exit_tip);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setRecreateMain(EventBusUtils.RecreateMain recreateMain) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (mHomeFragment != null) {
            fragmentTransaction.remove(mHomeFragment);
        }

        if (mSquareFragment != null) {
            fragmentTransaction.remove(mSquareFragment);
        }

        if (mProjectFragment != null) {
            fragmentTransaction.remove(mProjectFragment);
        }

        if (mHierachyNavigateMainFragment != null) {
            fragmentTransaction.remove(mHierachyNavigateMainFragment);
        }

        if (mMineFragment != null) {
            fragmentTransaction.remove(mMineFragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        recreate();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeEyeCare(EventBusUtils.ChangeEyeCare changeEyeCare) {
        openOrCloseEye(changeEyeCare.isEyeCare());
    }


//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mDatabind.mainViewpager.setPadding(StatusBarUtils.getStatusBarHeight(this),0,0,0);
//        } else {
//            mDatabind.mainViewpager.setPadding(0,0,0,0);
//        }
//    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatabind.bomb.release();
        EventBus.getDefault().unregister(this);
        mHomeFragment = null;
        mSquareFragment = null;
        mProjectFragment = null;
        mHierachyNavigateMainFragment = null;
        mMineFragment = null;
    }

}